import { AppBar, List, ListItem, ListItemIcon, ListItemText, makeStyles, Toolbar, Typography } from "@material-ui/core"
import { HomeOutlined } from "@material-ui/icons"
import { useHistory } from "react-router"

const useStyles = makeStyles({
  root: {
    display: 'flex'
  },
  title: {
    flexGrow: 1
  },
  list: {
    display: 'flex',
    flexDirection: 'row'
  },
})

const Nav = () => {
  const classes = useStyles()
  const history = useHistory()
  const appBarItems = [
    {
      text: 'Home',
      icon: <HomeOutlined color='secondary' />,
      path: '/'
    },
    {
      text: 'Login',
      icon: null,
      path: '/login'
    }
  ]

  return (
    <AppBar 
      className={classes.root}
      elevation={0}
    >
      <Toolbar>
        <Typography 
          className={classes.title}
          variant='h5'
        >
          Dira
        </Typography>
        
        <List className={classes.list}>
          {appBarItems.map((item) => (
            <ListItem
              button
              key={item.text}
              onClick={() => history.push(item.path)}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItem>
          ))}
        </List>

      </Toolbar>
    </AppBar>
  );
}
 
export default Nav;