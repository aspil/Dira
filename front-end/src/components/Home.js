import HomeNav from './HomeNav';

function Home() {
  return (
    <div className="home">
      <div className="home_grad1">
        <HomeNav/>
        
        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
          
          <form>
          <input type="text" placeholder="Email Adress"></input>
          <button>Start for free</button>
          </form>
          <br/><br/><br/>
      </div>

      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />

      <div className="home_grad2">

      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />

        <div style={{textAlign:"center"}}>
          <button>Start for free</button>
        </div>
        <br/><br/><br/>
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