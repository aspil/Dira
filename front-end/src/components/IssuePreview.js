import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';
import x_icon from "../Images/x_icon.png"

const IssuePreview = () => {
  return (
    // Gia na doylepsei to sidebar
    <div className="issuePreview proj_page">
      <div className="center_content">
        <SideNav />
        <main>
          {/* Content */}
          <div id="content">
            <div className="issuePreviewWrapper">
              <div className="headWrapper">
                <text id="issueEpic">Epic of this issue</text>
                <h1 id="issueName">Issue X</h1>
                <div>
                  <text className="label" id="issueType">Type:</text>
                  <text className="answer" id="issueType">"issuetype"</text>
                </div>
              </div>
              <div style={{ margin: "auto", marginBottom: "15px", alignItems: "center", display: "flex", justifyContent: "space-between" }}>
                <div>
                  <text className="label" id="reporter">Reporter:</text>
                  <text className="answer" id="reporter">"reporter"</text>
                  <br></br>
                  <text className="label" id="assignee">Assignee:</text>
                  <text className="answer" id="assignee">"assignee"</text>
                </div>
              </div>
              <div className="issueInfoWrapper">
                {/* Form */}
                <form className="issuePreviewForm">
                  <div style={{ margin: "auto", alignItems: "center", display: "flex", justifyContent: "space-between" }}>
                    {/* Priority */}
                    <div>
                      <text>Priority: </text>
                      <select name="priority" id="priority" value="EDW IPARXON VALUE">
                        <option value="low">Low</option>
                        <option value="medium">Medium</option>
                        <option value="high">High</option>
                      </select>
                    </div>
                    {/* Status */}
                    <div>
                      <text>Status: </text>
                      <select name="status" id="status" value="EDW IPARXON VALUE">
                        <option value="new">New</option>
                        <option value="open">Medium</option>
                        <option value="blocked">Blocked</option>
                        <option value="active">Active</option>
                        <option value="toTest">To Test</option>
                        <option value="resolved">Resolved</option>
                      </select>
                    </div>
                    <div>
                      {/* Resolution */}
                      <text>Resolution: </text>
                      <select name="resolution" id="resolution" value="EDW IPARXON VALUE">
                        <option value="unresolved">Unresolved</option>
                        <option value="resolved">Resolved</option>
                      </select>
                    </div>
                  </div>
                  <br />
                  <div style={{ margin: "auto", alignItems: "center", display: "flex" }}>
                    <div>
                      <text>Description:</text>
                      <textarea type="range" placeholder="Issue Description" value="EDW IPARXON VALUE"></textarea>
                    </div>
                    <div style={{ margin: "auto" }}>
                      <text style={{ marginLeft: "15px" }}>Labels:</text>
                      <br></br>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Analysis" type="checkbox" />
                        <label for="Analysis">Analysis</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Documentation" type="checkbox" />
                        <label for="Documentation">Documentation</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="UI" type="checkbox" />
                        <label for="UI">UI</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Backend" type="checkbox" />
                        <label for="Backend">Backend</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Database" type="checkbox" />
                        <label for="Database">Database</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Bug" type="checkbox" />
                        <label for="Bug">Bug</label>
                      </div>
                      <div className="checkboxAndLabel">
                        <input className="labelInput" id="Testing" type="checkbox" />
                        <label for="Testing">Testing</label>
                      </div>

                    </div>
                  </div>
                  <div style={{ textAlign: "center" }}>
                    <button>Save Changes</button>
                  </div>
                </form>
              </div>
              {/* button */}

            </div>
          </div>
        </main>
      </div>
      <Footer />
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



// Comments
// Assignee
// Reporter
// Dates: 
  // Created, 
  // Updated, 
  // Due date
// Time tracking: Estimated, Remaining, Logged
// Fix version(s)
// Issue Links: Depends on, Relates to
// Epic link
