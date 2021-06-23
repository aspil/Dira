import x_icon from "../Images/x_icon.png"

import SideNav from './SideNav'
import { useEffect, useRef, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Search } from '@material-ui/icons'
import { DiraIssueClient } from "dira-clients";
import edit_icon from "../Images/edit_icon.png"

const Backlog = ({ token, footerHandle, projectClientRef, userId }) => {
  const [backlogIssues, setBacklogIssues] = useState([]);
  const [searchFilteredIssues, setSearchFilteredIssues] = useState([]);
  const [searchFilter, setSearchFilter] = useState('');
  const [sprintIssues, setSprintIssues] = useState([
    { title: 'Issue y', dateCreated: "10/5/2023", priority: "high", key: 2 },
  ])

  const [members, setMembers] = useState([]);

  const [projectName, setProjectName] = useState('');

  const [newTitle, setNewTitle] = useState('');
  const [newDescription, setNewDescription] = useState('');
  const [newPriority, setNewPriority] = useState('Normal');
  const [newAssignee, setNewAssignee] = useState(null);
  const [newEpicLink, setNewEpicLink] = useState(null);
  const [newType, setNewType] = useState('Story');
  const [issueCreationError, setIssueCreationError] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
  const [focusedIssueId, setFocusedIssueId] = useState(null);
  const [focusedIssue, setFocusedIssue] = useState(null);
  const [sprint, handleSprintPanel] = useState("hide");
  const [userPermissions, setUserPermissions] = useState(undefined);
  const [hasRead, setHasRead] = useState(false);
  const [hasWrite, setHasWrite] = useState(false);

  const { projectId } = useParams();
  const issueClientRef = useRef(new DiraIssueClient(projectId));

  useEffect(() => {
    if (token) {
      issueClientRef.current.set_authorization_token(token);
    }
  }, [token, issueClientRef]);

  const fetchAllIssues = () => {
    issueClientRef.current.get_all_issues().then((res) => {
      console.log(res);
      setBacklogIssues(res.issues);
      setProjectName(res.name);
    }).catch((err) => {
      console.log(err);
    });
  }

  const fetchMembers = () => {
    projectClientRef.current.get_all_users_in_project_by_id(projectId).then((res) => {
      setMembers(res.users);
    }).catch((err) => {
      console.log(err);
    });
  }

  useEffect(fetchAllIssues, []);
  useEffect(fetchMembers, []);

  useEffect(footerHandle, [footerHandle]);

  //issue panel handlers
  const [issue_panel, handleIssuePanel] = useState("hide");
  const showIssuePanel = (issueId) => {
    setFocusedIssueId(issueId);
    setFocusedIssue(backlogIssues.find(issue => issue.id === issueId));
    handleIssuePanel("show");
  }
  const hideIssuePanel = () => {
    handleIssuePanel("hide");
  }

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
    setNewTitle('');
    setNewDescription('');
    setNewPriority('Normal');
    setNewAssignee(null);
    setNewEpicLink(null);
    setNewType('Story');
    setIssueCreationError(false);
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

    if (!(newTitle && newDescription)) {
      setIssueCreationError(true);
      setErrorMsg('Please fill in both title and description fields');
      return;
    }
    else if (newType === 'Epic' && newEpicLink) {
      setIssueCreationError(true);
      setErrorMsg('An Epic can\'t link to another Epic');
      return;
    }
    setIssueCreationError(false);

    issueClientRef.current.create_issue({
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
      setIssueCreationError(true);
      setErrorMsg('Couldn\'t create issue');
      console.log(err);
    });
  }


  const handleClearSearch = (e) => {
    e.preventDefault();
    setSearchFilter('');
  }

  useEffect(() => {
    if (searchFilter) {
      setSearchFilteredIssues(backlogIssues.filter((issue) => {
        const issueFields = Object.keys(issue);
        let isRelevant = false;
        issueFields.forEach(field => {
          if (typeof issue[field] === 'string') {
            isRelevant |= issue[field].toLowerCase().includes(searchFilter.toLowerCase());
          }
          else if (Array.isArray(issue[field])) {
            issue[field].forEach(subField => {
              if (typeof issue[field][subField] === 'string') {
                isRelevant |= issue[field][subField].toLowerCase().includes(searchFilter.toLowerCase());
              }
            })
          }
        })
        return isRelevant;
      }))

    } else {
      setSearchFilteredIssues(backlogIssues);
    }
  }, [searchFilter, backlogIssues]);

  const fetchUserPermissions = () => {
    projectClientRef.current.get_project_permissions_for_all_users(projectId).then(res => {
      console.log(res);
      setUserPermissions(res.find(customer => customer.customerId === userId));
    }).catch(err => {
      console.log(err);
    });
  };
  useEffect(fetchUserPermissions, []);

  const decodePermissions = () => {
    if (!userPermissions) {
      return;
    }

    setHasRead((0b0001 & userPermissions.permission) ? true : false);
    setHasWrite((0b0010 & userPermissions.permission) ? true : false);
  }
  useEffect(decodePermissions, [userPermissions]);

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

  const priorityToColorMapper = {
    Normal: 'green',
    Low: 'teal',
    Major: 'crimson',
    Blocked: 'purple'
  }

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
                    style={{ marginRight: "0" }}
                  />
                  <button type="submit" style={{ backgroundColor: 'grey', border: "2px solid grey" }}>
                    X
                  </button>
                </form>
              </div>
              <div className="tableWrapper">
                <table id="backlogIssuesTable">
                  <thead>
                    <tr>
                      <th>Key</th>
                      <th>Title</th>
                      <th>Description</th>
                      <th>Type</th>
                      <th>Priority</th>
                    </tr>
                  </thead>
                  <tbody>
                    {hasRead && searchFilteredIssues.map(issue => (
                      <tr key={issue.key} onClick={() => showIssuePanel(issue.id)}>
                        <td>{issue.key}</td>
                        <td>{issue.title}</td>
                        <td className="largeCell">{issue.description}</td>
                        <td>{issue.type}</td>
                        <td style={{ textAlign: "center" }}>
                          <text className="colored_text" style={{ backgroundColor: priorityToColorMapper[issue.priority], fontSize: "12px" }}>{issue.priority}</text>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              {hasWrite &&
                <div style={{ textAlign: "center" }}>
                  <button id="createIssueButton" onClick={showCreateIssuePopup}> + Create New Issue</button>
                </div>
              }
            </div>
            {/* Issue Panel */}
            {issue_panel === "show" &&
              <div className="issuePanel">

                <div>
                  <h2 style={{ color: "gray" }}>
                    <img src={x_icon} id="xIcon" alt="x_icon" onClick={hideIssuePanel}></img>
                    {focusedIssue.type}
                  </h2>
                  <h1 id="issueName">
                    {focusedIssue.title}
                  </h1>
                  <br />
                  {focusedIssue.epicId &&
                    <>
                      <text
                        class="colored_text"
                        onClick={() => showIssuePanel(focusedIssue.epicId)}
                      >
                        {backlogIssues.find(issue => issue.id === focusedIssue.epicId).key}
                      </text>
                      <br />
                      <br />
                    </>
                  }
                  <h3>Description</h3>
                  <p>{focusedIssue.description}</p>
                  <br />
                  <text className="label" id="status">Status: </text>
                  <text className="answer" id="statusAnswer">{focusedIssue.status}</text>
                  <br />
                  <text className="label" id="priority">Priority: </text>
                  <text
                    className="answer"
                    class="colored_text"
                    style={{
                      display: 'inline-block',
                      fontSize: '1em',
                      marginTop: '0.2em',
                      backgroundColor: priorityToColorMapper[focusedIssue.priority]
                    }}
                  >
                    {focusedIssue.priority}
                  </text>
                  <br />
                  <br />
                  <text className="label" id="resolution">Resolution: </text>
                  <text className="answer" id="resolutionAnswer">{focusedIssue.resolved ? "Resolved" : "Unresolved"}</text>
                  <br />
                  <br />
                  <text className="label" id="assignee">Assignee: </text>
                  <text className="answer" id="assigneeAnswer">{focusedIssue.assignee ? focusedIssue.assignee : "-"}</text>
                  <br />
                  <text className="label" id="reporter">Reporter: </text>
                  <text id="reporterAnswer">{focusedIssue.reporter}</text>
                  <br />
                  <br />
                  <text className="label" id="dateCreated">Created on: </text>
                  <text className="answer" id="dateCreatedAnswer">{new Date(focusedIssue.created).toLocaleString()}</text>
                  {hasWrite &&
                    <div style={{ textAlign: "center", marginTop: "20px" }}>
                      <Link to={`/project/${projectId}/issue_preview/${focusedIssueId}`} id="editIssueLink">
                        <img id="pencilIcon" src={edit_icon} alt="Pencil"></img>
                        Edit Issue
                      </Link>
                    </div>
                  }
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
                <img src={x_icon} id="xIcon" alt="x_icon" onClick={hideCreateSprintPopup}></img>
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
                <img src={x_icon} id="xIcon" alt="x_icon" onClick={hideCreateIssuePopup}></img>

              </div>
              <br />
              <br />
              <form
                className="newIssueForm"
                style={{ textAlign: "left" }}
                onSubmit={handleCreateIssueButtonClick}
                noValidate
              >
                <p>Title:</p>
                <input
                  type="text"
                  id="issueName"
                  placeholder="Issue Title"
                  value={newTitle}
                  onChange={(e) => { setNewTitle(e.target.value); }}
                />
                <p>Description:</p>
                <textarea
                  type="range"
                  placeholder="Issue Description"
                  value={newDescription}
                  onChange={(e) => { setNewDescription(e.target.value); }}
                />
                <div className="markdowns">
                  <div className="issueMarkdown">
                    <p>Priority:</p>
                    <select
                      id="priority"
                      onChange={(e) => { setNewPriority(e.target.value); }}
                    >
                      {priorities.map((priority) => (
                        <option key={priority} value={priority} >
                          {priority}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="issueMarkdown">
                    <p>Type:</p>
                    <select
                      id="type"
                      onChange={(e) => { setNewType(e.target.value !== 'None' ? e.target.value : null); }}
                    >
                      {types.map((type) => (
                        <option key={type} value={type} >
                          {type}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="issueMarkdown">
                    <p>Assignee:</p>
                    <select
                      id="assignee"
                      onChange={(e) => { setNewAssignee(e.target.value !== 'None' ? e.target.value : null); }}
                    >
                      <option value='None'>
                        None
                      </option>
                      {members.map(member => (
                        <option
                          key={member.id}
                          value={member.id}
                        >
                          {member.name} {member.surname}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="issueMarkdown">
                    <p>Epic:</p>
                    <select
                      id="epic"
                      onChange={(e) => { setNewEpicLink(e.target.value); }}
                    >
                      <option value='None'>
                        None
                      </option>
                      {backlogIssues.filter(issue => issue.type === 'Epic').map(epic => (
                        <option
                          key={epic.id}
                          value={epic.id}
                        >
                          {epic.key}, {epic.title}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                {issueCreationError &&
                  <p style={{ color: 'crimson', marginTop: '1em', textAlign: 'center' }}>
                    {errorMsg}
                  </p>
                }

                <div style={{ textAlign: "center" }}>
                  <button >Create Issue</button>
                </div>
              </form>
            </div>
          }
        </main>
      </div>
    </div >
  );
}

export default Backlog;