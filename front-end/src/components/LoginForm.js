import { Button, TextField, makeStyles, Container } from '@material-ui/core'
import { useState } from 'react'

const useStyles = makeStyles({
  field: {
    margin: '20px 0px',
    display: 'block'
  }
})

const LoginForm = () => {
  const classes = useStyles()

  const [user, setUser] = useState('')
  const [pass, setPass] = useState('')
  const [userError, setUserError] = useState(false)
  const [passError, setPassError] = useState(false)

  const handleSubmit = (e) => {
    e.preventDefault()

      setUserError(!user)
      setPassError(!pass)

    if (user && pass) {
      console.log(`${user}\n${pass}`)
    }
  }

  return (
    <Container>
      <form 
        noValidate 
        autoComplete='off'
        onSubmit = {handleSubmit}
      > 
        <TextField 
          className={classes.field} 
          label='Username'
          value={user}
          onChange={(e) => setUser(e.target.value)}
          variant='outlined'
          required
          color='primary'
          error={userError}
        />
        <TextField 
          className={classes.field} 
          label='Password'
          value={pass}
          onChange={(e) => setPass(e.target.value)}
          variant='outlined'
          required
          color='primary'
          error={passError}
        />
        <Button 
          variant='contained'
          color='secondary'
          type='submit'
        >
          Submit
        </Button>
      </form>
    </Container>
  )
}

export default LoginForm
