const ProjectNav = () => {

  const open_settings = () => {
    // document.getElementById("myForm").style.display = "block";
    console.log("eeeeeo");
  }
  
  function closeForm() {
    document.getElementById("myForm").style.display = "none";
    console.log("eeeeeo");

  }
  open_settings();
  return (
    
    <div className="projectnav">
      {/* <h1>Dira</h1>
      <div className="links">
        <a href="/pricing" className="boxxed_button">Go Premium</a>
        <a href="/contact">Contact</a>
      </div> */}

      <button className="open-button" onClick={open_settings}>Open Form</button>
      {/* <div class="form-popup" id="myForm">
        <form action="/action_page.php" class="form-container">
          <h1>Login</h1>

          <label for="email"><b>Email</b></label>
          <input type="text" placeholder="Enter Email" name="email" required></input>

          <label for="psw"><b>Password</b></label>
          <input type="password" placeholder="Enter Password" name="psw" required></input>

          <button type="submit" class="btn">Login</button>
          <button type="button" class="btn cancel" onclick={closeForm}>Close</button>
        </form>
      </div> */}

      
    </div>
  );
}
 
export default ProjectNav;


