import { Link, useHistory } from "react-router-dom";
import logo from "../Images/dira_icon_cropped.png"
import { useEffect, useState } from "react";


const Login = ({ setToken, userClientRef, setUserInfo, setIsLogged, navHandle, setStayLogged, stayLogged }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [usernameError, setUsernameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [errMessage, setErrMessage] = useState('');
  const history = useHistory();

  useEffect(navHandle, [navHandle]);

  const onSubmit = (e) => {
    e.preventDefault();

    console.log(userClientRef.current);

    userClientRef.current
      .login_user(username, password)
      .then((user) => {
        console.log(user);
        setUserInfo({
          username: user.username,
          email: user.email,
          plan: user.subscriptionPlan,
          id: user.id
        });
        setToken(user.token);
        setIsLogged(true);
        setUsernameError(false);
        setPasswordError(false);
        history.push('/proj_main');
      })
      .catch((err) => {
        console.log(err);
        if (err.errors) {
          setPasswordError(true);
          setUsernameError(false);
          setErrMessage(err.errors[0].defaultMessage);
        }
        else {
          setUsernameError(true);
          setPasswordError(false);
          setErrMessage(err.error.message);
        }
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
              {usernameError && <p style={{ "color": "red" }}>{errMessage}</p>}
              <p className="inputHead">Password:</p>
              <input
                type="password"
                placeholder="Password"
                onChange={(e) => { setPassword(e.target.value) }}
                required
                value={password} />
            </div>
            {passwordError && <p style={{ "color": "red" }}>{errMessage}</p>}
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