import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import search_icon from "../Images/search_icon.png"
import edit_icon from "../Images/edit_icon.png"
import x_icon from "../Images/x_icon.png"

const ActiveSprint = ({ footerHandle }) => {

    // Issue Panel
    const [issue_panel, handleIssuePanel] = useState("hide");
    const showIssuePanel = () => {
        handleIssuePanel("show")
    }
    const hideIssuePanel = () => {
        handleIssuePanel("hide")
    }

    const [inactiveIssues, setInactiveIssues] = useState([
        { title: 'Takis Papadakis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet', id: 1 },
        { title: 'Lakis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit', id: 2 },
        { title: 'Lakis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 3 },
        { title: 'Akis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 4 },
        { title: 'Papadakis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 5 }
    ])
    const [activeIssues, setActiveIssues] = useState([
        { title: 'Makis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 1 },
        { title: 'Takis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 2 },
        { title: 'Lakis Petroulakis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 3 },
        { title: 'Akis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 4 }
    ])
    const [doneIssues, setDoneIssues] = useState([
        { title: 'Makis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 1 },
        { title: 'Akis', dueDate: '14/5/2021', briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', id: 2 }
    ])

    useEffect(footerHandle, [footerHandle]);

    return (
        <div className="active_sprint proj_page">
            <div className="center_content">
                <SideNav />
                <main>
                    <div id="content">
                        {/* Sprint name */}
                        <h1 style={{ textAlign: "center" }}>"Sprint Title" Board</h1>
                        {/* search */}
                        <div className="search">
                            <img src={search_icon} alt="accountIcon"></img>
                            <input type="text" placeholder="Search Issue"></input>
                        </div>
                        {/* time remaining */}
                        <div className="timeRemaining">
                            <span>Time remaining: </span>
                            <span>X Days</span>

                        </div>
                        {/* issue tables */}
                        <div className="issuesPanel" id="inactiveIssuesPanel">
                            <h2 style={{ textAlign: "center", color: "white" }}>Inactive</h2>
                            <div className="issues" id="inactiveIssues">
                                {inactiveIssues.map(issue => (
                                    <div className="issueBox" onClick={showIssuePanel}>
                                        <p>{issue.briefDescription}</p>
                                        <div className="issueShortInfo">
                                            <p>{issue.title}</p>
                                            <span id="issueEpic">My Epic</span>
                                            <div id="spacer" style={{ height: "13px" }} />
                                            <p id="dueDate">{issue.dueDate}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="issuesPanel" id="activeIssuesPanel">
                            <h2 style={{ textAlign: "center", color: "white" }}>Active</h2>
                            <div className="issues" id="activeIssues">
                                {activeIssues.map(issue => (
                                    <div className="issueBox" onClick={showIssuePanel}>
                                        <p>{issue.briefDescription}</p>
                                        <div className="issueShortInfo">
                                            <p>{issue.title}</p>
                                            <span id="issueEpic">My Epic</span>
                                            <div id="spacer" style={{ height: "13px" }} />
                                            <p id="dueDate">{issue.dueDate}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="issuesPanel" id="doneIssuesPanel">
                            <h2 style={{ textAlign: "center", color: "white" }}>Done</h2>
                            <div className="issues" id="doneIssues">
                                {doneIssues.map(issue => (
                                    <div className="issueBox" onClick={showIssuePanel}>
                                        <p>{issue.briefDescription}</p>
                                        <div className="issueShortInfo">
                                            <p>{issue.title}</p>
                                            <span id="issueEpic">My Epic</span>
                                            <div id="spacer" style={{ height: "13px" }} />
                                            <p id="dueDate">{issue.dueDate}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                        {/* Issue Panel*/}
                        {issue_panel === "show" &&
                            <div className="issuePanel">
                                <div>
                                    <div style={{ display: "flex", justifyContent: "space-between",alignItems:"center" }}>
                                        <h1 id="issueName">Issue Name</h1>
                                        <Link to="/issue_preview" id = "editIssueLink">
                                                <img id="pencilIcon" src={edit_icon} alt="Pencil"></img>
                                                Edit Issue
                                        </Link>
                                        <img src={x_icon} id = "xIcon" alt="x_icon" onClick={hideIssuePanel}></img>

                                    </div>
                                    <br></br>
                                    <span id="issueEpic">Epic of this Issue</span>
                                    <br></br>
                                    <br></br>
                                    <h3>Description</h3>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit
                                    </p>
                                    <br />
                                    <span className="label" id="status">Status: </span>
                                    <span className="answer" id="statusAnswer">Open</span>
                                    <span className="answer" id="priorityAnswer">Major </span>
                                    <span className="label" id="priority">Priority: </span>
                                    <br />
                                    <span className="label" id="resolution">Resolution: </span>
                                    <span className="answer" id="resolutionAnswer">Unresolved</span>
                                    <br />
                                    <br />
                                    <span className="label" id="assignee">Assignee: </span>
                                    <span className="answer" id="assigneeAnswer">Makis</span>
                                    <br />
                                    <span className="label" id="reporter">Reporter: </span>
                                    <span id="reporterAnswer">Takis</span>
                                    <br />
                                    <br />
                                    <span className="label" id="dateCreated">Created on: </span>
                                    <span className="answer" id="dateCreatedAnswer">10/3/2021</span>

                                </div>
                            </div>
                        }
                    </div>
                </main>
            </div>
        </div>
    );
}

export default ActiveSprint;