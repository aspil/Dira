import logo from "../Images/dira_icon.png"

const Register = () => {
    return (
      <div className="register">
        <div style={{textAlign:"center"}}>
            <img src={logo} alt="dira logo" id="dira logo"/>
            <div className="login_grad" style={{textAlign:"center"}}>
                <h1 style={{fontWeight:"normal", margin:"15px"}}>Register</h1>
                
                <input type="text" placeholder="Username or email address"></input>
                <input type="text" placeholder="Password"></input>
                <input type="text" placeholder="Confirm Password"></input>
                <br></br>
                <button>Create Account</button>
                <p style={{margin: "5px"}}>Already a member? <a href="/Login">Register</a></p>
                <a href="/Contact">Contact Us</a>
            </div>
        </div>

      </div>
    );
  }
   
  export default Register;