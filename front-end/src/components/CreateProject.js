import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon_cropped.png"
import questionmark from "../Images/questionmark_icon.png"

import { useState } from "react";



const CreateProject = ({ projectClient, userPlan }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [key, setKey] = useState("");
  const [visibility, setVisibility] = useState("PUBLIC");
  const history = useHistory();

  function myFunction() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
  }
  const redirectToMain = () => {
    history.push('/')
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    projectClient.create_project({
      "description": description,
      "key": key,
      "name": name,
      "visibility": visibility
    })
      .then(res => {
        console.log(res);
        history.push('proj_main');
      })
      .catch((err) => console.log(err));
  }

  return (
    <div className="createProject" >
      <div style={{ textAlign: "center" }}>
        <img src={logo} alt="dira logo" id="dira_logo" onClick={redirectToMain} />
        <div className="login_grad">
          <h1 style={{ fontWeight: "normal", margin: "15px" }}>Create New Project</h1>
          <form onSubmit={handleSubmit}>
            <div style={{ textAlign: "left" }}>
              <p>Name:</p>
              <input className="textInput" type="text" placeholder="Project Name"
                value={name} onChange={(e) => setName(e.target.value)} />
              <p>Description:</p>
              <input className="textInput" type="text" placeholder="Project Description"
                value={description} onChange={(e) => setDescription(e.target.value)} />
              <p>Key:</p>
              <div style={{ display: "flex", alignItems: "center" }}>
                <input className="textInput" id="projectKey" placeholder="Project Key"
                  value={key} onChange={(e) => setKey(e.target.value)} />
                <img src={questionmark} alt="questionmark" id="questionmark" onClick={myFunction} />
                <div className="questionmark_popup" >
                  <span className="popuptext" id="myPopup">The keyword for your Project, e.g. 'PM' for 'Project Mars'.</span>
                </div>
              </div>
              <br />
              <p>Access:</p>
              <div>
                <div style={{ display: "flex", alignItems: "center" }}>
                  <div className="accessOptions">
                    <input className="accessInput" type="radio" id="public"
                      name="visibility" value="PUBLIC" defaultChecked
                      onClick={(e) => setVisibility(e.target.value)}
                    />
                    <label htmlFor="public">Public</label>
                  </div>
                  <div className="accessOptions">
                    <input className="accessInput" type="radio" id="private"
                      name="visibility" value="PRIVATE"
                      onClick={(e) => setVisibility(e.target.value)}
                      disabled={userPlan === "STANDARD"}
                    />
                    <label htmlFor="private" style={userPlan === "STANDARD" ? { opacity: "0.5" } : {}}>Private</label>
                  </div>
                </div>
                {userPlan === "STANDARD" && <p style={{ fontWeight: "normal" }}><Link to="/pricing" style={{ color: "blue" }}>Upgrade to Premium</Link> to create private projects.</p>}
              </div>
            </div>


            <button type="submit">Create Project</button>
          </form>
        </div>
      </div>

    </div>
  );
}

export default CreateProject;