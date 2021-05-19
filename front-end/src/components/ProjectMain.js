import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";


import SideNav from './SideNav';
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
      <div className="center">
        <SideNav/>
        <div className="content">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Hic incidunt blanditiis, esse sequi nulla dicta et veniam tenetur corrupti earum ducimus vel, similique expedita sit voluptatum accusantium minus ex aliquid!
          </p>
        </div>
      </div>

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