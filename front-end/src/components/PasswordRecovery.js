import logo from "../Images/dira_icon.png"
import arrows from "../Images/arrows.png"
import { useState } from "react";
import { Link } from "react-router-dom";



const PasswordRecovery = () => {
  
  const [step, setStep] = useState(1);

  const sendEmail = () => {
    setStep(2);
  }
  const checkCode = () => {
    setStep(3);
  }
  const resetPassword = () => {
    setStep(4);
  }
  
  return (
    <div className="password_recovery">
      <div style={{textAlign:"center"}}>
          <img src={logo} alt="dira logo" id="dira_logo" style={{margin:"-15vh"}}/>
          <div className="login_grad" style={{textAlign:"center"}}>
          <img src={arrows} alt="arrows"style={{width:"6vw",marginTop:"1vh"}}/>
          <h1 style={{fontWeight:"normal", margin:"15px"}}>Password Recovery</h1>
          <p style={{textAlign:'left', marginLeft:"1.8vw", marginBottom:"-1vh"}}>Enter your new password:</p>

          {step === 1 && 
            <div>
            <input type="text" placeholder="E-mail"></input>
            <p><button onClick={sendEmail}>Continue</button></p>
            </div>
          }   
          {step === 2 && 
            <div>
            <input type="text" placeholder="Code"></input>
            <p><button onClick={checkCode}>Continue</button></p>
            </div>
          }
          {step === 3 && 
            <div>
            <input type="text" placeholder="Password"></input>
            <p style={{textAlign:'left', marginLeft:"1.8vw", marginBottom:"-1vh"}}>Confirm your password:</p>
            <input type="text" placeholder="Confirm Password"></input>
            <p><button onClick={resetPassword} style={{fontSize:"1vw"} }>Update Password</button></p>
            </div>
          }
          <Link to="/contact">Contact Us</Link>

          </div>
      </div>
    </div>
  );
}
   
  export default PasswordRecovery;
