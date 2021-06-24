import SideNav from './SideNav';
import { useEffect, useState } from "react";
import x_icon from "../Images/x_icon.png"
import { useParams } from 'react-router';
import edit_icon from "../Images/edit_icon.png"

const Members = ({ footerHandle, projectClientRef, userId }) => {
    // Add member popup handlers
    const [add_members_popup, handleMembersPopup] = useState("hide");
    const [members, setMembers] = useState([]);
    const [memberPermissions, setMemberPermissions] = useState([]);
    const [projectName, setProjectName] = useState('');
    // Edit member popup handlers
    const [edit_member_popup, handleEditMember] = useState("hide");
    const [current_member, handleCurrentMember] = useState(null);

    const hide_members_popup = () => {
        handleMembersPopup("hide");
    }
    const show_members_popup = () => {
        handleMembersPopup("show");
    }
    const handleAddMemberButtonClick = () => {
        hide_members_popup();
    }


    const hideEditMember = () => {
        handleEditMember("hide");
    }
    const showEditMember = (member) => {
        handleCurrentMember(member);
        handleEditMember("show");
    }
    const handleEditMemberButtonClick = () => {
        hideEditMember();
    }

    const [isAdmin, setIsAdmin] = useState(false);
    useEffect(() => {
        const userPerms = memberPermissions.find(memberPermission => memberPermission.memberId === userId);
        if (userPerms) {
            setIsAdmin(userPerms.permissions.find(p => p === 'ADMIN') !== undefined);
        }
    }, [memberPermissions, userId]);

    const { projectId } = useParams();

    const fetchMembers = () => {
        projectClientRef.current.get_all_users_in_project_by_id(projectId).then((res) => {
            console.log(res);
            setMembers(res.users);
        }).catch((err) => {
            console.log(err);
        });
    }
    useEffect(fetchMembers, []);

    useEffect(() => {
        projectClientRef.current.get_project_by_id(projectId).then(res => {
            // console.log(res);
            setProjectName(res.name);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    const fetchMemberPermissions = () => {
        projectClientRef.current.get_project_permissions_for_all_users(projectId).then(res => {
            setMemberPermissions(res.map(permission => {
                const toSave = {
                    memberId: permission.customerId,
                    permissions: permission.permissions,
                    permissionId: permission.id
                };
                return toSave;
            }));
        }).catch(err => {
            console.log(err);
        })
    }
    useEffect(fetchMemberPermissions, []);

    const deleteMember = () => {
        projectClientRef.current.delete_user_from_project_with_id(projectId, current_member.id).then((res) => {
            console.log(res);
            fetchMemberPermissions();
            fetchMembers();
        }).catch((err) => {
            console.log(err);
        });
    }

    useEffect(footerHandle, [footerHandle]);

    return (
        <div className="members proj_page">
            <div className="center_content">
                <SideNav />
                <main>
                    {/* Content */}
                    <div className="content">
                        <h1 id="project_name">{projectName}</h1>
                        {/* Main Panel */}
                        <div className="main_panel">
                            <h1 id="team_members">Team Members</h1>
                            <button
                                onClick={show_members_popup}
                                style={isAdmin ? {} : { display: 'none' }}
                            >+ Add Member</button>
                            <div className="table_wrapper">
                                <table id="main_table">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Fullname</th>
                                            <th>Permissions</th>
                                            {isAdmin &&
                                                <th></th>
                                            }
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {members.map(member => (
                                            <tr key={member.id}>
                                                <td>
                                                    {member.id === userId && <span style={{ color: 'green' }}>(ME)</span>}
                                                    &nbsp;
                                                    {member.username}
                                                </td>
                                                <td>{member.name} {member.surname}</td>
                                                <td>
                                                    {
                                                        (memberPermissions.length > 0)
                                                        &&
                                                        memberPermissions
                                                            .find(memberPermission => memberPermission.memberId === member.id)
                                                            .permissions
                                                            .toString()
                                                    }
                                                </td>
                                                {isAdmin &&
                                                    <td style={{ width: "40px", padding: "0", textAlign: "center" }}>
                                                        {member.id !== userId &&
                                                            <img
                                                                id="pencilIcon"
                                                                src={edit_icon}
                                                                alt="Pencil"
                                                                onClick={() => showEditMember(member)}
                                                            />
                                                        }
                                                    </td>
                                                }
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    {/*Add members Popup */}
                    {add_members_popup === "show" &&
                        <div className="members_popup">
                            <div>
                                <h2>Add Member</h2>
                                <img src={x_icon} alt="accountIcon" onClick={hide_members_popup}></img>
                            </div>
                            <form className="add_member_form">
                                <input id="memberEmail" type="text" placeholder="Email Address"></input>
                                <button onClick={handleAddMemberButtonClick}>Add</button>
                            </form>
                        </div>
                    }
                    {/*Edit member Popup */}

                    {edit_member_popup === "show" &&
                        <div className="members_popup">
                            <div style={{ display: "flex", justifyContent: "space-between" }}>
                                <h2>{current_member.name}</h2>
                                <img src={x_icon} alt="accountIcon" onClick={hideEditMember}></img>
                            </div>
                            <br />
                            <div>
                                <form className="edit_member_form">
                                    <h2>Permissions:</h2>
                                    <br></br>
                                    <br></br>
                                    <label>
                                        <input id="checkbox" type="checkbox" />
                                        Read Issues
                                    </label>
                                    <br />
                                    <label>
                                        <input id="checkbox" type="checkbox" />
                                        Write Issues
                                    </label>
                                    <br />
                                    <label>
                                        <input id="checkbox" type="checkbox" />
                                        Delete Issues
                                    </label>
                                    <br />
                                    <button onClick={handleEditMemberButtonClick}>Save changes</button>
                                </form>
                            </div>
                            {(current_member.id !== userId) &&
                                <div className="deleteMember">
                                    <button
                                        id="removeUserButton"
                                        onClick={deleteMember}
                                    > Remove member</button>
                                </div>
                            }
                        </div>
                    }
                </main>
            </div>
        </div>
    );
}

export default Members;