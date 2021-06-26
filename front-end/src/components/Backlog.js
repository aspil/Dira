import x_icon from "../Images/x_icon.png"

import SideNav from './SideNav'
import { useEffect, useRef, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Search } from '@material-ui/icons'
import { DiraIssueClient } from "dira-clients";
import edit_icon from "../Images/edit_icon.png"

const Backlog = ({ token, footerHandle, projectClientRef, userId, username }) => {
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
  const [newComment, setNewComment] = useState('');
  const [newLabel, setNewLabel] = useState('');
  const [newCommentError, setNewCommentError] = useState('');
  const [newLabelError, setNewLabelError] = useState('');
  const [deleteCommentError, setDeleteCommentError] = useState('');
  const [deleteLabelError, setDeleteLabelError] = useState('');
  const [deleteIssueLinkError, setDeleteIssueLinkError] = useState('');
  const [newIssueLink, setNewIssueLink] = useState({ key: '', name: '' });
  const [newIssueLinkError, setNewIssueLinkError] = useState('');
  const [newIssueLinkType, setNewIssueLinkType] = useState('DEPENDS_ON');

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
      if (focusedIssueId) {
        setFocusedIssue(res.issues.find(issue => issue.id === focusedIssueId));
      }
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
    setFocusedIssue(searchFilteredIssues.find(issue => issue.id === issueId));

    setNewComment('');
    setNewLabel('');
    setNewCommentError('');
    setNewLabelError('');
    setDeleteLabelError('');
    setDeleteCommentError('');
    setNewIssueLink({ key: '', name: '' });
    setNewIssueLinkError('');
    setDeleteIssueLinkError('');
    setNewIssueLinkType('DEPENDS_ON');

    handleIssuePanel("show");
  }
  const hideIssuePanel = () => {
    setFocusedIssueId(null);
    setFocusedIssue(null);
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
            issue[field].forEach(arrayItem => {
              if (field !== 'issueLinks') {
                isRelevant |= arrayItem.value.toLowerCase().includes(searchFilter.toLowerCase());
              }
              else {
                isRelevant |= arrayItem.linkType.toLowerCase().includes(searchFilter.toLowerCase());
                isRelevant |= arrayItem.linkedIssue.key.toLowerCase().includes(searchFilter.toLowerCase());
                isRelevant |= arrayItem.linkedIssue.name.toLowerCase().includes(searchFilter.toLowerCase());
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

    if (userPermissions.permissions.find(perm => perm === 'ADMIN')) {
      setHasRead(true);
      setHasWrite(true);
      return;
    }

    if (userPermissions.permissions.find(perm => perm === 'READ')) {
      setHasRead(true);
    }
    if (userPermissions.permissions.find(perm => perm === 'WRITE')) {
      setHasWrite(true);
    }
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

  const addNewValueToField = (field) => {
    if (field === 'label' && !newLabel) {
      setNewLabelError('Please fill in field');
      return;
    }
    else if (field === 'comment' && !newComment) {
      setNewCommentError('Please fill in field');
      return;
    }
    else if (field === 'link' && !newIssueLink.key) {
      setNewIssueLinkError('Please pick an issue');
      return;
    }

    const newIssue = JSON.parse(JSON.stringify(focusedIssue));
    if (field === 'label') {
      newIssue.labels.push({ 'value': newLabel });
      setNewLabelError('');
    }
    else if (field === 'comment') {
      newIssue.comments.push({ 'value': `${username},${newComment}` });
      setNewCommentError('');
    }
    else if (field === 'link') {
      newIssue.issueLinks.push({ 'linkType': newIssueLinkType, 'linkedIssue': newIssueLink })
      setNewIssueLinkError('');
    }
    issueClientRef.current.update_issue(focusedIssueId, newIssue)
      .then(res => {
        console.log(res);
        fetchAllIssues();
        if (field === 'label') {
          setNewLabel('');
        }
        else if (field === 'comment') {
          setNewComment('');
        }
        else if (field === 'link') {
          setNewIssueLink({ key: '', name: '' });
          setNewIssueLinkType('DEPENDS_ON');
        }
      })
      .catch(err => {
        console.log(err);
        if (field === 'label') {
          setNewLabelError('Couldn\'t add label');
        }
        else if (field === 'comment') {
          setNewCommentError('Couldn\'t add comment');
        }
        else if (field === 'link') {
          setNewIssueLinkError('Couldn\'t add link');
        }
      })

  }
  const deleteValueFromField = (field, toDelete) => {
    const issue = JSON.parse(JSON.stringify(focusedIssue));
    if (field === 'label') {
      issue.labels = issue.labels.filter(labelObj => labelObj.value !== toDelete);
      setDeleteLabelError('');
    }
    else if (field === 'comment') {
      issue.comments = issue.comments.filter(commentObj => commentObj.value !== toDelete);
      setDeleteCommentError('');
    }
    issueClientRef.current.update_issue(focusedIssueId, issue)
      .then(res => {
        console.log(res);
        fetchAllIssues();
      })
      .catch(err => {
        console.log(err);
        if (field === 'label') {
          setDeleteLabelError('Couldn\'t delete label');
        }
        else if (field === 'comment') {
          setDeleteCommentError('Couldn\'t delete comment');
        }
      });

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
                  <p
                    className="issue_total"
                    style={!hasRead ? { display: 'none' } : {}}
                  >
                    {searchFilteredIssues.length} Issues
                  </p>
                  {!hasRead &&
                    <p style={{ color: 'crimson' }}>You don't have permission to view this Backlog</p>
                  }
                </div>
                <form
                  onSubmit={handleClearSearch}
                  noValidate
                  style={!hasRead ? { display: 'none' } : {}}
                >
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
                          <span className="colored_text" style={{ backgroundColor: priorityToColorMapper[issue.priority], fontSize: "12px" }}>{issue.priority}</span>
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
                <h2 style={{ color: "gray" }}>
                  <img src={x_icon} id="xIcon" alt="x_icon" onClick={hideIssuePanel}></img>
                  {focusedIssue.type}
                </h2>
                <h1 id="issueName" style={{ alignItems: "center" }}>
                  {focusedIssue.title}
                </h1>
                <div className="issuePanelMain">
                  <br />
                  {focusedIssue.epicId &&
                    <>
                      <span
                        className="colored_text"
                        onClick={() => showIssuePanel(focusedIssue.epicId)}
                      >
                        {backlogIssues.find(issue => issue.id === focusedIssue.epicId).key}
                      </span>
                      <br />
                      <br />
                    </>
                  }
                  <h3>Description</h3>
                  <p>{focusedIssue.description}</p>
                  <br />
                  <span className="label" id="status">Status: </span>
                  <span className="answer" id="statusAnswer">{focusedIssue.status}</span>
                  <br />
                  <span className="label" id="priority">Priority: </span>
                  <span
                    className="colored_text answer"
                    style={{
                      display: 'inline-block',
                      fontSize: '1em',
                      marginTop: '0.2em',
                      backgroundColor: priorityToColorMapper[focusedIssue.priority]
                    }}
                  >
                    {focusedIssue.priority}
                  </span>
                  <br />
                  <br />
                  <span className="label" id="resolution">Resolution: </span>
                  <span className="answer" id="resolutionAnswer">{focusedIssue.resolved ? "Resolved" : "Unresolved"}</span>
                  <br />
                  <br />
                  <span className="label" id="assignee">Assignee: </span>
                  <span className="answer" id="assigneeAnswer">{focusedIssue.assignee ? focusedIssue.assignee : "-"}</span>
                  <br />
                  <span className="label" id="reporter">Reporter: </span>
                  <span id="reporterAnswer">{focusedIssue.reporter}</span>
                  <br />
                  <br />
                  <span className="label" id="dateCreated">Created on: </span>
                  <span className="answer" id="dateCreatedAnswer">{new Date(focusedIssue.created).toLocaleString()}</span>
                  <br />
                  <span className="label" id="dateCreated">Last Updated: </span>
                  <span className="answer" id="dateCreatedAnswer">{new Date(focusedIssue.updated).toLocaleString()}</span>
                  <br /><br />
                  {/* Labels */}
                  <p className="label">Labels: </p>
                  {Boolean(deleteLabelError) && <p style={{ color: 'crimson' }}>{deleteLabelError}</p>}
                  {focusedIssue.labels.map(({ key, value: label }) => (
                    <div
                      className="issueLabelsWrapper"
                      key={key}
                    >
                      <button
                        className="issueLabelX"
                        onClick={() => deleteValueFromField('label', label)}
                      >
                        X
                      </button>
                      <span className="issueLabel"> {label} </span>
                    </div>
                  ))}
                  <input
                    type="text"
                    name="newLabel"
                    id="newLabel"
                    placeholder="+ Add label"
                    style={{ marginLeft: "10px", marginRight: "0px", border: "1px solid grey", borderRadius: "0" }}
                    value={newLabel}
                    onChange={(e) => (setNewLabel(e.target.value))}
                  />
                  <button
                    style={{ backgroundColor: "grey" }}
                    onClick={() => addNewValueToField('label')}
                  >
                    +
                  </button>
                  {Boolean(newLabelError) && <p style={{ color: 'crimson', marginTop: '-1em', fontSize: '0.8em' }}>{newLabelError}</p>}

                  {/* Comments */}
                  <p className="label">Comments: </p>
                  {Boolean(deleteCommentError) && <p style={{ color: 'crimson' }}>{deleteCommentError}</p>}
                  {focusedIssue.comments.map(({ key, value: comment }) => (
                    <div
                      className="issueCommentsWrapper"
                      key={key}
                    >
                      <button
                        className="issueCommentX"
                        onClick={() => deleteValueFromField('comment', comment)}
                      >
                        X
                      </button>
                      <span className="issueComment">
                        {comment.slice(0, comment.indexOf(','))} wrote: {comment.slice(comment.indexOf(',') + 1)}
                      </span>
                    </div>
                  ))}
                  <input
                    type="text"
                    name="newComment"
                    id="newComment"
                    placeholder="+ Add comment"
                    style={{ marginLeft: "10px", marginRight: "0px", border: "1px solid grey", borderRadius: "0" }}
                    value={newComment}
                    onChange={(e) => (setNewComment(e.target.value))}
                  />
                  <button
                    style={{ backgroundColor: "grey" }}
                    onClick={() => addNewValueToField('comment')}
                  >
                    +
                  </button>
                  {Boolean(newCommentError) && <p style={{ color: 'crimson', marginTop: '-1em', fontSize: '0.8em' }}>{newCommentError}</p>}

                  {/* Links */}
                  {focusedIssue.type !== 'Epic' &&
                    <>
                      <p className="label">Links: </p>
                      {focusedIssue.issueLinks.map(linkObject => (
                        <div
                          key={linkObject.id}
                          className="issueLinksWrapper"
                        >
                          <button className="issueLinkX">X</button>
                          <span
                            className="issueLink"
                            onClick={() => showIssuePanel(linkObject.linkedIssue.id)}
                          >
                            {linkObject.linkType}:&nbsp;{linkObject.linkedIssue.key},&nbsp;{linkObject.linkedIssue.name}
                          </span>
                        </div>
                      ))}
                      <select
                        style={{ marginLeft: '20px' }}
                        value={newIssueLink.key}
                        name="newLink"
                        onChange={(e) => {
                          if (e.target.value === '') {
                            setNewIssueLink({ key: '', name: '' });
                            return;
                          }
                          const { key, title: name } = backlogIssues.find(issue => issue.key === e.target.value);
                          setNewIssueLink({ key, name });
                        }}
                      >
                        <option value=''>
                          None
                        </option>
                        {backlogIssues.filter(issue =>
                          (issue.id !== focusedIssueId)
                          &&
                          (issue.type !== 'Epic')
                          &&
                          (focusedIssue.issueLinks.find(linkObj => linkObj.linkedIssue.id === issue.id) === undefined)
                        ).map(issue => (
                          <option
                            key={issue.id}
                            value={issue.key}
                          >
                            {issue.key}, {issue.title}
                          </option>
                        ))}
                      </select>
                      <select
                        style={{ marginLeft: '20px' }}
                        value={newIssueLinkType}
                        onChange={(e) => setNewIssueLinkType(e.target.value)}
                      >
                        <option value='DEPENDS_ON'>
                          Depends on
                        </option>
                        <option value='RELATES_TO'>
                          Relates to
                        </option>
                      </select>
                      <button
                        style={{ backgroundColor: "grey" }}
                        onClick={() => addNewValueToField('link')}
                      >
                        +
                      </button>
                      {Boolean(newIssueLinkError) && <p style={{ color: 'crimson', fontSize: '0.8em' }}>{newIssueLinkError}</p>}
                    </>
                  }
                </div>
                {/* Edit Issue */}
                {hasWrite &&
                  <div style={{ textAlign: "center", marginTop: "20px" }}>
                    <Link to={`/project/${projectId}/issue_preview/${focusedIssueId}`} id="editIssueLink">
                      <img id="pencilIcon" src={edit_icon} alt="Pencil" ></img>
                      Edit Issue
                    </Link>
                  </div>
                }
              </div>
            }
            {sprint === "show"
              // current sprint (if there is one)
              ?
              <div className="sprint">
                <div className="head">
                  <div className="info">
                    <h2>Active Sprint</h2>
                    <span style={{ fontWeight: "bold" }}>Due date: </span>
                    <span className="dueDate:">12/5/2022</span>
                    <br />
                    <span style={{ fontWeight: "bold" }}>Time Remaining: </span>
                    <span className="timeRemaining:">8 days</span>
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
                <div id="createSprintContent">
                  <span id="createSprintText">No active sprint</span>
                  <br></br>
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
                      value={newPriority}
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
                      onChange={(e) => { setNewType(e.target.value); }}
                      value={newType}
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
                      onChange={(e) => { setNewEpicLink(e.target.value !== 'None' ? e.target.value : null); }}
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