import x_icon from "../Images/x_icon.png"

import ProjectNav from './ProjectNav'
import Footer from './Footer'
import SideNav from './SideNav'
import { useEffect, useRef, useState } from 'react'
import { Link, useParams, useHistory } from 'react-router-dom'
import { Search } from '@material-ui/icons'
import { DiraIssueClient } from "dira-clients";
import edit_icon from "../Images/edit_icon.png"

const Backlog = ({ token, footerHandle, projectClient }) => {
  const history = useHistory();

  const [backlogIssues, setBacklogIssues] = useState([]);
  const [searchFilteredIssues, setSearchFilteredIssues] = useState([]);
  const [searchFilter, setSearchFilter] = useState('');
  const [sprintIssues, setSprintIssues] = useState([
    { title: 'Issue y', dateCreated: "10/5/2023", priority: "high", key: 2 },
  ])

  const [members, setMembers] = useState([]);

  const [projectName, setProjectName] = useState('');

  const [newTitle, setNewTitle] = useState(null);
  const [newDescription, setNewDescription] = useState(null);
  const [newPriority, setNewPriority] = useState('Normal');
  const [newAssignee, setNewAssignee] = useState(null);
  const [newEpicLink, setNewEpicLink] = useState(null);
  const [newType, setNewType] = useState('Story');

  const [focusedIssueId, setFocusedIssueId] = useState(null);
  const [sprint, handleSprintPanel] = useState("hide");

  const [issue_panel, handleIssuePanel] = useState("hide");
  const showIssuePanel = (issueId) => {
    setFocusedIssueId(issueId);
    handleIssuePanel("show");
  }

  const { projectId } = useParams();
  const issueClient = useRef(new DiraIssueClient(projectId)).current;

  useEffect(() => {
    if (token) {
      issueClient.set_authorization_token(token);
    }
  }, [token]);

  const fetchAllIssues = () => {
    issueClient.get_all_issues().then((res) => {
      console.log(res);
      setBacklogIssues(res.issues);
      setProjectName(res.name);
    }).catch((err) => {
      console.log(err);
    });
  }

  const fetchMembers = () => {
    projectClient.get_all_users_in_project_by_id(projectId).then((res) => {
      setMembers(res.users);
    }).catch((err) => {
      console.log(err);
    });
  }

  useEffect(fetchAllIssues, []);
  useEffect(fetchMembers, []);

  useEffect(footerHandle, [footerHandle]);

  // Create sprint popup handlers
  const [create_sprint_popup, handleCreateSprintPopup] = useState("hide");
  const hideCreateSprintPopup = () => {
    handleCreateSprintPopup("hide");
  }
  const showCreateSprintPopup = () => {
    handleCreateSprintPopup("show");
  }
  const handleCreateSprintButtonClick = () => {
    hideCreateSprintPopup();
  }

  const clearState = () => {
    setNewTitle(null);
    setNewDescription(null);
    setNewPriority('Normal');
    setNewAssignee(null);
    setNewEpicLink(null);
    setNewType('Story');
  }

  // Create issue popup handlers
  const [create_issue_popup, handleCreateIssuePopup] = useState("hide");
  const hideCreateIssuePopup = () => {
    clearState();
    handleCreateIssuePopup("hide");
  }
  const showCreateIssuePopup = () => {
    handleCreateIssuePopup("show");
  }
  const handleCreateIssueButtonClick = (e) => {
    e.preventDefault();
    issueClient.create_issue({
      "description": newDescription,
      "type": newType,
      "priority": newPriority,
      "title": newTitle,
      "assigneeId": newAssignee,
      "epicId": newEpicLink
    }).then((res) => {
      fetchAllIssues();
      hideCreateIssuePopup();
    }).catch((err) => {
      console.log('error during creation of issue');
      console.log(err);
    });
  }


  const handleClearSearch = (e) => {
    e.preventDefault();
    setSearchFilter('');
  }

  useEffect(() => {
    if (searchFilter) {
      const currentIssues = backlogIssues;
      setSearchFilteredIssues(currentIssues.filter((issue) => {
        const issueFields = Object.keys(issue);
        let isRelevant = false;
        issueFields.forEach(field => {
          if (typeof issue[field] === 'string') {
            isRelevant |= issue[field].includes(searchFilter);
          }
          else if (Array.isArray(issue[field])) {
            issue[field].forEach(subField => {
              if (typeof issue[field][subField] === 'string') {
                isRelevant |= issue[field][subField].includes(searchFilter);
              }
            })
          }
        })
        return isRelevant;
      }))

    } else {
      setSearchFilteredIssues(backlogIssues);
    }
  }, [searchFilter, backlogIssues])

  const types = [
    'Story',
    'Epic',
    'Defect'
  ]

  const priorities = [
    'Normal',
    'Low',
    'Major',
    'Blocked'
  ]

  return (
    <div className="backlog proj_page">
      <div className="center_content">
        <SideNav />
        <main>
          <div className="backlogHead" style={{ marginBottom: "15px", display: "flex" }}>
            <h1>{projectName}</h1>
          </div>
          <div className="flex_cont">
            {/* Backlog Panel*/}
            <div className="backlogPanel">
              <div className="head">
                <div className="info">
                  <h2>Backlog</h2>
                  <p className="issue_total">{searchFilteredIssues.length} Issues</p>

                </div>
                <form onSubmit={handleClearSearch} noValidate>
                  <input
                    type="text"
                    placeholder="Search for and issue"
                    value={searchFilter}
                    onChange={(e) => { setSearchFilter(e.target.value); }}
                  />
                  <button type="submit" style={{ backgroundColor: 'crimson' }}>
                    Clear Search
                  </button>
                </form>
              </div>
              <div className="tableWrapper">
                <table id="backlogIssuesTable">
                  <tr>
                    <th>Key</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Type</th>
                    <th>Priority</th>
                  </tr>
                  {searchFilteredIssues.map(issue => (
                    <tr key={issue.key} onClick={() => showIssuePanel(issue.id)}>
                      <td>{issue.key}</td>
                      <td>{issue.title}</td>
                      <td>{issue.description}</td>
                      <td>{issue.type}</td>
                      <td>{issue.priority}</td>
                    </tr>
                  ))}
                </table>
              </div>
              <div style={{ textAlign: "center" }}>
                <button id="createIssueButton" onClick={showCreateIssuePopup}> + Create New Issue</button>
              </div>
            </div>
            {/* Issue Panel */}
            {issue_panel === "show" &&
              <div className="issuePanel">
                <div>
                  <h1 id="issueName">Issue Name</h1>
                  <br></br>
                  <text id="issueEpic">Epic of this issue</text>
                  <br></br>
                  <br></br>
                  <h3>Description</h3>
                  <p>
                    {/* Lorem ipsum dolor sit amet, consectetur adipiscing elit
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit */}
                  </p>
                  <br />
                  <text className="label" id="status">Status: </text>
                  <text className="answer" id="statusAnswer">Open</text>
                  <br />
                  <text className="label" id="priority">Priority: </text>
                  <text className="answer" id="priorityAnswer">Major </text>
                  <br />
                  <br />
                  <text className="label" id="resolution">Resolution: </text>
                  <text className="answer" id="resolutionAnswer">Unresolved</text>
                  <br />
                  <br />
                  <text className="label" id="assignee">Assignee: </text>
                  <text className="answer" id="assigneeAnswer">Makis</text>
                  <br />
                  <text className="label" id="reporter">Reporter: </text>
                  <text id="reporterAnswer">Takis</text>
                  <br />
                  <br />
                  <text className="label" id="dateCreated">Created on: </text>
                  <text className="answer" id="dateCreatedAnswer">10/3/2021</text>
                  <div style={{ textAlign: "center", marginTop: "20px" }}>
                    <Link to={`/project/${projectId}/issue_preview/${focusedIssueId}`} id="editIssueLink">
                      <img id="pencilIcon" src={edit_icon} alt="Pencil"></img>
                      Edit Issue
                    </Link>
                  </div>
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
                    <text style={{ fontWeight: "bold" }}>Due date: </text>
                    <text className="dueDate:">12/5/2022</text>
                    <br />
                    <text style={{ fontWeight: "bold" }}>Time Remaining: </text>
                    <text className="timeRemaining:">8 days</text>
                  </div>
                  <form>
                    <input type="search" placeholder="Search for and issue" />
                    <button type="submit">
                      <Search fontSize="small" />
                    </button>
                  </form>
                </div>
                <div className="tableWrapper">
                  <table id="backlogIssuesTable">
                    <tr>
                      <th>Title</th>
                      <th>Date Created</th>
                      <th>Priority</th>
                    </tr>
                    {sprintIssues.map(issue => (
                      <tr key={issue.id} onClick={() => showIssuePanel(issue.id)}>
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
                  <button id="createSprintButton" onClick={showCreateSprintPopup}>+ Create Sprint</button>
                </div>
              </div>
            }

          </div>
          {/* create Sprint Popup */}
          {create_sprint_popup === "show" &&
            <div className="createPopup">
              <div>
                <h2>Create a new Sprint</h2>
                <img src={x_icon} alt="accountIcon" onClick={hideCreateSprintPopup}></img>
              </div>
              <br />
              <br />
              <form className="newIssueForm" style={{ textAlign: "left" }} >
                <p>Title:</p>
                <input type="text" id="sprintName" placeholder="Sprint Title" required></input>
                <div className="priority">
                  <p>Duration:</p>
                  <select name="priority" id="priority">
                    <option value="1week">1 week</option>
                    <option value="2weeks">2 weeks</option>
                    <option value="3weeks">3 weeks</option>
                    <option value="4weeks">4 weeks</option>
                  </select>
                </div>
                <div style={{ textAlign: "center" }}>
                  <button onClick={handleCreateSprintButtonClick}>Create Sprint</button>
                </div>
              </form>
            </div>
          }
          {/* create Issue Popup */}
          {create_issue_popup === "show" &&
            <div className="createPopup" style={{ fontWeight: "bold" }}>
              <div>
                <h2>Create a new Issue</h2>
                <img src={x_icon} alt="accountIcon" onClick={hideCreateIssuePopup}></img>
              </div>
              <br />
              <br />
              <form className="newIssueForm" style={{ textAlign: "left" }} onSubmit={handleCreateIssueButtonClick}>
                <p>Title:</p>
                <input
                  type="text"
                  id="issueName"
                  placeholder="Issue Title"
                  value={newTitle}
                  required
                  onChange={(e) => { setNewTitle(e.target.value); }}
                />
                <p>Description:</p>
                <textarea
                  type="range"
                  required
                  placeholder="Issue Description"
                  value={newDescription}
                  onChange={(e) => { setNewDescription(e.target.value); }}
                />
                <div className="markdowns" style={{ display: "flex", justifyContent: "space-between" }}>
                  <div className="issuePriority">
                    <p>Priority:</p>
                    <select
                      id="priority"
                      onChange={(e) => { setNewPriority(e.target.value); }}
                    >
                      {priorities.map((priority) => (
                        <option value={priority} >
                          {priority}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="issueType">
                    <p>Type:</p>
                    <select
                      id="type"
                      onChange={(e) => { setNewType(e.target.value !== 'None' ? e.target.value : null); }}
                    >
                      {types.map((type) => (
                        <option value={type} >
                          {type}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="issueAssignee">
                    <p>Assignee:</p>
                    <select
                      id="assignee"
                      onChange={(e) => { setNewAssignee(e.target.value !== 'None' ? e.target.value : null); }}
                    >
                      <option value='None'>
                        None
                      </option>
                      {members.map(member => (
                        <option value={member.id}>
                          {member.name} {member.surname}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="epic">
                    <p>Epic:</p>
                    <select
                      id="epic"
                      onChange={(e) => { setNewEpicLink(e.target.value); }}
                    >
                      <option value='None'>
                        None
                      </option>
                      {backlogIssues.filter(issue => issue.type === 'Epic').map(epic => (
                        <option value={epic.id} >
                          {epic.key}, {epic.title}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div style={{ textAlign: "center" }}>
                  <button >Create Issue</button>
                </div>
              </form>
            </div>
          }
        </main>
      </div>
    </div>
  );
}

export default Backlog;