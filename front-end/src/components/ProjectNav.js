import accountIcon from "../Images/profile_icon.png"
import { Link } from "react-router-dom";


const ProjectNav = ({ username, doLogout }) => {

  const openSettings = () => {
    if (document.getElementById("myForm").style.display === "none") {
      document.getElementById("myForm").style.display = "block";
    }
    else {
      document.getElementById("myForm").style.display = "none";
    }
  }
  return (
    <div className="projectnav">
      <Link to="/proj_main" className="dira_link">Dira</Link>
      <div className="right_links">
        <Link to="/create_project">+ Create a Project</Link>
        <Link to="/proj_main">My Projects</Link>
        <Link to="/pricing" className="boxxed_button">Go Premium</Link>
        <img src={accountIcon} alt="accountIcon" className="settingsPopupReference" onClick={openSettings}></img>
      </div>
      <div className="form-popup" id="myForm">
        <form className="form-container">
          <p style={{ textAlign: "center" }}><strong>{username}</strong></p>
          <button
            type="button"
            className="btn">
            Upgrade
          </button>
          <button
            type="button"
            className="btn"
            onClick={doLogout}>
            Logout
          </button>
        </form>
      </div>


    </div>
  );
}

export default ProjectNav;


