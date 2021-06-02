import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon.png"
import { useState } from "react";


const Login = ({ setToken, client, setUserInfo }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();

  const onSubmit = (e) => {
    e.preventDefault();

    client
      .login_user(username, password)
      .then((user) => {
        console.log(user);
        localStorage.jwtoken = user.token;
        setUserInfo({
          username: user.username,
          email: user.email,
          id: user.id
        })
        setToken(user.token);
        history.push('/proj_main');
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
                  type="text" 
                  placeholder="Username" 
                  onChange = {(e) => {setUsername(e.target.value)}}
                  value={username}/>
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