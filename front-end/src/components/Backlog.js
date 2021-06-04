import x_icon from "../Images/x_icon.png"

import ProjectNav from './ProjectNav'
import Footer from './Footer'
import SideNav from './SideNav'
import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Search } from '@material-ui/icons'
import { DiraIssueClient } from "dira-clients";

const Backlog = ({ token }) => {


  
  const [backlogIssues, setBacklogIssues] = useState([
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue X', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue x', dateCreated:"10/5/2023", priority:"high", id: 3 }
  ])
  const [sprintIssues, setSprintIssues] = useState([
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 1 },
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 2 },
    { title: 'Issue y', dateCreated:"10/5/2023", priority:"high", id: 3 }
  ])


  const [sprint, handleSprintPanel] = useState("show");

  const [issue_panel, handleIssuePanel] = useState("hide");
  const showIssuePanel = () => {
    handleIssuePanel("show")
  }

  const [projectName, setProjectName] = useState('');

  const { projectId } = useParams();
  const issueClient = new DiraIssueClient(undefined, projectId);


  useEffect(() => {
    issueClient.set_authorization_token(token);

    issueClient.create_issue({
      "description": "balblalbal",
      "id": 0,
      "key": "jdjdjdjdjj",
      "labels": [
        "important",
        "kfc"
      ],
      "priority": 59,
      "title": "kfcjjjjkfc"
    })

    issueClient.get_all_issues()
      .then((res) => {
        console.log(res);
        setBacklogIssues(res.backlogIssues);
        setProjectName(res.name);
      })
      .catch(console.log);
  }, []);

  // Create issue popup handlers
  const [create_issue_popup, handleCreateIssuePopup] = useState("hide");
  const hideCreateIssuePopup = () => {
    handleCreateIssuePopup("hide");
  }
  const showCreateIssuePopup = () => {
    handleCreateIssuePopup("show");
  }
  const handlePopupButtonClick = () => {
    hideCreateIssuePopup();
  }


  const handleSubmit = (e) => {
    e.preventDefault();

    alert('click');
  }

  return (
    <div className="backlog proj_page">
      <ProjectNav />
      <div className="center_content">
        <SideNav />
        <main>
          <div classname="backlogHead" style={{marginBottom:"15px", display:"flex"}}>
            <h1>Project Name</h1>
          </div>
          <div className="flex_cont">
            {/* Backlog Panel*/}
            <div className="backlogPanel">
              <div className="head">
                <div className="info">
                  <h2>Backlog</h2>
                  <p className="issue_total">6 Issues</p>
                </div>
                <form onSubmit={handleSubmit}>
                  <input type="search" placeholder="Search for and issue"/>
                  <button type="submit">
                    <Search fontSize="small" />
                  </button>
                </form>
              </div>
              <div className="tableWrapper">
                <table id = "backlogIssuesTable">
                  <tr>
                  <th>Title</th>
                  <th>Date Created</th>
                  <th>Priority</th>
                  </tr>
                  {backlogIssues.map(issue => (
                  <tr key={issue.id} onClick={showIssuePanel}>
                  <td>{issue.title}</td>
                  <td>{issue.dateCreated}</td>
                  <td>{issue.priority}</td>
                  </tr>
                  ))}
                </table>
              </div>
              <div style={{textAlign:"center"}}>
                <button id="createIssueButton" onClick={showCreateIssuePopup}> + Create New Issue</button>
              </div>
            </div>
            {/* Issue Panel */}
              {issue_panel === "show" && 
                <div  className = "issuePanel">
                <div>
                  <h1 id="issueName">Issue Name</h1>
                  <br></br>
                  <text id ="issueEpic">Epic of this issue</text>
                  <br></br>
                  <br></br>
                  <h3>Description</h3>
                  <p>
                  {/* Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit */}
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  </p>
                  <br/>
                  <text className="label" id="status">Status: </text>
                  <text className="answer" id="statusAnswer">Open</text>
                  <br/>
                  <text className="label" id="priority">Priority: </text>
                  <text className="answer" id="priorityAnswer">Major </text>
                  <br/>
                  <br/>
                  <text className="label" id="resolution">Resolution: </text>
                  <text className="answer" id="resolutionAnswer">Unresolved</text>
                  <br/>
                  <br/>
                  <text className="label" id="assignee">Assignee: </text>
                  <text className="answer" id="assigneeAnswer">Makis</text>
                  <br/>
                  <text className="label" id="reporter">Reporter: </text>
                  <text id="reporterAnswer">Takis</text>
                  <br/>
                  <br/>
                  <text className="label" id="dateCreated">Created on: </text>
                  <text className="answer" id="dateCreatedAnswer">10/3/2021</text>
                </div>
                </div>
              }
              {sprint === "show"
                // current sprint (if there is one)
                ? 
                <div className="sprint">
                  <div className="head">
                    <div className="info">
                      <h2>Active Sprint</h2>
                      <p className="issue_total">6 Issues</p>
                    </div>
                    <form onSubmit={handleSubmit}>
                      <input type="search" placeholder="Search for and issue"/>
                      <button type="submit">
                        <Search fontSize="small" />
                      </button>
                    </form>
                  </div>
                  <div className="tableWrapper">
                    <table id = "backlogIssuesTable">
                      <tr>
                      <th>Title</th>
                      <th>Date Created</th>
                      <th>Priority</th>
                      </tr>
                      {sprintIssues.map(issue => (
                      <tr key={issue.id} onClick={showIssuePanel}>
                      <td>{issue.title}</td>
                      <td>{issue.dateCreated}</td>
                      <td>{issue.priority}</td>
                      </tr>
                      ))}
                    </table>
                  </div>
                </div>
                // create sprint button (if there is no sprint)
                :
                <div className="createSprint">
                  <div>
                    <button id="createSprintButton">Create Sprint</button>
                  </div>
                </div>
              }
            
          </div>
          {/* Popup */}
          {create_issue_popup === "show" && 
                    <div className="createIssuePopup">
                        <div>
                            <h2>Create a new Issue</h2>
                            <img src={x_icon} alt="accountIcon" onClick={hideCreateIssuePopup}></img>
                        </div>
                        <br/>
                        <br/>
                        <form className="newIssueForm" style={{textAlign:"left"}}>
                          <p>Title:</p>
                            <input type="text" id="issueName" placeholder="Issue Title"></input>
                            <p>Description:</p>
                            <textarea type="range" placeholder="Issue Description"></textarea>
                            <div className="priority">
                              <p>Priority:</p>
                              <select name="priority" id="priority">
                                <option value="low">Low</option>
                                <option value="medium">Medium</option>
                                <option value="high">High</option>
                              </select>
                            </div>
                            <br></br>   
                            <p>Labels:</p>
                            <input type="checkbox" id="issueLabelOption" name="label1" value="label_value"/>
                            <label for="vehicle1"> Label</label>
                            <input type="checkbox" id="issueLabelOption" name="label2" value="label_value"/>
                            <label for="vehicle1"> Label</label>
                            <input type="checkbox" id="issueLabelOption" name="label3" value="label_value"/>
                            <label for="vehicle1"> Label</label>
                            <input type="checkbox" id="issueLabelOption" name="label4" value="label_value"/>
                            <label for="vehicle1"> Label</label>
                            <input type="checkbox" id="issueLabelOption" name="label5" value="label_value"/>
                            <label for="vehicle1"> Label</label>
                            
                            <div style={{textAlign:"center"}}>
                              <button onClick={handlePopupButtonClick}>Create Issue</button>
                            </div>
                        </form>
                    </div>
                }
        </main>
      </div>
      <Footer />
    </div>
  );
}
 
export default Backlog;