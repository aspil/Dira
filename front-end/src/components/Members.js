import SideNav from './SideNav';
import { useEffect, useState } from "react";
import x_icon from "../Images/x_icon.png"
import { useParams } from 'react-router';
import edit_icon from "../Images/edit_icon.png"
import trashcan_icon from "../Images/trashcan_icon.png"
import { useRef } from 'react';
import { CardMembershipRounded } from '@material-ui/icons';


const Members = ({ footerHandle, projectClient, userId }) => {
    // Add member popup handlers
    const [add_members_popup, handleMembersPopup] = useState("hide");
    const [members, setMembers] = useState([]);
    const [memberPermissions, setMemberPermissions] = useState([]);
    const [projectName, setProjectName] = useState('');
    // Edit member popup handlers
    const [edit_member_popup, handleEditMember] = useState("hide");
    const [current_member, handleCurrentMember] = useState([]);

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
        const userPerms = memberPermissions.find(perm => perm.id === userId);
        if (userPerms) {
            setIsAdmin(Boolean(userPerms.permissions.find(p => p === 'ADMIN')));
        }
    }, [memberPermissions, userId]);

    const { projectId } = useParams();

    const fetchMembers = () => {
        projectClient.get_all_users_in_project_by_id(projectId).then((res) => {
            console.log(res);
            setMembers(res.users);
        }).catch((err) => {
            console.log(err);
        });
    }
    useEffect(fetchMembers, []);

    useEffect(() => {
        projectClient.get_project_by_id(projectId).then(res => {
            // console.log(res);
            setProjectName(res.name);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    const fetchMemberPermissions = () => {
        projectClient.get_project_permissions_for_all_users(projectId).then(res => {
            setMemberPermissions(res.map(customer => {
                const toSave = { id: customer.customerId };
                toSave.permissions = [];

                const p = [
                    {
                        bit: 0b0001,
                        string: 'READ'
                    },
                    {
                        bit: 0b0010,
                        string: 'WRITE'
                    },
                    {
                        bit: 0b0100,
                        string: 'DELETE'
                    },
                    {
                        bit: 0b1000,
                        string: 'ADMIN'
                    },
                ];

                p.forEach(perm => {
                    const hasPerm = perm.bit & customer.permission;
                    hasPerm && toSave.permissions.push(perm.string);
                })

                return toSave;
            }));
        }).catch(err => {
            console.log(err);
        })
    }
    useEffect(fetchMemberPermissions, []);

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
                            <button onClick={show_members_popup}>+ Add Member</button>
                            <div className="table_wrapper">
                                <table id="main_table">
                                    <tr>
                                        <th>Username</th>
                                        <th>Fullname</th>
                                        <th>Permissions</th>
                                        {isAdmin &&
                                            <th></th>
                                        }


                                    </tr>
                                    {members.map(member => (
                                        <tr key={member.id}>
                                            <td>{member.username}</td>
                                            <td>{member.name} {member.surname}</td>
                                            <td>{memberPermissions.length && memberPermissions.find(perms => perms.id === member.id).permissions.toString()}</td>
                                            {isAdmin &&
                                                <td style={{ width: "40px", padding: "0", textAlign: "center" }}>
                                                    <img id="pencilIcon" src={edit_icon} alt="Pencil" onClick={() => showEditMember(member)}></img>
                                                </td>
                                            }
                                        </tr>
                                    ))}
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
                            <div className="deleteMember">
                                <button id="removeUserButton"> Remove member</button>
                            </div>
                        </div>
                    }
                </main>
            </div>
        </div>
    );
}

export default Members;