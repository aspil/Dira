import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon.png"
import questionmark from "../Images/questionmark_icon.png"

import { useState } from "react";



const CreateProject = ({ projectClient, token }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [key, setKey] = useState("");
  const [visibility, setVisibility] = useState("PUBLIC");
  const history = useHistory();

  const premium_user = "no"
  function myFunction() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    projectClient.set_authorization_token(token);

    projectClient.create_project({
      "description": description,
      "key": key,
      "name": name,
      "visibility": visibility
    })
      .then(res => console.log(res))
      .catch(() => console.log('error'));

    history.push('proj_main');

  }

  return (
    <div className="createProject" >
      <div style={{textAlign:"center"}}>
          <img src={logo} alt="dira logo" id="dira_logo"/>
          <div className="login_grad">
              <h1 style={{fontWeight:"normal", margin:"15px"}}>Create New Project</h1>
              <form onSubmit={handleSubmit}>
                <div style={{textAlign:"left"}}>
                  <p>Name:</p>
                  <input className="textInput" type="text" placeholder="Project Name"
                  value={name} onChange={(e) => setName(e.target.value)}/>
                  <p>Description:</p>
                  <input className="textInput" type="text" placeholder="Project Description"
                  value={description} onChange={(e) => setDescription(e.target.value)}/>
                  <p>Key:</p>
                  <div style={{display:"flex", alignItems:"center"}}>
                    <input className="textInput" id="projectKey" placeholder="Project Key"
                      value={key} onChange={(e) => setKey(e.target.value)}/>
                    <img src={questionmark} alt="questionmark" id="questionmark" onClick={myFunction}/>
                    <div className="questionmark_popup" >
                      <span className="popuptext" id="myPopup">The keyword for your Project, e.g. 'PM' for 'Project Mars'.</span>
                    </div>
                  </div>
                  <br/>
                  <p>Access:</p>
                  {premium_user === "no" && 
                    <div>
                      <div style={{display:"flex", alignItems:"center"}}>
                        <div className = "accessOptions">  
                          <input className = "accessInput" type="radio" id="public"
                            name="visibility" value="PUBLIC" checked 
                          />
                          <label for="public">Public</label>
                        </div>
                        <div className = "accessOptions">
                          <input className = "accessInput" type="radio" id="private" 
                            name="visibility" value="private" disabled
                          />
                          <label for="private" style={{opacity:"0.5"}}>Private</label>
                        </div>
                      </div> 
                      <p style={{fontWeight:"normal"}}><Link to="/pricing">Upgrade to Premium</Link> to create private projects.</p>
                    </div>
                  }
                  {premium_user === "yes" && 
                    <div>
                      <div style={{display:"flex", alignItems:"center"}}>
                        <div className = "accessOptions">  
                          <input className = "accessInput" type="radio" id="public" 
                            name="visibility" value="PUBLIC" onClick={() => setVisibility("PUBLIC")} checked 
                          />
                          <label for="public">Public</label>
                        </div>
                        <div className = "accessOptions">
                          <input className = "accessInput" type="radio" id="private" 
                            name="visibility" value="PRIVATE" onClick={() => setVisibility("PRIVATE")}
                          />
                          <label for="private">Private</label>
                        </div>
                      </div> 
                    </div>
                  }
                </div>


                <button type="submit">Create Project</button>
              </form>
          </div>
      </div>

    </div>
  );
  }
   
  export default CreateProject;