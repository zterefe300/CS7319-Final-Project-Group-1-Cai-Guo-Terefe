import React, { useState } from "react";
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  Container,
  Grid2,
  TextField,
  Typography,
} from "@mui/material";
import { replace, useNavigate } from "react-router-dom";

const SignUpPage = () => {
  const navigate = useNavigate();
  const [errors, setErrors] = useState({
    firstName: false,
    lastName: false,
    userName: false,
    password: false,
    confirmPassword: false,
  });

  const [inputValues, setInputValues] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    password: "",
    confirmPassword: "",
    adminCode: "",
  });

  const isValidInput = () => {
    let passwordErrorMessage;
    let errorField = {};
    const {
      firstName = "",
      lastName = "",
      userName = "",
      password = "",
      confirmPassword = "",
    } = inputValues;

    const isValidPasswordLength =
      (password.length > 7 && password.length < 32) ||
      (confirmPassword.length > 8 && confirmPassword.length < 32);
    const isPasswordMatch = password === confirmPassword;
    const isValidPassword = isValidPasswordLength && isPasswordMatch;

    if (!isPasswordMatch) {
      passwordErrorMessage = "Password do not match";
    } else if (!isValidPasswordLength) {
      passwordErrorMessage = "Password needs to be between 8 and 32 characters";
    }
    firstName
      ? (errorField = { ...errorField, firstName: false })
      : (errorField = { ...errorField, firstName: true });
    lastName
      ? (errorField = { ...errorField, lastName: false })
      : (errorField = { ...errorField, lastName: true });
    userName
      ? (errorField = { ...errorField, userName: false })
      : (errorField = { ...errorField, userName: true });
    isValidPassword
      ? (errorField = {
          ...errorField,
          password: false,
          confirmPassword: false,
        })
      : (errorField = {
          ...errorField,
          password: passwordErrorMessage,
          confirmPassword: true,
        });

    setErrors(errorField);
    return Boolean(firstName && lastName && userName && isValidPassword);
  };

  const handleInputChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    setInputValues((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleAccountCreation = () => {
    const isValid = isValidInput();
    if (isValid) {
      const payload = {
        firstName: inputValues.firstName,
        lastName: inputValues.lastName,
        userName: inputValues.userName,
        password: inputValues.password,
        confirmPassword: inputValues.confirmPassword,
        adminCode: inputValues.adminCode,
      };

      fetch("http://localhost:8080/inventory/unSelected/api/auth/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      })
        .then(() => navigate("/", { replace: true }))
        .catch(() => alert("Something went wrong"));
    }
  };

  const handleResetButton = () => {
    setInputValues({
      firstName: "",
      lastName: "",
      userName: "",
      password: "",
      confirmPassword: "",
      adminCode: "",
    });
    setErrors({
      firstName: false,
      lastName: false,
      userName: false,
      password: false,
      confirmPassword: false,
    });
  };

  return (
    <Container maxWidth="sm">
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardHeader
          title="Create An Account"
          sx={{
            textAlign: "center",
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
                error={errors.firstName}
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
                error={errors.lastName}
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
                error={errors.userName}
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
                inputProps={{ maxLength: 32 }}
                error={errors.password}
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
                inputProps={{ maxLength: 32 }}
                error={errors.confirmPassword}
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
                value={inputValues.adminCode}
                type="input"
                inputProps={{ maxLength: 6 }}
              />
            </Grid2>
            <Button
              variant="contained"
              onClick={handleAccountCreation}
              size="medium"
            >
              Create an account
            </Button>
            <Button
              variant="contained"
              onClick={handleResetButton}
              size="medium"
            >
              Reset
            </Button>
          </Grid2>
          {errors.password && (
            <Typography color="error">{errors.password}</Typography>
          )}
        </CardContent>
      </Card>
    </Container>
  );
};

export default SignUpPage;
