import logo from "../Images/dira_icon_cropped.png"
import { Link, useHistory } from "react-router-dom";
import { useEffect, useState } from "react";

const Register = ({ client, navHandle }) => {
  const [name, setName] = useState('')
  const [surname, setSurname] = useState('')
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const history = useHistory();
  const [error, setError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [errMessage, setErrMessage] = useState('');

  useEffect(navHandle, [navHandle]);

  const onSubmit = (e) => {
    e.preventDefault()

    client.register_user({
      "username": username,
      "name": name,
      "surname": surname,
      "email": email,
      "password": password,
      "subscriptionPlan": "STANDARD"
    }).then(res => {
      console.log(res);
      setError(false);
      setPasswordError(false);
      history.push('/')
    }).catch((err) => {
      console.log(err);
      if (err.errors) {
        setPasswordError(true);
        setError(false);
        setErrMessage(err.errors[0].defaultMessage);
      }
      else {
        setError(true);
        setPasswordError(false);
        setErrMessage(err.error.message);
      }
    })

  }
  const redirectToMain = () => {
    history.push('/')
  }
  return (
    <div className="register">
      <div style={{ textAlign: "center" }}>
        <img src={logo} alt="dira logo" id="dira logo" onClick={redirectToMain} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <h1 style={{ fontWeight: "normal", margin: "15px" }}>Register</h1>
          <form onSubmit={onSubmit}>
            {error && <p style={{ "color": "red" }}>{errMessage}</p>}
            <input
              type="email" placeholder="Email" required
              value={email} onChange={(e) => { setEmail(e.target.value); }}
            />
            <input
              type="text" placeholder="Username" required
              value={username} onChange={(e) => { setUsername(e.target.value); }}
            />
            {/* <input type="text" placeholder="Confirm Password" value={name}></input> */}
            <div>
              <input
                style={{ width: "125px" }}
                type="text" placeholder="Name" required
                value={name} onChange={(e) => { setName(e.target.value); }}
              />
              <input
                style={{ width: "125px" }}
                type="text" placeholder="Surname" required
                value={surname} onChange={(e) => { setSurname(e.target.value); }}
              />
            </div>
            {passwordError && <p style={{ "color": "red" }}>{errMessage}</p>}
            <input
              type="password" placeholder="Password" required
              value={password} onChange={(e) => { setPassword(e.target.value); }}
            />
            <br></br>
            <button type="submit">Create Account</button>
          </form>
          <p style={{ margin: "15px" }}>Already a member? <Link to="/sign_in">Sign in</Link></p>
        </div>
      </div>

    </div>
  );
}

export default Register;