import accountIcon from "../Images/profile_icon.png"
import { Link } from "react-router-dom";


const ProjectNav = () => {

  const openSettings = () => {
    if(document.getElementById("myForm").style.display == "none"){
      document.getElementById("myForm").style.display = "block";
    }
    else{
      document.getElementById("myForm").style.display = "none";
    }
  }
  return (
    <div className="projectnav">
      <Link to="/proj_main" className="dira_link">Dira</Link>
      <div className="right_links">
        <Link to="/new_project">+ New Project</Link>
        <Link to="/pricing" className="boxxed_button">Go Premium</Link>
        <img src={accountIcon} alt="accountIcon" className="settingsPopupReference" onClick={openSettings}></img>
      </div>
      <div class="form-popup" id="myForm">
        <form class="form-container">
          <p>"Username"</p>
          <button type="submit" class="btn">Upgrade</button>
          <button type="button" class="btn">Logout</button>
        </form>
      </div>

      
    </div>
  );
}
 
export default ProjectNav;


