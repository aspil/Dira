import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Plan from './components/Plans';
import Reports from './components/Reports';
import { Redirect, Route, Switch, useHistory } from 'react-router-dom';
import ProjectMain from './components/ProjectMain';
import ProjectNav from './components/ProjectNav';
import PasswordRecovery from './components/PasswordRecovery';
import ChangePassword from './components/ChangePassword';
import Members from './components/Members';
import Backlog from './components/Backlog';
import IssuePreview from './components/IssuePreview';
import { useEffect, useRef, useState } from 'react';
import { DiraProjectClient, DiraUserClient } from "dira-clients";
import CreateProject from './components/CreateProject';
import HomeNav from './components/HomeNav';
import Footer from './components/Footer';

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
  const [showProjectNav, setShowProjectNav] = useState(true);
  const [showFooter, setShowFooter] = useState(false);
  const [showFooterStyles, setShowFooterStyles] = useState(false);
  const [stayLogged, setStayLogged] = useState(true);

  const history = useHistory();

  const setPremiumPlan = () => {
    const info = { ...userInfo };
    info.plan = "PREMIUM";
    setUserInfo(info);
  }

  const userClientRef = useRef(new DiraUserClient());
  const projectClientRef = useRef(new DiraProjectClient());

  useEffect(() => {
    if (token) {
      projectClientRef.current.set_authorization_token(token);
    }
  }, [token, projectClientRef]);

  const refreshToken = () => {
    if (projectClientRef.current.headers.Authorization) {
      projectClientRef.current.keepalive()
        .then((res) => {
          console.log(res);
          setToken(res.token);
        })
        .catch(err => {
          console.log(err);
          doLogout();
        });
    }

    setTimeout(refreshToken, 15 * 60 * 1e3);
  };
  useEffect(() => {
    setTimeout(refreshToken, 15 * 60 * 1e3); // 15 minutes
  }, []);

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
    history.push('/');
  }

  const showHomeNavHook = () => {
    setShowHomeNav(false);

    return function cleanup() {
      setShowHomeNav(true);
    }
  }
  const showProjectNavHook = () => {
    setShowProjectNav(false);

    return function cleanup() {
      setShowProjectNav(true);
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

  const fetchAllIssues = (issueClientRef, setIssues, setProjectName, focusedIssueId, setFocusedIssue) => {
    if (!issueClientRef.current.headers.Authorization) {
      return;
    }
    issueClientRef.current.get_all_issues().then((res) => {
      console.log(res);
      setIssues(res.issues);
      setProjectName(res.name);
      if (focusedIssueId) {
        setFocusedIssue(res.issues.find(issue => issue.id === focusedIssueId));
      }
    }).catch((err) => {
      console.log(err);
    });
  }

  const fetchMembers = (projectId, setMembers) => {
    if (!projectClientRef.current.headers.Authorization) {
      return;
    }

    projectClientRef.current.get_all_users_in_project_by_id(projectId).then((res) => {
      setMembers(res.users);
    }).catch((err) => {
      console.log(err);
    });
  }

  return (
    <div className="App">
      {isLogged && showProjectNav && <ProjectNav username={userInfo.username} doLogout={doLogout} />}
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
              userClientRef={userClientRef}
              setUserInfo={setUserInfo}
              setIsLogged={setIsLogged}
              setStayLogged={setStayLogged}
              stayLogged={stayLogged}
              navHandle={showHomeNavHook} />
          }
        </Route>
        <Route exact path={["/register", "/register/:userEmail"]}>
          {token !== undefined && <Redirect to="/proj_main" />}
          {token === undefined && <Register userClientRef={userClientRef} navHandle={showHomeNavHook} />}
        </Route>
        <Route path="/recover">
          {token !== undefined && <Redirect to="/proj_main" />}
          {token === undefined && <PasswordRecovery userClientRef={userClientRef} navHandle={showHomeNavHook} />}
        </Route>
        <Route path="/change_password">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined && <ChangePassword
            navHandle={showProjectNavHook}
            userId={userInfo.userId}
            userClientRef={userClientRef}
          />}

        </Route>

        <Route path="/pricing">
          <Plan
            userClientRef={userClientRef}
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
            userClientRef={userClientRef}
            token={token}
            doLogout={doLogout}
            footerHandle={showFooterHook}
            footerStylesHandle={footerStylesHook}
            userPlan={userInfo.plan}
            projectClientRef={projectClientRef} />}
        </Route>
        <Route path="/project/:projectId/backlog">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined && <Backlog
            userId={userInfo.id}
            username={userInfo.username}
            token={token}
            doLogout={doLogout}
            footerHandle={showFooterHook}
            projectClientRef={projectClientRef}
            fetchAllIssues={fetchAllIssues}
            fetchMembers={fetchMembers}
          />}
        </Route>
        <Route path="/project/:projectId/members">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined &&
            <Members
              username={userInfo.username}
              doLogout={doLogout}
              projectClientRef={projectClientRef}
              userId={userInfo.id}
              footerHandle={showFooterHook}
              fetchMembers={fetchMembers}
            />
          }
        </Route>
        <Route path="/project/:projectId/issue_preview/:issueId">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined && <IssuePreview username={userInfo.username} footerHandle={showFooterHook} token={token} />}
        </Route>
        <Route path="/project/:projectId/graphic_reports">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined && <Reports
            footerHandle={showFooterHook}
            token={token}
            projectClientRef={projectClientRef}
            fetchAllIssues={fetchAllIssues}
            fetchMembers={fetchMembers}
          />}
        </Route>
        <Route path="/create_project">
          {token === undefined && <Redirect to="/sign_in" />}
          {token !== undefined && <CreateProject
            projectClientRef={projectClientRef}
            navHandle={showProjectNavHook}
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
  );
}

export default App;
