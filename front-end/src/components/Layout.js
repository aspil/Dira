import { makeStyles } from "@material-ui/core";
import Nav from "./Home_Nav"

const useStyles = makeStyles(theme => ({
  toolbar: theme.mixins.toolbar
})) 

const Layout = ({children}) => {
  const classes = useStyles()

  return (
    <div>
      {/* app bar */}
      <Nav />

      {/* side drawer */}

      <div>
        <div className={classes.toolbar}/>
        {children}
      </div>
    </div>
  );
}
 
export default Layout;