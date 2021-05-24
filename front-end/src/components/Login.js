import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon.png"
import dira_client from "dira-clients";
import { useState } from "react";

const client = new dira_client();
client.register_user(
  {
    "username": "tester",
    "name": "Tester",
    "surname": "Mc Tester",
    "email": "test@otenet.gr",
    "password": "12345678",
    "subscriptionPlan": "STANDARD"
}
)

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();

  const onSubmit = (e) => {
    e.preventDefault();

    client
      .login_user(email, password)
      .then((token) => {
        console.log(token);
        // history.push('/proj_main');
      })
      .catch(() => {
        alert('Error');
      });

  }
  return (
    <div className="login" >
      <div style={{textAlign:"center"}}>
          <img src={logo} alt="dira logo" id="dira logo"/>
          <div className="login_grad" style={{textAlign:"center"}}>
              <h1 style={{fontWeight:"normal", margin:"15px"}}>Login</h1>
              <form onSubmit={onSubmit} noValidate>
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