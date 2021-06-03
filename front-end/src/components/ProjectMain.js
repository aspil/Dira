import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useEffect, useState } from "react";
import { useHistory } from 'react-router';

const ProjectMain = ({ userInfo, userClient, token }) => {
  const [listState, setListState] = useState("showProjects");
  const history = useHistory()
  const [projects, setProjects] = useState([]);

  const swapList = () => {
    if(listState=="showProjects"){
      setListState("showIssues");
    }
    else{
      setListState("showProjects");
    }
  }

  useEffect(() => {
    userClient.get_user_projects(userInfo.id)
      .then((res) => {
        setProjects(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);



  const [issues, setIssues] = useState([
    { title: 'Issue x', project: '14/5/2021', sprint: 'sprint X', status: "active", id: 1 },
    { title: 'Issue X', project: 'DD/MM/YYYY', sprint: 'sprint X', status: "active", id: 2 },
    { title: 'Issue x', project: '3/8/2022', sprint: 'sprint X', status: "active", id: 3 }
  ])

  return (
    <div className="projectmain">
      <ProjectNav username={userInfo.username}/>

      <div className = "leftPanel" style={{width:"60%", margin:"0.5%"}}>
{/* projectButtons */}
        <div className = "projectButtons">
          <button onClick={() => {history.push("/create_project")}}> + New Project</button>
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
              <th>Name</th>
              <th>Description</th>
              <th>Key</th>
            </tr>
            {projects.map(project => (
              <tr onClick={() => {history.push(`/backlog/${project.id}`)}} key={project.id}>
                  <td>{project.name}</td>
                  <td>{project.description}</td>
                  <td>{project.key}</td>
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
{/* Footer */}
      <div style={{clear: "both", position:"absolute", bottom:"0", width:"100%"}}>
        <Footer/>
      </div>

    </div>
  );
}
 
export default ProjectMain;

