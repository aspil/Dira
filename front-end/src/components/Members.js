import SideNav from './SideNav';
import { useEffect, useState } from "react";
import x_icon from "../Images/x_icon.png"
import { useParams } from 'react-router';
import edit_icon from "../Images/edit_icon.png"
import trashcan_icon from "../Images/trashcan_icon.png"


const Members = ({ username, doLogout, footerHandle }) => {
    // Add member popup handlers
    const [add_members_popup, handleMembersPopup] = useState("hide");
    
    const hide_members_popup = () => {
        handleMembersPopup("hide");
    }
    const show_members_popup = () => {
        handleMembersPopup("show");
    }
    const handleAddMemberButtonClick = () => {
        hide_members_popup();
    }
    

    // Edit member popup handlers
    const [edit_member_popup, handleEditMember] = useState("hide");
    const [current_member, handleCurrentMember] = useState([]);

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
    //
    const isAdmin = "yes";

    const [members, setMembers] = useState([
        { name: 'Makis', dateJoined: '14/5/2021', role: 'developer', id: 1 },
        { name: 'Takis', dateJoined: '2/11/2019', role: 'admin', id: 2 },
        { name: 'Lakis', dateJoined: '3/8/2018', role: 'developer', id: 3 },
        { name: 'Akis', dateJoined: '16/5/2020', role: 'developer', id: 4 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
        { name: 'Papadakis', dateJoined: '25/3/2021', role: 'developer', id: 5 },
    ])

    useEffect(footerHandle, [footerHandle]);

    return (
        // Gia na doylepsei to sidebar
        <div className="members proj_page">
            <div className="center_content">
                <SideNav />
                <main>
                    {/* Content */}
                    <div className="content">
                        <h1 id="project_name">KFC is coming to Greece</h1>
                        {/* Main Panel */}
                        <div className="main_panel">
                            <h1 id="team_members">Team Members</h1>
                            <button onClick={show_members_popup}>+ Add Member</button>
                            <div className="table_wrapper">
                                <table id="main_table">
                                    <tr>
                                        <th>Name</th>
                                        <th>Date Joined</th>
                                        <th>Role</th>
                                        {isAdmin === "yes" &&
                                            <th></th>
                                        }


                                    </tr>
                                    {members.map(member => (
                                        <tr key={member.id}>
                                            <td>{member.name}</td>
                                            <td>{member.dateJoined}</td>
                                            <td>{member.role}</td>
                                            {isAdmin === "yes" &&
                                                <td style={{width:"40px",padding:"0",textAlign:"center"}}>
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
                            <div style={{display:"flex",justifyContent:"space-between"}}>
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
                                        <input id="checkbox" type="checkbox"/>
                                        Read Issues
                                    </label>
                                    <br />
                                    <label>
                                        <input id="checkbox" type="checkbox"/>
                                        Write Issues
                                    </label>
                                    <br />
                                    <label>
                                        <input id="checkbox" type="checkbox"/>
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