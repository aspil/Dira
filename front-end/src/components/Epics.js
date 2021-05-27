import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';
import x_icon from "../Images/x_icon.png"

const Epics = () => {

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
  
  const [epics, setEpics] = useState([
      { name: 'Makis', dueDate: '14/5/2021', role: 'developer', id: 1 },
      { name: 'Takis', dueDate: '2/11/2019', role: 'admin', id: 2 },
      { name: 'Lakis', dueDate: '3/8/2018', role: 'developer', id: 3 },
      { name: 'Akis', dueDate: '16/5/2020', role: 'developer', id: 4 },
      { name: 'Papadakis', dueDate: '25/3/2021', role: 'developer', id: 5 },
      { name: 'Papadakis', dueDate: '25/3/2021', role: 'developer', id: 6 },
    ])

  return (
      // Gia na doylepsei to sidebar
  <div className="epics proj_page">
      <ProjectNav />
      <div className="center_content">
          <SideNav />
          <main>
              {/* Content */}
            <div id="content">
              <h1 id="project_name">KFC is coming to Greece</h1>
              {/* Main Panel */}
              <div className="main_panel">
                <h1 id="epics_header">Epics</h1>
                <button onClick={showCreateEpicPopup}> + Create Epic</button>
                <div className="table_wrapper">
                  <table id = "main_table">
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
                <div className = "epicPanel">
                <text id ="epicName">Epic Name</text>
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
                <br/>
                <text className="label" id="status">Status: </text>
                <text className="answer" id="statusAnswer">Open</text>
                <br/>
                <br/>
                <text className="label" id="creator">Created by: </text>
                <text className="answer" id="assigneeAnswer">Makis</text>
                <br/>
                <br/>
                <text className="label" id="dateCreated">Created on: </text>
                <text className="answer" id="dateCreatedAnswer">10/3/2021</text>
                <br/>
                <text className="label" id="DueDate">Due date: </text>
                <text className="answer" id="DueDateAnswer">2/5/2021</text>
                <br/>
                <text className="label" id="timeLeft">Time Remaining: </text>
                <text className="answer" id="timeLeftAnswer">5 days</text>
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
                          <input type="text" placeholder="Title"></input>
                          <textarea type="range" placeholder="Description"></textarea>
                          <p style={{textAlign:"left", fontWeight:"bold", marginBottom:"15px"}}>Select epic color:</p>
                          <div className="colors">
                            <div class="box brown"/>
                            <div class="box red"/>
                            <div class="box orange"/>
                            <div class="box yellow"/>
                            <div class="box green"/> 
                            <div class="box blue"/>
                            <div class="box pink"/>
                            <div class="box purple"/>
                            <div class="box black"/>

                          </div> 
                          <button onClick={handlePopupButtonClick}>Create</button>
                      </form>
                  </div>
              }   
          </main>
      </div>
      <Footer />
  </div>
  );
}
        
    export default Epics;