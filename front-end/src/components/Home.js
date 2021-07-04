import logo from "../Images/dira_icon.png"
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
        <br /><br /><br />
      </div>

      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />

      <div className="home_grad2">

        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />

        <div style={{ textAlign: "center" }}>
          <button onClick={handleStartForFreeButtonClick}>Start for free</button>
        </div>
        <br /><br /><br /><br /><br /><br />

      </div>
      <Footer position={'fixed'}/>
    </div>
  );
}

export default Home;

// const Home = () => {
//   return (
//     <div>
//       Home page
//     </div>
//   );
// }

// export default Home;