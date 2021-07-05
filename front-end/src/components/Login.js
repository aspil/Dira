import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon_cropped.png"
import { useEffect, useState } from "react";


const Login = ({ setToken, userClientRef, setUserInfo, setIsLogged, navHandle, setStayLogged, stayLogged }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errMessage, setErrMessage] = useState('');
  const history = useHistory();

  useEffect(navHandle, [navHandle]);

  const onSubmit = (e) => {
    e.preventDefault();
    setErrMessage('');

    userClientRef.current
      .login_user(username, password)
      .then((user) => {
        setUserInfo({
          username: user.username,
          email: user.email,
          plan: user.subscriptionPlan,
          id: user.id
        });
        setToken(user.token);
        setIsLogged(true);
        history.push('/proj_main');
      })
      .catch((err) => {
        setErrMessage('Failed to login with given credentials');
        console.log(err);
      });
  }
  const redirectToMain = () => {
    history.push('/')
  }
  return (
    <div className="login" >
      <div style={{ textAlign: "center" }}>
        <img src={logo} alt="dira logo" id="dira logo" onClick={redirectToMain} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <h1 style={{ fontWeight: "normal", margin: "15px" }}>Login</h1>
          <form onSubmit={onSubmit}>
            <div style={{ textAlign: "left" }}>
              <p className="inputHead">Username:</p>
              <input
                type="text"
                placeholder="Username"
                onChange={(e) => { setUsername(e.target.value) }}
                required
                value={username} />
              <p className="inputHead">Password:</p>
              <input
                type="password"
                placeholder="Password"
                onChange={(e) => { setPassword(e.target.value) }}
                required
                value={password} />
            </div>
            {Boolean(errMessage) && <p style={{ "color": "crimson" }}>{errMessage}</p>}
            <button type="submit">Login</button>
          </form>
          <label htmlFor='stay_logged' style={{ alignItems: "center" }}>
            <input
              type="checkbox"
              id='stay_logged'
              defaultChecked={stayLogged}
              onClick={() => { setStayLogged(!stayLogged) }}
            />
            Stay Logged in
          </label>

          <div style={{ textAlign: "right", marginRight: "10px" }}>
            <Link to="/recover" >Forgot Password?</Link>
          </div>

          <p style={{ margin: "15px" }}>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
      </div>

    </div>
  );
}

export default Login;