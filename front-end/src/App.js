// import { createMuiTheme, ThemeProvider } from '@material-ui/core'
// import { teal, indigo } from '@material-ui/core/colors';
// import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// import Home from './components/Home';
// import Layout from './components/Layout';
// import LoginForm from './components/LoginForm';

// const theme = createMuiTheme({
//   palette: {
//     primary: indigo,
//     secondary: teal 
//   },
//   typography: {
//     fontFamily: 'Quicksand',
//     fontWeightLight: 400,
//     fontWeightRegular: 500,
//     fontWeightMedium: 600,
//     fontWeightBold: 700,
//   }
// })

// export default function App() {
//   return (
//     <ThemeProvider theme={theme}>
//       <Router>
//         <Layout>
//           <Switch>
//             <Route exact path="/">
//               <Home />
//             </Route>
//             <Route path ="/login">
//               <LoginForm />
//             </Route>
//           </Switch>
//         </Layout>
//       </Router>
//     </ThemeProvider>
//   );
// }

import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Password_Recovery_1 from './components/Password_Recovery_1';
import Password_Recovery_2 from './components/Password_Recovery_2';
import Password_Recovery_3 from './components/Password_Recovery_3';



function App() {
  return (
    <div className="App">
        <Password_Recovery_1/>
      {/* <Home/> */}
    </div>
  );
}

export default App;