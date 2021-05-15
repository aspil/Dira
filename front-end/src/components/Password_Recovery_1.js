import logo from "../Images/dira_icon.png"
import arrows from "../Images/arrows.png"



const Password_Recovery_1 = () => {
    return (
      <div className="password_recovery">
        <div style={{textAlign:"center"}}>
            <img src={logo} alt="dira logo" id="dira logo"/>
            <div className="login_grad" style={{textAlign:"center"}}>
              
              <img src={arrows} alt="arrows"/>
              <h1 style={{fontWeight:"normal", margin:"15px"}}>Password Recovery</h1>
              <br></br>                
                <p style={{textAlign:'left', marginLeft:"1.8vw", marginBottom:"-1vh"}}>Enter your Email Adress:</p>
                <input type="text" placeholder="E-mail"></input>

                <p><button>Continue</button></p>
                <a href="/Contact">Contact Us</a>
            </div>
        </div>

      </div>
    );
  }
   
  export default Password_Recovery_1;