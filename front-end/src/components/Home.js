import logo from "../Images/dira_icon.png"
import { useHistory } from "react-router-dom";
import { useEffect } from 'react';


function Home({ footerHandle }) {
  const history = useHistory();

  const handleStartForFreeButtonClick = () => {
    history.push('/sign_in')
  }
  useEffect(footerHandle, [footerHandle]);

  return (
    <div className="home">
      <div className="home_grad1">
        <img src={logo} alt="dira logo" id="dira logo" />

        <form>
          <input type="text" placeholder="Email Adress"></input>
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