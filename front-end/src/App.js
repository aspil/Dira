import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans'
import { BrowserRouter as Router, Redirect, Route, Switch, useHistory} from 'react-router-dom';
import ProjectMain from './components/ProjectMain'
import PasswordRecovery from './components/PasswordRecovery';
import ActiveSprint from './components/ActiveSprint';
import Members from './components/Members';
import Epics from './components/Epics';
import Backlog from './components/Backlog';
import { useState } from 'react';
import { DiraProjectClient, DiraUserClient } from "dira-clients";
import CreateProject from './components/CreateProject';

function App() {
  const history = useHistory();
  const [token, setToken] = useState(localStorage.jwtoken);
  const [userInfo, setUserInfo] = useState({
    username: null,
    email: null,
    id: null
  });
  const userClient = new DiraUserClient();
  const projectClient = new DiraProjectClient();

  return (
    <Router>
      <div className="App">
          <Switch>
            <Route exact path="/">
              { token !== undefined && <Redirect to="/proj_main" /> }
              { token === undefined && <Home/> }
            </Route>
            <Route path="/sign_in">
              { token !== undefined && <Redirect to="/proj_main" /> }
              { token === undefined && <Login setToken={setToken} 
                                              client={userClient} 
                                              setUserInfo={setUserInfo} 
                                        /> }
            </Route>
            <Route path="/register">
              { token !== undefined && <Redirect to="/proj_main" /> }
              { token === undefined && <Register client={userClient} /> }
            </Route>
            <Route path="/recover">
              { token !== undefined && <Redirect to="/proj_main" /> }
              { token === undefined && <PasswordRecovery/> }
            </Route>

            <Route path="/pricing">
              <Plan/>
            </Route>

            <Route path="/proj_main">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <ProjectMain username={userInfo.username}/> }
            </Route>
            <Route path="/backlog">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <Backlog username={userInfo.username}/> }
            </Route>
            <Route path="/members">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <Members username={userInfo.username}/> }
            </Route>
            <Route path="/active_sprint">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <ActiveSprint username={userInfo.username}/> }
            </Route>
            <Route path="/epics">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <Epics username={userInfo.username}/> }
            </Route>
            <Route path="/create_project">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <CreateProject 
                                          projectClient={projectClient}
                                          token={token}
                                        /> }
            </Route>
          </Switch>
      </div>
    </Router>
  );
}

export default App;