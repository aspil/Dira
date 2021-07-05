import logo from "../Images/dira_icon.png"
import mainScreen from "../Images/main_screen.png"
import backlogScreen from "../Images/backlog_screen.png"
import reportsScreen from "../Images/reports_screen.png"

import { useHistory } from "react-router-dom";
import Footer from "./Footer";
import { useState } from "react";


function Home() {
  const history = useHistory();
  const [email, setEmail] = useState('');

  const handleStartForFreeButtonClick = (e) => {
    e.preventDefault();
    if (email) {
      history.push(`/register/${email}`);
    }
    else {
      history.push(`/register`);
    }
  }

  return (
    <div className="home">
      <div className="home_grad1">
        <img src={logo} alt="dira logo" id="dira logo" />

        <form onSubmit={handleStartForFreeButtonClick}>
          <input
            type="email"
            placeholder="Email Adress"
            value={email}
            onChange={e => setEmail(e.target.value)}
          />
          <button>Start for free</button>
        </form>
        <br /><br />
      </div>
      <br />
      <div style={{display:"flex",alignItems:"center"}}>
        <div className="leftContent">
          <img className="homeScreenshot" src={mainScreen} alt="mainScreen" id="mainScreen" />
        </div>
        <div className="rightContent">
          <span className="description">Track Everything</span>
        </div>
      </div>

      <br /><br /><br />

      <div style={{display:"flex",alignItems:"center",backgroundColor:"rgb(15, 98, 192)", padding:"40px 0px 40px 0px",borderRadius:"25px"}}>
        <div className="leftContent">
          <span className="description" style={{color:"white"}}>Break Down your Work</span>
        </div>
        <div className="rightContent">
          <img className="homeScreenshot" src={backlogScreen} alt="backlogScreen" id="backlogScreen"/>
        </div>
      </div>

      <div className="home_grad2">
      <br /><br /><br />

      <div style={{display:"flex",alignItems:"center"}}>
        <div className="leftContent">
          <img className="homeScreenshot" src={reportsScreen} alt="reportsScreen" id="reportsScreen" />
        </div>
        <div className="rightContent">
          <span className="description">Analyze and Improve</span>
        </div>
      </div>
      

        <div style={{ textAlign: "center" ,marginTop:"100px"}}>
          <button onClick={handleStartForFreeButtonClick}>Start for free</button>
        </div>
        <br /><br /><br /><br /><br /><br />

      </div>
      <Footer position={'fixed'}/>
    </div>
  );
}

export default Home;
