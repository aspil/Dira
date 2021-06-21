import accountIcon from "../Images/profile_icon.png"
import { Link, useHistory } from "react-router-dom";
import { useState } from "react";


const ProjectNav = ({ username, doLogout }) => {
  const history = useHistory();

  const [settingsState, setSettingsState] = useState("hide");

  const handleAccountClick = () => {
    if(settingsState == "hide"){
      setSettingsState("show");
    }
    else{
      setSettingsState("hide");
    }
  }

  const logout = () => {
    setSettingsState("hide");
    doLogout();
  }
  const goToPricing = () => {
    setSettingsState("hide");
    history.push('/pricing');
  }

  return (
    <div className="projectnav">
      <Link to="/proj_main" className="dira_link">Dira</Link>
      <div className="right_links">
        <Link to="/create_project">+ Create a Project</Link>
        <Link to="/proj_main">My Projects</Link>
        <Link to="/pricing" className="boxxed_button">Go Premium</Link>
        <img src={accountIcon} alt="accountIcon" className="settingsPopupReference" onClick={handleAccountClick}></img>
      </div>

      {settingsState === "show" &&
        <div className="form-popup" id="myForm">
          <form className="form-container">
            <p id="accountName" ><strong>{username}</strong></p>
            <div className="accountLinkWrapper" onClick={goToPricing}>
              <p
                type="button"
                className="accountLink">
                Upgrade
              </p>
            </div>
            <div className="accountLinkWrapper" onClick={logout}>
              <p
                type="button"
                className="accountLink">
                Logout
              </p>
            </div>
          </form>
        </div>
      }


    </div>
  );
}

export default ProjectNav;


