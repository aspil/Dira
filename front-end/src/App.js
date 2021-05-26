import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans'
import { BrowserRouter as Router, Redirect, Route, Switch, useHistory} from 'react-router-dom';
import ProjectMain from './components/ProjectMain'
import PasswordRecovery from './components/PasswordRecovery';
import ActiveSprint from './components/ActiveSprint';
import Members from './components/Members';
import Backlog from './components/Backlog';
import { useState } from 'react';
import dira_client from "dira-clients";

function App() {
  const history = useHistory();
  const [token, setToken] = useState(localStorage.jwtoken);
  const client = new dira_client();

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
              { token === undefined && <Login setToken={setToken} client={client} /> }
            </Route>
            <Route path="/register">
              { token !== undefined && <Redirect to="/proj_main" /> }
              { token === undefined && <Register client={client} /> }
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
              { token !== undefined && <ProjectMain/> }
            </Route>
            <Route path="/backlog">
              {/* { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <Backlog/> } */}
              <Backlog/>
            </Route>
            <Route path="/members">
              { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <Members/> }
            </Route>
            <Route path="/active_sprint">
              {/* { token === undefined && <Redirect to="/sign_in" /> }
              { token !== undefined && <ActiveSprint/> } */}
              <ActiveSprint/>
            </Route>
          </Switch>
      </div>
    </Router>
  );
}

export default App;