import { Link } from "react-router-dom";
import logo from "../Images/dira_icon_white.png"

const Footer = () => {
  return (
    <div className="footer">
      <img src={logo} alt="dira logo" id="dira logo" />
      <div className="links">
        <Link to="#">Features</Link>
        <Link to="/pricing">Pricing</Link>
      </div>

    </div>
  );
}

export default Footer;