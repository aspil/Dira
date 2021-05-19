import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";


// import logo from "../Images/dira_icon.png"

const ProjectMain = () => {

  const [listState, setListState] = useState("showProjects");

  const swapList = () => {
    if(listState=="showProjects"){
      setListState("showIssues");
    }
    else{
      setListState("showProjects");
    }
  }

  return (
    <div className="projectmain">
      <ProjectNav/>

      <div className = "leftPanel" style={{width:"60%", margin:"0.5%"}}>
        {/* projectButtons */}
        <div className = "projectButtons">
          <button > + New Project</button>
          <button style={{backgroundColor:"black"}}>Join a Project</button>
        </div>
        <div style={{clear: "both"}}>
          {/* listButtons */}
          <div className = "listButtons">
            { listState == "showProjects" &&
              <div>
              <button className="pressedButton" onClick={swapList}>My Projects</button>
              <button className="unpressedButton" onClick={swapList}>My Recet Issues</button>
            </div>
            }
            { listState == "showIssues" &&
              <div>
              <button className="unpressedButton" onClick={swapList}>My Projects</button>
              <button className="pressedButton" onClick={swapList}>My Recet Issues</button>
            </div>
            }
            </div>
        </div>
      </div>

      <div style={{clear: "both"}}>
        <Footer/>
      </div>

    </div>
  );
}
 
export default ProjectMain;

// const Home = () => {
//   return (
//     <div>
//       Home page
//     </div>
//   );
// }
 
// export default Home;