import HomeNav from './HomeNav';
import { Card, CardContent, Grid, Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import CheckIcon from '@material-ui/icons/Check';
import { Clear } from '@material-ui/icons';

const useStyles = makeStyles({
  title: {
    fontSize: '5rem',
    textAlign: 'center',
    color: 'white',
    margin: '5rem 0'
  },
  subtitle: {
    fontSize: '3rem',
    textAlign: 'center',
    color: 'dimgray',
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
  table: {
    width: '50%',
    margin: '2rem auto',
  }
})

const Plan = () => {
  const classes = useStyles()

  const tableHead = [
    '',
    'Standard',
    'Premium'
  ]

  const tableContent = [
    [
      'User limit (per project)',
      '7',
      '100'
    ],
    [
      'Scrum statistic reports',
      <CheckIcon />,
      <CheckIcon />,
    ],
    [
      'Private projects',
      <Clear />,
      <CheckIcon />,
    ],
    [
      'Project roles',
      <Clear />,
      <CheckIcon />,
    ],
    [
      'Support',
      'Business Hours',
      '24/7 Premium Support'
    ],
  ]
  return (
    <div>
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
      <div>
        <h1 className={classes.subtitle}>
          Features
        </h1>
        
        <div className={classes.table}>
          <TableContainer component={Paper}>
            <Table aria-label="simple table">
              <TableHead>
                <TableRow>
                  {tableHead.map(col => (
                    <TableCell
                      align ={tableHead.indexOf(col) === 0 ? 'left' : 'center'}
                    >
                      {col}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {tableContent.map((row) => (
                  <TableRow key={row[0]}>
                    {row.map((col) => (
                      <TableCell
                        align ={row.indexOf(col) === 0 ? 'left' : 'center'}
                      >
                        {col}
                      </TableCell>
                    ))}
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      </div>
    </div>
  );
}
 
export default Plan;