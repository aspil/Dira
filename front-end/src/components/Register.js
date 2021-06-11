import logo from "../Images/dira_icon.png"
import { Link, useHistory } from "react-router-dom";
import { useEffect, useState } from "react";

const Register = ({ client, navHandle }) => {
  const [name, setName] = useState('')
  const [surname, setSurname] = useState('')
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const history = useHistory();

  useEffect(() => {
    navHandle(false);

    return function cleanup() {
      navHandle(true);
    }
  }, [])

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
      history.push('/')
    }).catch(() => {
      alert('error');
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
          <form noValidate onSubmit={onSubmit}>
            <input
              type="text" placeholder="Email"
              value={email} onChange={(e) => { setEmail(e.target.value); }}
            />
            <input
              type="text" placeholder="Username"
              value={username} onChange={(e) => { setUsername(e.target.value); }}
            />
            <input
              type="password" placeholder="Password"
              value={password} onChange={(e) => { setPassword(e.target.value); }}
            />
            {/* <input type="text" placeholder="Confirm Password" value={name}></input> */}
            <input
              type="text" placeholder="Name"
              value={name} onChange={(e) => { setName(e.target.value); }}
            />
            <input
              type="text" placeholder="Surname"
              value={surname} onChange={(e) => { setSurname(e.target.value); }}
            />
            <br></br>
            <button type="submit">Create Account</button>
          </form>
          <p style={{ margin: "15px" }}>Already a member? <Link to="/sign_in">Sign in</Link></p>
          <Link to="/contact">Contact Us</Link>
        </div>
      </div>

    </div>
  );
}

export default Register;