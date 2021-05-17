const HomeNav = () => {
  return (
    <div className="home_nav">
      <h1>Dira</h1>
      <div className="links">
        <a href="/">Features</a>
        <a href="/pricing">Pricing</a>
        <a href="/contact">Contact</a>
        <a href="/sign_in">Sign in</a>
        <a href="/sign_up" id="boxxed_button">Sign up</a>
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