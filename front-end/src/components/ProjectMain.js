import { useEffect, useState } from "react";
import { Link, useHistory } from 'react-router-dom';
import edit_icon from "../Images/edit_icon.png"
import x_icon from "../Images/x_icon.png"
import trashcan_icon from "../Images/trashcan_icon.png"


const ProjectMain = ({ userInfo, userClientRef, userPlan, doLogout, footerHandle, footerStylesHandle, projectClientRef }) => {
  const [listState, setListState] = useState("showProjects");
  const history = useHistory()
  const [projects, setProjects] = useState([]);
  const [current_project, handleCurrentProject] = useState({
    id: null,
    description: '',
    key: '',
    name: '',
    visibility: ''
  });
  const [editError, setEditError] = useState('');
  const [publicProjects, setPublicProjects] = useState([]);


  const editCurrProjField = (field, e) => {
    const newProj = { ...current_project };
    newProj[field] = e.target.value;

    handleCurrentProject(newProj);
  }


  // Edit project popup handlers
  const [edit_project_popup, handleEditProject] = useState("hide");
  const hideEditProject = () => {
    handleEditProject("hide");
  }
  const showEditProject = (project) => {
    handleCurrentProject(project);
    setEditError('');
    handleEditProject("show");
  }
  const handleEditProjectButtonClick = (e) => {
    e.preventDefault();
    setEditError('');
    if (!(current_project.description && current_project.key && current_project.name)) {
      setEditError('Please fill in all fields');
      return;
    }

    projectClientRef.current.update_project_with_id(current_project.id, {
      "id": current_project.id,
      "description": current_project.description,
      "key": current_project.key,
      "name": current_project.name,
      "visibility": current_project.visibility
    }).then(res => {
      console.log(res);
      fetchAllProjects();
      hideEditProject();
    }).catch(err => {
      console.log('error during update');
      setEditError('Couldn\'t update project');
      console.log(err);
    });

  }

  const handleDeleteProject = (id) => {
    projectClientRef.current.delete_project_by_id(id)
      .then(() => {
        fetchAllProjects();
      })
      .catch(err => {
        console.log('error during deletion');
        console.log(err);
      });
  }

  const swapList = () => {
    if (listState === "showProjects") {
      setListState("showIssues");
    }
    else {
      setListState("showProjects");
    }
  }

  const fetchAllProjects = () => {
    userClientRef.current.get_user_projects(userInfo.id).then((res) => {
      console.log(res);
      setProjects(res);
    }).catch((err) => {
      console.log('error while fetching user projects');
      console.log(err);
    });
  }

  const fetchAllPublicProjects = () => {
    projectClientRef.current.get_all_projects().then((res) => {
      setPublicProjects(res.filter(proj => proj.visibility === 'PUBLIC' && !Boolean(projects.find(p => p.id === proj.id))));
    }).catch((err) => {
      console.log('error while fetching all projects');
      console.log(err);
    });
  }

  useEffect(fetchAllProjects, [userInfo.id, userClientRef]);
  useEffect(fetchAllPublicProjects, [projects, projectClientRef]);

  useEffect(footerHandle, [footerHandle]);
  useEffect(footerStylesHandle, [footerStylesHandle]);

  return (
    <div className="projectmain">

      <div className="leftPanel" style={{ width: "60%", margin: "0.5%" }}>
        {/* projectButtons */}
        <div className="projectButtons">
          <button onClick={() => { history.push("/create_project") }}> + New Project</button>
        </div>
        <div style={{ clear: "both" }}>
          {/* listButtons */}
          <div className="listButtons">
            {listState === "showProjects" &&
              <div>
                <button className="pressedButton" style={{ borderTopLeftRadius: "5px" }}>My Projects</button>
                <button className="unpressedButton" style={{ borderTopRightRadius: "5px" }} onClick={swapList}>Dira Public Projects</button>
              </div>
            }
            {listState === "showIssues" &&
              <div>
                <button className="unpressedButton" style={{ borderTopLeftRadius: "5px" }} onClick={swapList}>My Projects</button>
                <button className="pressedButton" style={{ borderTopRightRadius: "5px" }}>Dira Public Projects</button>
              </div>
            }
          </div>
          {/* tables */}
          {listState === "showProjects" &&
            <table id="main_table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Key</th>
                  <th>Visibility</th>
                </tr>
              </thead>
              <tbody>
                {projects.map(project => (
                  <tr key={project.id}>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.name}</td>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.description}</td>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.key}</td>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.visibility}</td>
                    <td style={{ position: "absolute", borderWidth: "0", padding: "0" }}>
                      <img id="pencilIcon" src={edit_icon} alt="Pencil" onClick={() => showEditProject(project)}></img>
                      <img id="trashcanIcon" src={trashcan_icon} alt="Trashcan" onClick={() => { handleDeleteProject(project.id); }}></img>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          }
          {/* Edit Project Popop */}
          {edit_project_popup === "show" &&
            <div className="createPopup">
              <div>
                <h2>Edit Project</h2>
                <img src={x_icon} alt="accountIcon" onClick={hideEditProject}></img>
              </div>
              <br />
              <br />
              <form className="editProjectForm" style={{ textAlign: "left", fontWeight: "bold" }}
                onSubmit={handleEditProjectButtonClick} noValidate>
                <p>Name:</p>
                <input type="text" id="projectName" placeholder="Project Title" value={current_project.name} onChange={(e) => { editCurrProjField('name', e) }} ></input>
                <p>Key:</p>
                <input type="text" id="projectKey" placeholder="Project Key" value={current_project.key} onChange={(e) => { editCurrProjField('key', e) }} ></input>
                <p>Description:</p>
                <textarea type="range" placeholder="Project Description" value={current_project.description} onChange={(e) => { editCurrProjField('description', e) }}></textarea>
                <p>Access:</p>
                <div>
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <div className="accessOptions">
                      <input className="accessInput" type="radio" id="public"
                        name="visibility" value="PUBLIC" defaultChecked={current_project.visibility === "PUBLIC"}
                        onClick={(e) => editCurrProjField('visibility', e)}
                      />
                      <label htmlFor="public">Public</label>
                    </div>
                    <div className="accessOptions">
                      <input className="accessInput" type="radio" id="private"
                        name="visibility" value="PRIVATE" defaultChecked={current_project.visibility === "PRIVATE"}
                        onClick={(e) => editCurrProjField('visibility', e)}
                        disabled={userPlan === "STANDARD"}
                      />
                      <label htmlFor="private" style={userPlan === "STANDARD" ? { opacity: "0.5" } : {}}>Private</label>
                    </div>
                  </div>
                  {userPlan === "STANDARD" && <p style={{ fontWeight: "normal" }}><Link to="/pricing" style={{ color: "blue" }}>Upgrade to Premium</Link> to create private projects.</p>}
                </div>
                <p
                  style={Boolean(editError) ? { color: 'crimson', textAlign: 'center' } : { display: 'none' }}
                >
                  {editError}
                </p>
                <div style={{ textAlign: "center" }}>
                  <button >Save Changes</button>
                </div>
              </form>
            </div>
          }
          {listState === "showIssues" &&
            <table id="main_table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Key</th>
                </tr>
              </thead>
              <tbody>
                {publicProjects.map(project => (
                  <tr key={project.id}>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.name}</td>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.description}</td>
                    <td onClick={() => { history.push(`/backlog/${project.id}`) }}>{project.key}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          }
        </div>
      </div>

    </div>
  );
}

export default ProjectMain;

