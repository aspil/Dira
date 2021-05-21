import { Link } from "react-router-dom";

const HomeNav = () => {
  return (
    <div className="home_nav">
      <h1><Link to="/">Dira</Link></h1>
      <div className="links">
        <Link to="/">Features</Link>
        <Link to="/pricing">Pricing</Link>
        <Link to="/contact">Contact</Link>
        <Link to="/sign_in">Sign in</Link>
        <Link to="/register" id="boxxed_button">Sign up</Link>
      </div>
    </div>
  );
}
 
export default HomeNav;