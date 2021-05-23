import ProjectNav from './ProjectNav'
import Footer from './Footer'
import SideNav from './SideNav'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { Search } from '@material-ui/icons'

const Backlog = () => {
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
        </main>
      </div>
      <Footer />
    </div>
  );
}
 
export default Backlog;