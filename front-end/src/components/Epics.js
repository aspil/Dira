import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useEffect, useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';
import x_icon from "../Images/x_icon.png"

const Epics = ({ footerHandle }) => {

  // Epic Info
  const [epic_panel, handleEpicInfo] = useState("hide");

  const showEpicInfo = () => {
    handleEpicInfo("show")
  }

  // Create Epic Popup
  const [create_epic_popup, handleCreateEpicPopup] = useState("hide");

  const hideCreateEpicPopup = () => {
    handleCreateEpicPopup("hide");
  }

  const showCreateEpicPopup = () => {
    handleCreateEpicPopup("show");
  }

  const handlePopupButtonClick = () => {
    hideCreateEpicPopup();
  }
  // Create Epic Colors
  // const [epic_color, handleNewEpicColor] = useState("none");

  // const selectNewEpicColor = (new_color) => {
  //   if (epic_color !== "none") {
  //     document.getElementById(epic_color).style.border = "none";
  //     document.getElementById(epic_color).style.padding = "1px";
  //     document.getElementById(epic_color).style.boxShadow = "none";

  //   }
  //   document.getElementById(new_color).style.border = "1px solid white";
  //   document.getElementById(new_color).style.padding = "0px";
  //   document.getElementById(new_color).style.boxShadow = "5px 5px 3px black";
  //   handleNewEpicColor(new_color);
  // }
  // Epics
  const [epics, setEpics] = useState([
    { name: 'Makis', dueDate: '14/5/2021', role: 'developer', id: 1 },
    { name: 'Takis', dueDate: '2/11/2019', role: 'admin', id: 2 },
    { name: 'Lakis', dueDate: '3/8/2018', role: 'developer', id: 3 },
    { name: 'Akis', dueDate: '16/5/2020', role: 'developer', id: 4 },
    { name: 'Papadakis', dueDate: '25/3/2021', role: 'developer', id: 5 },
    { name: 'Papadakis', dueDate: '25/3/2021', role: 'developer', id: 6 },
  ])

  useEffect(footerHandle, [footerHandle]);

  return (
    // Gia na doylepsei to sidebar
    <div className="epics proj_page">
      <div className="center_content">
        <SideNav />
        <main>
          {/* Content */}
          <div id="content">
            <h1 id="project_name">KFC is coming to Greece (o pinakas einai troll)</h1>
            {/* Main Panel */}
            <div className="main_panel">
              <h1 id="epics_header">Epics</h1>
              <button onClick={showCreateEpicPopup}> + Create Epic</button>
              <div className="table_wrapper">
                <table id="main_table">
                  <tr>
                    <th>Name</th>
                    <th>Date Joined</th>
                    <th>Role</th>
                  </tr>
                  {epics.map(epic => (
                    <tr key={epic.id} onClick={showEpicInfo}>
                      <td>{epic.name}</td>
                      <td>{epic.dueDate}</td>
                      <td>{epic.role}</td>
                    </tr>
                  ))}
                </table>
              </div>
            </div>
            {epic_panel === "show" &&
              <div className="epicPanel">
                <span id="epicName">Epic Name</span>
                <br></br>
                <br></br>
                <h3>Description</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit

                </p>
                <br />
                <span className="label" id="status">Status: </span>
                <span className="answer" id="statusAnswer">Open</span>
                <br />
                <br />
                <span className="label" id="creator">Created by: </span>
                <span className="answer" id="assigneeAnswer">Makis</span>
                <br />
                <br />
                <span className="label" id="dateCreated">Created on: </span>
                <span className="answer" id="dateCreatedAnswer">10/3/2021</span>
                <br />
                <span className="label" id="DueDate">Due date: </span>
                <span className="answer" id="DueDateAnswer">2/5/2021</span>
                <br />
                <span className="label" id="timeLeft">Time Remaining: </span>
                <span className="answer" id="timeLeftAnswer">5 days</span>
              </div>
            }
          </div>
          {/* Popup */}
          {create_epic_popup === "show" &&
            <div className="create_epic_popup">
              <div>
                <h2>Create Epic</h2>
                <img src={x_icon} id="xIcon" alt="accountIcon" onClick={hideCreateEpicPopup}></img>
              </div>
              <form className="create_epic_form">
                <input type="text" placeholder="Epic Title"></input>
                <textarea type="range" placeholder="Epic Description"></textarea>
                <div style={{ display: "flex" }}>
                  <span style={{fontWeight:"bold"}}>Priority: </span>
                  <select name="priority" id="priority" style={{ marginLeft: "10px" }}>
                    <option value="low">Low</option>
                    <option value="medium">Medium</option>
                    <option value="high">High</option>
                  </select>
                </div>
                {/* color stuff (unused)
                <br />
                <p style={{ textAlign: "left", fontWeight: "bold", marginBottom: "15px" }}>Select epic color:</p>
                <div className="colors">
                  <div class="box" id="brown" onClick={() => selectNewEpicColor("brown")} />
                  <div class="box" id="red" onClick={() => selectNewEpicColor("red")} />
                  <div class="box" id="orange" onClick={() => selectNewEpicColor("orange")} />
                  <div class="box" id="yellow" onClick={() => selectNewEpicColor("yellow")} />
                  <div class="box" id="green" onClick={() => selectNewEpicColor("green")} />
                  <div class="box" id="blue" onClick={() => selectNewEpicColor("blue")} />
                  <div class="box" id="pink" onClick={() => selectNewEpicColor("pink")} />
                  <div class="box" id="purple" onClick={() => selectNewEpicColor("purple")} />
                  <div class="box" id="black" onClick={() => selectNewEpicColor("black")} />
                </div> */}
                <button onClick={handlePopupButtonClick}>Create</button>
              </form>
            </div>
          }
        </main>
      </div>
    </div>
  );
}

export default Epics;