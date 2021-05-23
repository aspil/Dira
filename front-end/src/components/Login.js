import { Link } from "react-router-dom";
import logo from "../Images/dira_icon.png"
// import dira_client from "dira-clients";
import { useState } from "react";

// const client = 

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const onSubmit = (e) => {
    e.preventDefault();

    console.log(email, password);
  }
  return (
    <div className="login" >
      <div style={{textAlign:"center"}}>
          <img src={logo} alt="dira logo" id="dira logo"/>
          <div className="login_grad" style={{textAlign:"center"}}>
              <h1 style={{fontWeight:"normal", margin:"15px"}}>Login</h1>
              <form onSubmit={onSubmit}>
                <input 
                  type="email" 
                  placeholder="Email address" 
                  onChange = {(e) => {setEmail(e.target.value)}}
                  value={email}/>
                <input 
                  type="password" 
                  placeholder="Password" 
                  onChange = {(e) => {setPassword(e.target.value)}}
                  value={password}/>
                <button type="submit">Login</button>
              </form>
              <div style={{textAlign:"right", marginRight:"1.8vw"}}>
              <Link to="/recover" >Forgot Password?</Link>
              </div>

              <p style={{margin: "15px"}}>Don't have an account? <Link to="/register">Register</Link></p>
              <Link to="/contact">Contact Us</Link>
          </div>
      </div>

    </div>
  );
  }
   
  export default Login;