import ProjectNav from './ProjectNav'
import Footer from './Footer'
import SideNav from './SideNav'
import { useState } from 'react'
import { Link } from 'react-router-dom'

const Backlog = () => {
  const [issues, setIssues] = useState([
    {name: "issue 1"},{name: "issue 2"}
  ])

  return (
    <div className="backlog">
      <ProjectNav />
      <div className="center_content">
        <SideNav />
        <main>
          <h1>Project Name</h1>
          <div className="flex_cont">
            <div className="issues">
              <div className="text">
                <div className="info">
                  <h2>Backlog</h2>
                  <p className="issue_total">6 issues</p>
                </div>
                <input type="search" placeholder="Search for and issue"/>
              </div>
              <ul>
                {issues.map((item) => (
                  <li><Link>{item.name}</Link></li>
                ))}
              </ul>
            </div>
            <div className="sprints"></div>
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
}
 
export default Backlog;