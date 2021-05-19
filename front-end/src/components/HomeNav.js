import { Link } from "react-router-dom";

const HomeNav = () => {
  return (
    <div className="home_nav">
      <h1><Link to="/">Dira</Link></h1>
      <div className="links">
        <Link to="/">Features</Link>
        <Link to="/pricing">Pricing</Link>
        <Link to="/contact">Contact</Link>
        <Link to="/sign_in">Sign in</Link>
        <Link to="/register" id="boxxed_button">Sign up</Link>
      </div>
    </div>
  );
}
 
export default HomeNav;


// import { AppBar, List, ListItem, ListItemIcon, ListItemText, makeStyles, Toolbar, Typography } from "@material-ui/core"
// import { HomeOutlined } from "@material-ui/icons"
// import { useHistory } from "react-router"

// const useStyles = makeStyles({
//   root: {
//     display: 'flex'
//   },
//   title: {
//     flexGrow: 1
//   },
//   list: {
//     display: 'flex',
//     flexDirection: 'row'
//   },
// })

// const Nav = () => {
//   const classes = useStyles()
//   const history = useHistory()
//   const appBarItems = [
//     {
//       text: 'FEATURES',
//       icon: null,
//       path: '/features'
//     },
//     {
//       text: 'PRICING',
//       icon: null,
//       path: '/pricing'
//     },
//     {
//       text: 'CONTACT',
//       icon: null,
//       path: '/contact'
//     },
//     {
//       text: 'sign in',
//       icon: null,
//       path: '/sign in'
//     },
//     {
//       text: 'sign up',
//       icon: null,
//       path: '/sign up'
//     }
//   ]

//   return (
//     <AppBar 
//       className={classes.root}
//       elevation={0}
//     >
//       <Toolbar>
//         <Typography 
//           className={classes.title}
//           variant='h5'
//         >
//           Dira
//         </Typography>
        
//         <List className={classes.list}>
//           {appBarItems.map((item) => (
//             <ListItem
//               button
//               key={item.text}
//               onClick={() => history.push(item.path)}
//             >
//               <ListItemIcon>{item.icon}</ListItemIcon>
//               <ListItemText primary={item.text} />
//             </ListItem>
//           ))}
//         </List>

//       </Toolbar>
//     </AppBar>
//   );
// }
 
// export default Nav;