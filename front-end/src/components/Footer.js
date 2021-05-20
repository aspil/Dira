import { Link } from "react-router-dom";
import logo from "../Images/dira_icon_white.png"

const Footer = () => {
    return (
      <div className="footer">
        <img src={logo} alt="dira logo" id="dira logo"/>
        <div className="links">
          <Link to="/">Features</Link>
          <Link to="/pricing">Pricing</Link>
          <Link to="/contact">Contact Us</Link>
        </div>
        <input type="text" placeholder="Email Adress" style={{margin:0,borderWidth:"thin"}}></input>
        <Link className="sub_link" to="/subscribe">SUBSCRIBE</Link>
        
      </div>
    );
  }
   
  export default Footer;