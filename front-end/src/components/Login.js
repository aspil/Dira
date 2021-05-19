import { Link } from "react-router-dom";
import logo from "../Images/dira_icon.png"

const Login = () => {
    return (
      <div className="login" >
        <div style={{textAlign:"center"}}>
            <img src={logo} alt="dira logo" id="dira logo"/>
            <div className="login_grad" style={{textAlign:"center"}}>
                <h1 style={{fontWeight:"normal", margin:"15px"}}>Login</h1>
                
                <input type="text" placeholder="Username or email address"></input>
                <input type="text" placeholder="Password"></input>
                <div style={{textAlign:"right", marginRight:"1.8vw"}}>
                <Link to="/recover" >Forgot Password?</Link>
                </div>

                <p><button>Login</button></p>
                <p style={{margin: "15px"}}>Don't have an account? <Link to="/register">Register</Link></p>
                <Link to="/contact">Contact Us</Link>
            </div>
        </div>

      </div>
    );
  }
   
  export default Login;