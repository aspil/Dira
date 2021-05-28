import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon.png"
import questionmark from "../Images/questionmark_icon.png"

import { useState } from "react";



const Create_project = () => {

  function myFunction() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
  }

  return (
    <div className="createProject" >
      <div style={{textAlign:"center"}}>
          <img src={logo} alt="dira logo" id="dira_logo"/>
          <div className="login_grad">
              <h1 style={{fontWeight:"normal", margin:"15px"}}>Create New Project</h1>
              <form>
                <div style={{textAlign:"left"}}>
                  <p>Name:</p>
                  <input className="textInput" type="text" placeholder="Project Name"/>
                  <p>Key:</p>
                  <div style={{display:"flex", alignItems:"center"}}>
                    <input className="textInput" id="projectKey" placeholder="Project Key"/>
                    <img src={questionmark} alt="questionmark" id="questionmark" onClick={myFunction}/>
                    <div className="questionmark_popup" >
                      <span className="popuptext" id="myPopup">The keyword for your Project, e.g. 'PM' for 'Project Mars'.</span>
                    </div>
                  </div>
                  <br/>
                  <p>Access:</p>
                  <div style={{display:"flex", alignItems:"center"}}>
                    <div className = "accessOptions">  
                      <input className = "accessInput" type="radio" name="gender" id="public" value="public"/>
                      <label for="public">Public</label>
                    </div>
                    <div className = "accessOptions">
                      <input className = "accessInput" type="radio" name="gender" id="private" value="private"/>
                      <label for="private">Private</label>
                    </div>
                  </div> 
                  <p style={{fontWeight:"normal"}}><Link to="/register">Upgrade to Premium</Link> to create private projects.</p>
                </div>
                <button type="submit">Create Project</button>
              </form>
          </div>
      </div>

    </div>
  );
  }
   
  export default Create_project;