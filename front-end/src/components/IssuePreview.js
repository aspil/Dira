import SideNav from './SideNav';
import { useEffect } from "react";
import { useHistory, useParams } from 'react-router-dom';
import { useState } from 'react';
import { useRef } from 'react';
import { DiraIssueClient } from 'dira-clients';

const IssuePreview = ({ footerHandle, token }) => {
  const { projectId, issueId } = useParams();
  const [issue, setIssue] = useState(null);
  const [errorMsg, setErrorMsg] = useState('');
  const history = useHistory();
  useEffect(footerHandle, [footerHandle]);

  const issueClientRef = useRef(new DiraIssueClient(projectId));
  useEffect(() => {
    if (token) {
      issueClientRef.current.set_authorization_token(token);
    }
  }, [token, issueClientRef]);

  const fetchIssue = () => {
    if (!issueClientRef.current.headers.Authorization) {
      return;
    }
    issueClientRef.current.get_issue(issueId)
      .then(res => {
        console.log(res);
        setIssue(res);
      })
      .catch(err => {
        console.log(err);
      })
  }
  useEffect(fetchIssue, [issueClientRef.current.headers.Authorization, issueClientRef, issueId]);

  const editIssueField = (field, e) => {
    const newIssue = { ...issue };
    let newValue = e.target.value;
    if (field === 'resolved' && e.target.value === 'false') {
      newValue = false;
    }
    else if (field === 'resolved' && e.target.value === 'true') {
      newValue = true;
    }
    newIssue[field] = newValue;

    setIssue(newIssue);
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!(issue.description)) {
      setErrorMsg('Please fill in all fields');
      return;
    }
    setErrorMsg('');

    issueClientRef.current.update_issue(issueId, issue)
      .then(res => {
        console.log(res);
        history.push(`/project/${projectId}/backlog`);
      })
      .catch(err => {
        console.log(err);
        setErrorMsg('Couldn\'t update issue');
      });

  }

  return (
    <div className="issuePreview proj_page">
      <div className="center_content">
        <SideNav />
        <main>
          {/* Content */}
          {issue && <div id="content">
            <div className="issuePreviewWrapper">
              <div className="headWrapper">
                {/* <span id="issueEpic">Epic of this issue</span> */}
                <h1 id="issueName">{issue.title}</h1>
                <div>
                  <span className="label" id="issueType">Type:</span>
                  <span className="answer" id="issueType">{issue.type}</span>
                </div>
              </div>
              <div style={{ margin: "auto", marginBottom: "15px", alignItems: "center", display: "flex", justifyContent: "space-between" }}>
                <div>
                  <span className="label" id="reporter">Reporter: </span>
                  <span className="answer" id="reporter">{issue.reporter}</span>
                  <br></br>
                  <span className="label" id="assignee">Assignee: </span>
                  <span className="answer" id="assignee">
                    {issue.assignee ? issue.assignee : '-'}
                  </span>
                </div>
              </div>
              <div className="issueInfoWrapper">
                {/* Form */}
                <form className="issuePreviewForm" noValidate onSubmit={handleSubmit}>
                  <div style={{ margin: "auto", alignItems: "center", display: "flex", justifyContent: "space-between" }}>
                    {/* Priority */}
                    <div>
                      <span className="label">Priority: </span>
                      <select
                        name="priority"
                        id="priority"
                        onChange={(e) => editIssueField('priority', e)}
                        value={issue.priority}
                      >
                        <option value="Low">Low</option>
                        <option value="Normal">Normal</option>
                        <option value="Major">Major</option>
                        <option value="Blocked">Blocked</option>
                      </select>
                    </div>
                    {/* Status */}
                    <div>
                      <span className="label">Status: </span>
                      <select
                        name="status"
                        id="status"
                        onChange={(e) => editIssueField('status', e)}
                        value={issue.status}
                      >
                        <option value="Open">Open</option>
                        <option value="Blocked">Blocked</option>
                        <option value="ToTest">To Test</option>
                        <option value="InDevelopment">In Development</option>
                        <option value="New">New</option>
                      </select>
                    </div>
                    <div>
                      {/* Resolution */}
                      <span className="label">Resolution: </span>
                      <select
                        name="resolution"
                        id="resolution"
                        onChange={(e) => editIssueField('resolved', e)}
                        value={issue.resolved}
                      >
                        <option value={true}>Resolved</option>
                        <option value={false}>Unresolved</option>
                      </select>
                    </div>
                  </div>
                  <br />
                  <div style={{ margin: "auto", alignItems: "center", display: "flex" }}>
                    <div>
                      <span className="label">Description:</span>
                      <br />
                      <textarea
                        type="range"
                        placeholder="Issue Description"
                        value={issue.description}
                        onChange={(e) => editIssueField('description', e)}
                      />
                    </div>
                  </div>
                  {Boolean(errorMsg) && <p style={{ marginTop: '1em', color: 'crimson', textAlign: 'center' }}>{errorMsg}</p>}
                  <div style={{ textAlign: "center" }}>
                    <button>Save Changes</button>
                  </div>
                </form>
              </div>
              {/* button */}

            </div>
          </div>}
        </main>
      </div>
    </div>
  );
}

export default IssuePreview;


// ID (autogenerated), Key (autogenerated based on a project's key)
// Title
// Type:
// Epic: An issue representing a high level idea of what we want to do/achieve as a whole with requirements. Usually an Epic is something that cannot be completed in a Sprint (a sprint is usually 2 weeks)
// Story (feature): A story is an issue representing a fraction of the feature that we want to implement as part of an epic. This is basically a deliverable that can be developed and tested within the time of a sprint based on the requirements that were formed with the product owner
// Bug: An issue that tracks a defect in the implementation of a feature that came up during the sprint or after being released
// Priority: Blocker, Major, Normal, Low
// Status:
  // New: An issue that was just created. We need to fill in details of the issue when we are on this status
  // Open: An issue that has all the details filled in and is ready to be analysed
  // Blocked: An issue that has been descoped and will be probably be prioritized in the future again
  // In Development: An issue that has been analysed and is currently being developed
  // To Test: An issue that has been developed and dev tested and is ready to be tested by a QA
  // Resolved
// Resolution
  // Unresolved: It has not been resolved yet
  // Done: It has been successfully developed and tested
// Labels: A custom field that can be filled by the user in order to give a special meaning to the ticket (e.g part of specific functionality features)
// Description
// Assignee
// Reporter

// Comments
// Dates: 
  // Created, 
  // Updated, 
  // Due date
// Time tracking: Estimated, Remaining, Logged
// Fix version(s)
// Issue Links: Depends on, Relates to
// Epic link
