import AppHead from './components/AppHead'
import LoginForm from './components/LoginForm';

import { createMuiTheme, ThemeProvider } from '@material-ui/core'
import { indigo, teal } from '@material-ui/core/colors';

const theme = createMuiTheme({
  palette: {
    primary: indigo,
    secondary: teal 
  }
})

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <AppHead/>
      <LoginForm/>
    </ThemeProvider>
  );
}
