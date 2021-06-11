import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import search_icon from "../Images/search_icon.png"
import edit_icon from "../Images/edit_icon.png"

const ActiveSprint = ({ footerHandle }) => {
    const history = useHistory();

    // Issue Panel
    const [issue_panel, handleIssuePanel] = useState("hide");
    const showIssuePanel = () => {
        handleIssuePanel("show")
    }
    // Edit Issue Button
    const handleEditIssue = () => {
        history.push('issue_preview');
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
                            <text>Time remaining: </text>
                            <text>X Days</text>

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
                                            <text id="issueEpic">My Epic</text>
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
                                            <text id="issueEpic">My Epic</text>
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
                                            <text id="issueEpic">My Epic</text>
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
                                    <div style={{ display: "flex", justifyContent: "space-between" }}>
                                        <h1 id="issueName">Issue Name</h1>
                                        <button id="editIssueButton" onClick={handleEditIssue}>
                                            <img id="pencilIcon" src={edit_icon} alt="Pencil"></img>
                                            Edit Issue
                                        </button>
                                    </div>
                                    <br></br>
                                    <text id="issueEpic">Epic of this Issue</text>
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
                                    <text className="label" id="status">Status: </text>
                                    <text className="answer" id="statusAnswer">Open</text>
                                    <text className="answer" id="priorityAnswer">Major </text>
                                    <text className="label" id="priority">Priority: </text>
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