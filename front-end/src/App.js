
import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans'
import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import ProjectMain from './components/ProjectMain'
import PasswordRecovery from './components/PasswordRecovery';
import Backlog from './components/Backlog';

function App() {
  return (
    <Router>
      <div className="App">
          <Switch>
            <Route exact path="/">
              <Home />
            </Route>
            <Route path="/sign_in">
              <Login />
            </Route>
            <Route path="/register">
              <Register />
            </Route>
            <Route path="/proj_main">
              <ProjectMain />
            </Route>
            <Route path="/pricing">
              <Plan/>
            </Route>
            <Route path="/recover">
              <PasswordRecovery/>
            </Route>
            <Route path="/backlog">
              <Backlog/>
            </Route>
          </Switch>
      </div>
    </Router>
  );
}

export default App;