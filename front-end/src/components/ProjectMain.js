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

  const [projects, setProjects] = useState([
    { title: 'Project Facebook', due_date: '14/5/2021', type: 'public', id: 1 },
    { title: 'Mars Landing 2030', due_date: 'DD/MM/YYYY', type: 'private', id: 2 },
    { title: 'KFC is coming to Greece', due_date: '3/8/2022', type: 'public', id: 3 }
  ])

  const [issues, setIssues] = useState([
    { title: 'Issue x', project: '14/5/2021', sprint: 'sprint X', status: "active", id: 1 },
    { title: 'Issue X', project: 'DD/MM/YYYY', sprint: 'sprint X', status: "active", id: 2 },
    { title: 'Issue x', project: '3/8/2022', sprint: 'sprint X', status: "active", id: 3 }
  ])

  return (
    <div className="projectmain">
      <ProjectNav/>
      <div className="center">
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
              <button className="pressedButton" style={{borderTopLeftRadius:"5px"}}>My Projects</button>
              <button className="unpressedButton"style={{borderTopRightRadius:"5px"}} onClick={swapList}>My Recet Issues</button>
            </div>
            }
            { listState == "showIssues" &&
              <div>
              <button className="unpressedButton" style={{borderTopLeftRadius:"5px"}} onClick={swapList}>My Projects</button>
              <button className="pressedButton"style={{borderTopRightRadius:"5px"}}>My Recet Issues</button>
            </div>
            }
          </div>
          {/* tables */}
          { listState == "showProjects" &&
          <table id = "main_table">
            <tr>
              <th>Title</th>
              <th>Due Date</th>
              <th>Type</th>
            </tr>
            {projects.map(project => (
                <tr key={project.id}>
                  <td>{project.title}</td>
                  <td>{project.due_date}</td>
                  <td>{project.type}</td>
                </tr>
            ))}
          </table>
          }
          { listState == "showIssues" &&
          <table id = "main_table">
            <tr>
              <th>Title</th>
              <th>Project</th>
              <th>Sprint</th>
              <th>Status</th>
            </tr>
            {issues.map(issue => (
                <tr key={issue.id}>
                  <td>{issue.title}</td>
                  <td>{issue.project}</td>
                  <td>{issue.sprint}</td>
                  <td>{issue.status}</td>
                </tr>
            ))}
          </table>
          }

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