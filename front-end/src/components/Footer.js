import {Link} from "react-router-dom";
import logo from "../Images/dira_icon_white_cropped.png"

const Footer = ({position}) => {
    return (
        <div>
            {/*position at the bottom of the screen*/}
            {position !== 'fixed' ?
            <div className="footer">
                <img src={logo} alt="dira logo" id="dira logo"/>
                <div className="links">
                    <Link to="#">Features</Link>
                    <Link to="/pricing">Pricing</Link>
                </div>
            </div>
                // position at the bottom of the page
                :
                <div className="footer" style={{position:"initial",height:"8vh"}}>
                    <img src={logo} alt="dira logo" id="dira logo"/>
                    <div className="links">
                        <Link to="#">Features</Link>
                        <Link to="/pricing">Pricing</Link>
                    </div>
                </div>
            }
        </div>
    );
}

export default Footer;