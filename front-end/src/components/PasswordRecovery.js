import logo from "../Images/dira_icon_cropped.png"
import arrows from "../Images/arrows.png"
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";



const PasswordRecovery = ({ navHandle, userClientRef }) => {

  const [step, setStep] = useState(1);
  const history = useHistory();
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [pin, setPin] = useState('');
  const [recoveryError, setRecoveryError] = useState('');

  const sendEmail = () => {
    if (!email) {
      setEmailError('Please fill in email field');
      return;
    }
    else if (!email.includes('@') || email.indexOf('@') === 0 || email.indexOf('@') === (email.length - 1)) {
      setEmailError('Invalid email format');
      return;
    }
    setEmailError('');

    userClientRef.current.reset_password(email)
      .then(() => {
        setStep(2);
      })
      .catch(err => {
        console.log(err);
        setEmailError('Couldn\'t send recovery email');
      });
  };

  const resetPassword = () => {
    if (!(pin && password && confirmPassword)) {
      setRecoveryError('Please fill in all fields');
      return;
    }
    else if (password !== confirmPassword) {
      setRecoveryError('Passwords don\'t match');
      return;
    }
    setRecoveryError('');

    // TO-DO
    setRecoveryError('Not Available');
  }

  useEffect(navHandle, [navHandle]);

  return (
    <div className="login">
      <div style={{ textAlign: "center" }}>
        <img src={logo} alt="dira logo" id="dira logo" onClick={() => history.push('/')} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <img src={arrows} alt="arrows" style={{ width: "90px" }} />
          <h1 style={{ fontWeight: "normal", marginBottom: "40px" }}>Password Recovery</h1>
          {step === 1 &&
            <div>
              <p className="inputHead" style={{ textAlign: 'left' }}>Enter e-mail address:</p>
              <input
                type="text"
                placeholder="E-mail"
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
              {Boolean(emailError) && <p style={{ color: 'crimson' }}>{emailError}</p>}
              <p><button onClick={sendEmail}>Continue</button></p>
            </div>
          }
          {step === 2 &&
            <div>
              <p className="inputHead" style={{ textAlign: 'left' }}>Pin inside email:</p>
              <input
                type="text"
                placeholder="Pin"
                value={pin}
                onChange={e => setPin(e.target.value)}
              />
              <p className="inputHead" style={{ textAlign: 'left' }}>New password:</p>
              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
              <p className="inputHead" style={{ textAlign: 'left' }}>Confirm password:</p>
              <input
                type="password"
                placeholder="Password"
                value={confirmPassword}
                onChange={e => setConfirmPassword(e.target.value)}
              />
              {Boolean(recoveryError) && <p style={{ color: 'crimson' }}>{recoveryError}</p>}
              <p><button onClick={resetPassword} >Update Password</button></p>
            </div>
          }
        </div>
      </div>
    </div>
  );
}

export default PasswordRecovery;
