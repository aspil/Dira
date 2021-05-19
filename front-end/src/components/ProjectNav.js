import accountIcon from "../Images/profile_icon.png"


const ProjectNav = () => {

  const openSettings = () => {
    if(document.getElementById("myForm").style.display == "none"){
      document.getElementById("myForm").style.display = "block";
    }
    else{
      document.getElementById("myForm").style.display = "none";
    }
  }

  // openSettings();
  return (
    
    <div className="projectnav">
      <h1>Dira</h1>
      <div className="links">
        <a href="/pricing" className="boxxed_button">Go Premium</a>
        <a href="/contact">Contact</a>
        <img src={accountIcon} alt="accountIcon" className="settingsPopupReference" onClick={openSettings}/>

        {/* <a  onClick={openSettings}>Open Form</a> */}
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


