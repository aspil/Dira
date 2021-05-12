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

import HomeNav from './components/HomeNav';
import Home from './components/Home';

function App() {
  return (
    <div className="App">
      <Home/>
    </div>
  );
}

export default App;