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
  
  function closeForm() {
    document.getElementById("myForm").style.display = "none";

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
        <form action="/action_page.php" class="form-container">
          <h1>Login</h1>
          <label for="email"><b>Email</b></label>
          <input type="text" placeholder="Enter Email" name="email" required></input>
          <label for="psw"><b>Password</b></label>
          <input type="password" placeholder="Enter Password" name="psw" required></input>
          <button type="submit" class="btn">Login</button>
          <button type="button" class="btn cancel" onClick={closeForm}>Close</button>
        </form>
      </div>

      
    </div>
  );
}
 
export default ProjectNav;


