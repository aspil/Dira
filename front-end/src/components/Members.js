import SideNav from './SideNav';
import { useEffect, useState } from "react";
import x_icon from "../Images/x_icon.png"
import { useParams } from 'react-router';


const Members = ({ username, doLogout, footerHandle }) => {

    const [members_popup, handleMembersPopup] = useState("hide");

    const hide_members_popup = () => {
        handleMembersPopup("hide");
    }

    const show_members_popup = () => {
        handleMembersPopup("show");
    }

    const handlePopupButtonClick = () => {
        hide_members_popup();
    }


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
                            <button onClick={show_members_popup}>+ Add Members</button>
                            <div className="table_wrapper">
                                <table id="main_table">
                                    <tr>
                                        <th>Name</th>
                                        <th>Date Joined</th>
                                        <th>Role</th>
                                    </tr>
                                    {members.map(member => (
                                        <tr key={member.id}>
                                            <td>{member.name}</td>
                                            <td>{member.dateJoined}</td>
                                            <td>{member.role}</td>
                                        </tr>
                                    ))}
                                </table>
                            </div>
                        </div>
                    </div>
                    {/* Popup */}
                    {members_popup === "show" &&
                        <div className="add_members_popup">
                            <div>
                                <h2>Add Members</h2>
                                <img src={x_icon} alt="accountIcon" onClick={hide_members_popup}></img>
                            </div>
                            <form className="members_form">
                                <input type="text" placeholder="Email Address"></input>
                                <input type="text" placeholder="Email Address"></input>
                                <input type="text" placeholder="Email Address"></input>
                                <button onClick={handlePopupButtonClick}>Add</button>
                            </form>
                        </div>
                    }
                </main>
            </div>
        </div>
    );
}

export default Members;