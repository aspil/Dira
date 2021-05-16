import HomeNav from './HomeNav';
import logo from "../Images/dira_icon.png"
import Footer from "./Footer"
import { Card, CardContent, Grid, Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core'

const useStyles = makeStyles({
  title: {
    fontSize: '5rem',
    textAlign: 'center',
    color: 'white',
    margin: '5rem 0'
  },
  card: {
    width: '210px'
  },
  cont2: {
    backgroundColor: 'rgb(0, 132, 255)'
  },
  grid: {
    margin: '2rem auto',
    width: '450px'
  },
  planTitle1: {
    color: 'rgb(0, 132, 255)',
    textDecoration: 'underline',
    textUnderlineOffset: '10px',
    textAlign: 'center',
    marginBottom: '1rem'
  },
  planDesc1: {
    color: 'rgb(0, 132, 255)',
    textAlign: 'center'
  },
  bttn1: {
    border: '0',
    backgroundColor: 'rgb(0, 132, 255)',
    fontSize: '3rem',
    padding: '1rem',
    borderRadius: '50%',
    marginLeft: '20%',
    marginBottom: '1rem'
  },
  planTitle2: {
    color: 'white',
    textDecoration: 'underline',
    textUnderlineOffset: '10px',
    textAlign: 'center',
    marginBottom: '1rem'
  },
  planDesc2: {
    color: 'white',
    textAlign: 'center'
  },
  bttn2: {
    border: '0',
    backgroundColor: 'white',
    fontSize: '3rem',
    color: 'rgb(0, 132, 255)',
    padding: '1rem',
    borderRadius: '50%',
    marginLeft: '20%',
    marginBottom: '1rem'
  },
})

const Plan = () => {
  const classes = useStyles()

  return (
    <div >
      <div className='home_grad1'>
        <HomeNav/>
        <h1 className={classes.title}>
          Plans + Pricing
        </h1>
        <Grid container className={classes.grid} spacing={2}>
          <Grid item className={classes.card}>
            <Card >
              <CardContent>
                <h1 className={classes.planTitle1}>Standard</h1>
                <button className={classes.bttn1}>0&euro;</button>
                <p className={classes.planDesc1}>
                  For small teams looking for an easy
                  tool to organize and track their work
                </p>
              </CardContent>
            </Card>
          </Grid>
          <Grid item className={classes.card}>
            <Card >
              <CardContent className={classes.cont2}>
                <h1 className={classes.planTitle2}>Premium</h1>
                <button className={classes.bttn2}>
                  12&euro; 
                  <p style={{fontSize:'1rem', color:'inherit'}}>per user</p>
                </button>
                <p className={classes.planDesc2}>
                  For startups and organizations that require a fully capable
                  tool to collaborate and track work efficiently
                </p>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </div>
    </div>
  );
}
 
export default Plan;