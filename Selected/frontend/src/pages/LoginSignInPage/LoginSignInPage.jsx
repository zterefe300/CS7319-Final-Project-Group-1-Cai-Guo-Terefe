import React, { useState } from "react";
import { 
  Button,
  Card,
  CardContent,
  CardHeader,
  Container,
  Grid2,
  TextField
} from "@mui/material"
import { useNavigate } from "react-router-dom";

const LoginSignInPage = () => {
  const navigate = useNavigate();
  const [ inputValues, setInputValues ] = useState({
    email: "",
    password: ""
  })

  const handleInputChange = (e) => {
    const name = e.target.name
    const value = e.target.value
    setInputValues(prevState => ({
      ...prevState,
      [name]: value
    }))
  }

  const handleLoginClick = () => {
    console.log("Clicked on Login")
    console.log(inputValues)
  }

  const handleAccountCreationClick = () => {
    navigate("/signup", { replace: true })
  }

  return (
    <Container maxWidth="sm">
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa"}}>
        <CardHeader 
          title="Login"
          sx={{
            textAlign: "center"
          }}
        />
        <CardContent>
          <Grid2 container spacing={2}>
            <Grid2 size={12}>
              <TextField 
                name="email"
                id="email"
                label="email"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.email}
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="password"
                id="password"
                label="password"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.password}
              />
            </Grid2>
            <Button 
              variant="contained" 
              onClick={handleLoginClick}
              size="medium"
            >
              Login
            </Button>
            <Button 
              variant="contained" 
              onClick={handleAccountCreationClick} 
              size="medium"
            >
              Create An Account
            </Button>
          </Grid2>
        </CardContent>
      </Card>
    </Container>
  )
}

export default LoginSignInPage;