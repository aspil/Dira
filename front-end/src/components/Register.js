import logo from "../Images/dira_icon_cropped.png"
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const Register = ({ userClientRef, navHandle, enteredEmail }) => {
  const { userEmail } = useParams();
  const [name, setName] = useState('')
  const [surname, setSurname] = useState('')
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState(userEmail || '')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const history = useHistory();
  const [error, setError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [errMessage, setErrMessage] = useState('');

  useEffect(navHandle, [navHandle]);

  const onSubmit = (e) => {
    e.preventDefault()

    if (password !== confirmPassword) {
      setError(false);
      setPasswordError(true);
      setErrMessage("Passwords don't match.");
      return;
    }
    setError(false);
    setPasswordError(false);

    userClientRef.current.register_user({
      "username": username,
      "name": name,
      "surname": surname,
      "email": email,
      "password": password,
      "subscriptionPlan": "STANDARD"
    }).then(res => {
      console.log(res);
      history.push('/');
    }).catch((err) => {
      console.log(err);
      if (err === undefined) {
        return;
      }
      if (err.errors) {
        setPasswordError(true);
        setErrMessage(err.errors[0].defaultMessage);
      }
      else {
        setError(true);
        setErrMessage(err.error.message);
      }
    });
  }

  const redirectToMain = () => {
    history.push('/')
  }
  return (
    <div className="login">
      <div style={{ textAlign: "center" }}>
        <img src={logo} alt="dira logo" id="dira logo" onClick={redirectToMain} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <h1 style={{ fontWeight: "normal", margin: "15px" }}>Register</h1>
          <form onSubmit={onSubmit}>
            {error && <p style={{ "color": "red" }}>{errMessage}</p>}
            <div style={{ textAlign: "left" }}>
              <p className="inputHead">Email Adress:</p>
              <input
                type="email" placeholder="Email" required
                value={email} onChange={(e) => { setEmail(e.target.value); }}
              />
              <p className="inputHead">Username:</p>
              <input
                type="text" placeholder="Username" required
                value={username} onChange={(e) => { setUsername(e.target.value); }}
              />
              {/* <input type="text" placeholder="Confirm Password" value={name}></input> */}
              <div>
                <div style={{ float: "left" }}>
                  <p className="inputHead">Name:</p>
                  <input
                    style={{ width: "144px" }}
                    type="text" placeholder="Name" required
                    value={name} onChange={(e) => { setName(e.target.value); }}
                  />
                </div>
                <div >
                  <p className="inputHead" style={{ marginLeft: "210px" }}>Surname:</p>
                  <input
                    style={{ width: "144px" }}
                    type="text" placeholder="Surname" required
                    value={surname} onChange={(e) => { setSurname(e.target.value); }}
                  />
                </div>
              </div>

              {passwordError && <p style={{ "color": "red" }}>{errMessage}</p>}
              <p className="inputHead">Password:</p>
              <input
                type="password" placeholder="Password" required
                value={password} onChange={(e) => { setPassword(e.target.value); }}
              />
              <p className="inputHead">Confirm Password:</p>
              <input
                type="password" placeholder="Confirm Password" required
                value={confirmPassword} onChange={(e) => { setConfirmPassword(e.target.value); }}
              />
            </div>
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