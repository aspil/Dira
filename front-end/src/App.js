import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import ProjectMain from './components/ProjectMain';
import ProjectNav from './components/ProjectNav';
import PasswordRecovery from './components/PasswordRecovery';
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

function App() {
  const [token, setToken] = useState(localStorage.getItem('JWToken') || undefined);
  const [userInfo, setUserInfo] = useState(JSON.parse(localStorage.getItem('userInfo')) || {
    username: undefined,
    email: undefined,
    id: undefined
  });
  const [isLogged, setIsLogged] = useState(localStorage.getItem('JWToken') ? true : false);
  const [showHomeNav, setShowHomeNav] = useState(true);
  const [showFooter, setShowFooter] = useState(false);
  const [showFooterStyles, setShowFooterStyles] = useState(false);
  const [stayLogged, setStayLogged] = useState(false);

  const userClient = new DiraUserClient('https://localhost:8080/dira');
  const projectClient = new DiraProjectClient('https://localhost:8080/dira');

  useEffect(() => {
    const doBeforeUnload = () => {
      localStorage.setItem('JWToken', token);
      localStorage.setItem('userInfo', JSON.stringify(userInfo));
    }

    if (stayLogged) {
      window.addEventListener('beforeunload', doBeforeUnload);
      return () => {
        window.removeEventListener('beforeunload', doBeforeUnload);
      }
    } else {
      window.removeEventListener('beforeunload', doBeforeUnload);
    }
  }, [stayLogged, token, userInfo])

  const doLogout = () => {
    setToken(undefined);
    setUserInfo({
      username: undefined,
      email: undefined,
      id: undefined
    });
    localStorage.clear();
    setIsLogged(false);
    setStayLogged(false);
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

          <Route path="/pricing">
            <Plan />
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
              projectClient={projectClient} />}
          </Route>
          <Route path="/backlog/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <Backlog
              username={userInfo.username}
              token={token}
              doLogout={doLogout}
              footerHandle={showFooterHook}
            />}
          </Route>
          <Route path="/members/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined &&
              <Members
                username={userInfo.username}
                doLogout={doLogout}
                footerHandle={showFooterHook} />
            }
          </Route>
          <Route path="/active_sprint/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <ActiveSprint username={userInfo.username} footerHandle={showFooterHook} />}
          </Route>
          <Route path="/epics/:projectId">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <Epics username={userInfo.username} footerHandle={showFooterHook} />}
          </Route>
          <Route path="/issue_preview">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <IssuePreview username={userInfo.username} footerHandle={showFooterHook} />}
          </Route>
          <Route path="/create_project">
            {token === undefined && <Redirect to="/sign_in" />}
            {token !== undefined && <CreateProject
              projectClient={projectClient}
              token={token}
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