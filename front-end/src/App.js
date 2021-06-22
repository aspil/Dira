import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import ProjectMain from './components/ProjectMain';
import ProjectNav from './components/ProjectNav';
import PasswordRecovery from './components/PasswordRecovery';
import ChangePassword from './components/ChangePassword';
import ActiveSprint from './components/ActiveSprint';
import Members from './components/Members';
import Epics from './components/Epics';
import Backlog from './components/Backlog';
import IssuePreview from './components/IssuePreview';
import { useEffect, useState } from 'react';
import { DiraProjectClient, DiraUserClient } from "dira-clients";
import CreateProject from './components/CreateProject';
import HomeNav from './components/HomeNav';
import Footer from './components/Footer';

const userClient = new DiraUserClient();
const projectClient = new DiraProjectClient();

function App() {
  const [token, setToken] = useState(localStorage.getItem('JWToken') || undefined);
  const [userInfo, setUserInfo] = useState(JSON.parse(localStorage.getItem('userInfo')) || {
    username: undefined,
    email: undefined,
    plan: undefined,
    id: undefined
  });
  const [isLogged, setIsLogged] = useState(localStorage.getItem('JWToken') ? true : false);
  const [showHomeNav, setShowHomeNav] = useState(true);
  const [showFooter, setShowFooter] = useState(false);
  const [showFooterStyles, setShowFooterStyles] = useState(false);
  const [stayLogged, setStayLogged] = useState(true);

  const setPremiumPlan = () => {
    const info = { ...userInfo };
    info.plan = "PREMIUM";
    setUserInfo(info);
  }

  useEffect(() => {
    if (token) {
      projectClient.set_authorization_token(token);
    }
  }, [token]);

  useEffect(() => {
    const doBeforeUnload = () => {
      localStorage.setItem('JWToken', token);
      localStorage.setItem('userInfo', JSON.stringify(userInfo));
    }

    if (stayLogged && isLogged) {
      window.addEventListener('beforeunload', doBeforeUnload);
      return () => {
        window.removeEventListener('beforeunload', doBeforeUnload);
      }
    } else {
      window.removeEventListener('beforeunload', doBeforeUnload);
    }
  }, [stayLogged, isLogged, token, userInfo])

  const doLogout = () => {
    setToken(undefined);
    setUserInfo({
      username: undefined,
      email: undefined,
      plan: undefined,
      id: undefined
    });
    localStorage.clear();
    setIsLogged(false);
  }

  const showHomeNavHook = () => {
    setShowHomeNav(false);

    return function cleanup() {
      setShowHomeNav(true);
    }
  }

  const showFooterHook = () => {
    setShowFooter(true);

    return function cleanup() {
      setShowFooter(false);
    }
  }

  const footerStylesHook = () => {
    setShowFooterStyles(true);

    return function cleanup() {
      setShowFooterStyles(false);
    }
  }

  return (
    <Router>
      <div className="App">
        {isLogged && <ProjectNav username={userInfo.username} doLogout={doLogout} />}
        {!isLogged && showHomeNav && <HomeNav />}

        <Switch>
          <Route exact path="/">
            {token !== undefined && <Redirect to="/proj_main" />}
            {token === undefined && <Home footerHandle={showFooterHook} />}
          </Route>
          <Route path="/sign_in">
            {token !== undefined && <Redirect to="/proj_main" />}
            {token === undefined &&
              <Login
                setToken={setToken}
                client={userClient}
                setUserInfo={setUserInfo}
                setIsLogged={setIsLogged}
                setStayLogged={setStayLogged}
                stayLogged={stayLogged}
                navHandle={showHomeNavHook} />
            }
          </Route>
          <Route path="/register">
            {token !== undefined && <Redirect to="/proj_main" />}
            {token === undefined && <Register client={userClient} navHandle={showHomeNavHook} />}
          </Route>
          <Route path="/recover">
            {token !== undefined && <Redirect to="/proj_main" />}
            {token === undefined && <PasswordRecovery navHandle={showHomeNavHook} />}
          </Route>
          <Route path="/change_password">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <ChangePassword
              navHandle={showHomeNavHook}
            />}

          </Route>

          <Route path="/pricing">
            <Plan
              userClient={userClient}
              userId={userInfo.id}
              userPlan={userInfo.plan}
              isLogged={isLogged}
              setPremiumPlan={setPremiumPlan}
            />
          </Route>

          <Route path="/proj_main">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <ProjectMain
              userInfo={userInfo}
              userClient={userClient}
              token={token}
              doLogout={doLogout}
              footerHandle={showFooterHook}
              footerStylesHandle={footerStylesHook}
              userPlan={userInfo.plan}
              projectClient={projectClient} />}
          </Route>
          <Route path="/backlog/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <Backlog
              username={userInfo.username}
              token={token}
              doLogout={doLogout}
              footerHandle={showFooterHook}
              projectClient={projectClient}
            />}
          </Route>
          <Route path="/members/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined &&
              <Members
                username={userInfo.username}
                doLogout={doLogout}
                projectClient={projectClient}
                userId={userInfo.id}
                footerHandle={showFooterHook} />
            }
          </Route>
          <Route path="/active_sprint/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <ActiveSprint username={userInfo.username} footerHandle={showFooterHook} />}
          </Route>
          <Route path="/project/:projectId/issue_preview/:issueId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <IssuePreview username={userInfo.username} footerHandle={showFooterHook} />}
          </Route>
          <Route path="/create_project">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <CreateProject
              projectClient={projectClient}
              userPlan={userInfo.plan}
            />}
          </Route>
        </Switch>

        {showFooter &&
          <div style={showFooterStyles ? { clear: "both", position: "absolute", bottom: "0", width: "100%" } : {}}>
            <Footer />
          </div>
        }
      </div>
    </Router>
  );
}

export default App;
