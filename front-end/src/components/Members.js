import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';
import x_icon from "../Images/x_icon.png"

const Members = () => {

    const [members, setMembers] = useState([
        { name: 'Makis', dateJoined: '14/5/2021', role: 'dev', id: 1 },
        { name: 'Takis', dateJoined: 'DD/MM/YYYY', role: 'admin', id: 2 },
        { name: 'Lakis', dateJoined: '3/8/2022', role: 'dev', id: 3 }
      ])

    return (
        <div className="members">
            <ProjectNav/>
            {/* <SideNav/> */}
            {/* <div className="content">
                <h1 id="project_name">KFC is coming to Greece</h1>
                <div className="main_panel">
                    <h1 id="team_members">Team Members</h1>
                    <button>+ Add Members</button>
                    <table id = "main_table">
                        <tr>
                        <th>Title</th>
                        <th>Due Date</th>
                        <th>Type</th>
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
            </div> */}
            <div className="add_members_popup">
                <div>
                <h2>Add Members</h2>
                <img src={x_icon} alt="accountIcon"></img>
                </div>
                <form className="members_form">
                <input type="text" placeholder="Email Address"></input>
                <input type="text" placeholder="Email Address"></input>
                <input type="text" placeholder="Email Address"></input>
                <button>Add</button>

                </form>
            </div>

            <div style={{clear: "both", position:"absolute", bottom:"0", width:"100%"}}>
                <Footer/>
            </div>
        </div>

    );
}
        
    export default Members;