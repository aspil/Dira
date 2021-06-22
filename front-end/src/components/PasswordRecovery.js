import logo from "../Images/dira_icon_cropped.png"
import arrows from "../Images/arrows.png"
import { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";



const PasswordRecovery = ({ navHandle }) => {

  const [step, setStep] = useState(1);
  const history = useHistory();

  const sendEmail = () => {
    setStep(2);
  }
  // const checkCode = () => {
  //   setStep(2);
  // }
  const resetPassword = () => {
    setStep(3);
  }

  const redirectToMain = () => {
    history.push('/')
  }
  useEffect(navHandle, [navHandle]);

  return (
    <div className="login">
      <div style={{ textAlign: "center" }}>
      <img src={logo} alt="dira logo" id="dira logo" onClick={redirectToMain} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <img src={arrows} alt="arrows" style={{ width: "90px"}} />
          <h1 style={{ fontWeight: "normal", marginBottom: "40px" }}>Password Recovery</h1>
          {step === 1 &&
            <div>
              <p className="inputHead" style={{ textAlign: 'left'}}>Enter e-mail address:</p>
              <input type="text" placeholder="E-mail"></input>
              <p><button onClick={sendEmail}>Continue</button></p>
            </div>
          }
          {/* {step === 2 &&
            <div>
              <p className="inputHead" style={{ textAlign: 'left'}}>Enter e-mail address:</p>
              <input type="text" placeholder="Code"></input>
              <p><button onClick={checkCode}>Continue</button></p>
            </div>
          } */}
          {step === 2 &&
            <div>
              <p className="inputHead" style={{ textAlign: 'left'}}>New password:</p>
              <input type="text" placeholder="Password"></input>
              <p className="inputHead" style={{ textAlign: 'left'}}>Confirm password:</p>
              <input type="text" placeholder="Confirm Password"></input>
              <p><button onClick={resetPassword} >Update Password</button></p>
            </div>
          }
        </div>
      </div>
    </div>
  );
}

export default PasswordRecovery;
