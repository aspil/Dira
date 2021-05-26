import x_icon from "../Images/x_icon.png"

import ProjectNav from './ProjectNav'
import Footer from './Footer'
import SideNav from './SideNav'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { Search } from '@material-ui/icons'

const Backlog = () => {

  const [create_issue_popup, handleMembersPopup] = useState("show");

  const hide_create_issue_popup = () => {
    handleMembersPopup("hide");
}

const show_create_issue_popup = () => {
    handleMembersPopup("show");
}

const handlePopupButtonClick = () => {
    hide_create_issue_popup();
}

  const [issues, setIssues] = useState([
    {name: "issue 1"},{name: "issue 2"}
  ])

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
          <h1>Project Name</h1>
          <div className="flex_cont">
            <div className="issues">
              <div className="head">
                <div className="info">
                  <h2>Backlog</h2>
                  <p className="issue_total">6 issues</p>
                </div>
                <form onSubmit={handleSubmit}>
                  <input type="search" placeholder="Search for and issue"/>
                  <button type="submit">
                    <Search fontSize="small" />
                  </button>
                </form>
              </div>
              <ul>
                {issues.map((item) => (
                  <li><Link>{item.name}</Link></li>
                ))}
              </ul>
            </div>
            <div className="sprints"></div>
          </div>
          {create_issue_popup === "show" && 
                    <div className="createIssuePopup">
                        <div>
                            <h2>Add Members</h2>
                            <img src={x_icon} alt="accountIcon" onClick={hide_create_issue_popup}></img>
                        </div>
                        <form className="members_form">
                            <input type="text" placeholder="Title"></input>
                            <select name="cars" id="cars">
                              <option value="volvo">Volvo</option>
                              <option value="saab">Saab</option>
                              <option value="opel">Opel</option>
                              <option value="audi">Audi</option>
                            </select>
                            <button onClick={handlePopupButtonClick}>Create Issue</button>
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