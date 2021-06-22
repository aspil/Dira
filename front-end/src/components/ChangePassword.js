import logo from "../Images/dira_icon_cropped.png"
import arrows from "../Images/arrows.png"
import { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";



const ChangePassword = ({ navHandle }) => {

  const history = useHistory();
  useEffect(navHandle, [navHandle]);

  const redirectToMain = () => {
    history.push('/')
  }
  const [currentPassword, setCurrentPassword] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const [confirmPasswordError, setConfirmPasswordError] = useState(false);
  const [errMessage, setErrMessage] = useState('');

  const onSubmit = (e) => {
    e.preventDefault()
  
    if(newPassword === confirmPassword){
      setConfirmPasswordError(false);
    }
    else{
      setConfirmPasswordError(true);
    }
  }


  return (
    <div className="login">
      <div style={{ textAlign: "center" }}>
      <img src={logo} alt="dira logo" id="dira logo" onClick={redirectToMain} />
        <div className="login_grad" style={{ textAlign: "center" }}>
          <h1 style={{ fontWeight: "normal", marginBottom: "40px" }}>Change Password</h1>
            <div>
              <form onSubmit={onSubmit}>

                <p className="inputHead" style={{ textAlign: 'left'}}>Current Password:</p>
                <input type="password" 
                placeholder="Current Password"
                value={currentPassword} onChange={(e) => { setCurrentPassword(e.target.value); }}
                >

                </input>
                <p className="inputHead" style={{ textAlign: 'left'}}>New password:</p>
                <input type="password" 
                placeholder="New Password"
                value={newPassword} onChange={(e) => { setNewPassword(e.target.value); }}
                >

                </input>
                <p className="inputHead" style={{ textAlign: 'left'}}>Confirm New Password:</p>
                <input type="password" 
                placeholder="Confirm New Password"
                value={confirmPassword} onChange={(e) => { setConfirmPassword(e.target.value); }}
                >
                </input>
                {confirmPasswordError && <p style={{ "color": "red" }}>Passwords don't match</p>}

                <p><button type="submit">Update Password</button></p>
              </form>
            </div>
        </div>
      </div>
    </div>
  );
}

export default ChangePassword;
