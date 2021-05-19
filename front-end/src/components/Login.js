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
                <a href="/Contact" >Forgot Password?</a>
                </div>

                <p><button>Login</button></p>
                <p style={{margin: "15px"}}>Don't have an account? <a href="/Register">Register</a></p>
                <a href="/Contact">Contact Us</a>
            </div>
        </div>

      </div>
    );
  }
   
  export default Login;