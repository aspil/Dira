import { Link } from "react-router-dom";

const Footer = () => {
    return (
      <div className="footer">
        <h1 className="site_name">Dira</h1>
        <div className="links">
          <Link to="/">Features</Link>
          <Link to="/pricing">Pricing</Link>
          <Link to="/contact">Contact</Link>
        </div>
        <input type="text" placeholder="Email Adress" style={{margin:0,borderWidth:"thin"}}></input>
        <Link className="sub_link" to="/subscribe">SUBSCRIBE</Link>
        
      </div>
    );
  }
   
  export default Footer;