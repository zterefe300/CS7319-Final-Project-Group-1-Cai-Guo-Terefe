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


const SignUpPage = () => {
  const [ inputValues, setInputValues] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    email: "",
    password: "",
    confirmPassword: "",
    adminCode: "",
  })

  const handleInputChange = (e) => {
    const name = e.target.name
    const value = e.target.value
    setInputValues(prevState => ({
      ...prevState,
      [name]: value
    }))
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
                name="firstName"
                id="firstName"
                label="First Name"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.firstName}
                type="input"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="lastName"
                id="lastName"
                label="Last Name"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.lastName}
                type="input"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="userName"
                id="userName"
                label="User Name"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.userName}
                type="input"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="email"
                id="email"
                label="Email"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.email}
                type="email"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="password"
                id="password"
                label="Password"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.password}
                type="password"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="confirmPassword"
                id="confirmPassword"
                label="Confirm Password"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.confirmPassword}
                type="password"
              />
            </Grid2>
            <Grid2 size={12}>
              <TextField 
                name="adminCode"
                id="adminCode"
                label="Admin Code"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.adminCode}
                type="input"
                inputProps ={{ maxLength: 6 }}
              />
            </Grid2>
            <Button 
              variant="contained" 
              // onClick={handleLoginClick}
              size="medium"
            >
              Create an account
            </Button>
            <Button 
              variant="contained" 
              // onClick={handleAccountCreationClick} 
              size="medium"
            >
              Cancel
            </Button>
          </Grid2>
        </CardContent>
      </Card>
    </Container>
  )
}

export default SignUpPage;