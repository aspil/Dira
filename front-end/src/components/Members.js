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
    const [isAdmin, setIsAdmin] = useState(false);
    const [tableError, setTableError] = useState('');
    const [deleteMemberError, setDeleteMemberError] = useState('');
    const [addMemberError, setAddMemberError] = useState('');
    const [newMember, setNewMember] = useState('');

    const hideAddMember = () => {
        handleMembersPopup("hide");
    }
    const showAddMember = () => {
        setAddMemberError('');
        handleMembersPopup("show");
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
        setDeleteMemberError('');

        projectClientRef.current.delete_user_from_project_with_id(projectId, current_member.id).then((res) => {
            console.log(res);
            fetchMemberPermissions();
            fetchMembers();
        }).catch((err) => {
            console.log(err);
            setDeleteMemberError('Couldn\'t delete the member you specified');
        });
    }
    const addMember = (e) => {
        e.preventDefault();
        if (!newMember) {
            setAddMemberError('Please fill in the field');
            return;
        }
        setAddMemberError('');

        projectClientRef.current.add_user_to_project_with_id(projectId, newMember).then((res) => {
            console.log(res);
            fetchMemberPermissions();
            fetchMembers();
        }).catch((err) => {
            console.log(err);
            setAddMemberError('Couldn\'t add the member you specified');
        });
    }

    const checkForMembersFetchError = () => {
        if (members.length === 0 || memberPermissions.length === 0) {
            setTableError('Couldn\'t retrieve members from server');
        }
        else {
            setTableError('');
        }
    }
    useEffect(checkForMembersFetchError, [members, memberPermissions]);

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
                            {
                                !Boolean(tableError)
                                &&
                                <button
                                    onClick={showAddMember}
                                    style={isAdmin ? {} : { display: 'none' }}
                                >+ Add Member</button>
                            }
                            <div className="table_wrapper">
                                {
                                    Boolean(tableError) &&
                                    <p style={{ color: 'crimson', marginBottom: '1em' }}>{tableError}</p>
                                }
                                {
                                    Boolean(deleteMemberError) &&
                                    <p style={{ color: 'crimson', marginBottom: '1em' }}>{deleteMemberError}</p>
                                }
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
                                <img src={x_icon} alt="accountIcon" onClick={hideAddMember}></img>
                            </div>
                            <form className="add_member_form" onSubmit={addMember} noValidate>
                                <input
                                    id="memberEmail"
                                    type="text"
                                    placeholder="Email Address"
                                    value={newMember}
                                    onChange={(e) => { setNewMember(e.target.value); }}
                                />
                                {
                                    Boolean(addMemberError) &&
                                    <p style={{ color: 'crimson', marginBottom: '1em' }}>{addMemberError}</p>
                                }
                                <button>Add</button>
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