import React, { useState } from "react";
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  Container,
  Grid2,
  TextField,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setPersonDetail } from "../../redux/PersonSlice/personSlice";

const LoginSignInPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [inputValues, setInputValues] = useState({
    userName: "",
    password: "",
  });

  const handleInputChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    setInputValues((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleLoginClick = () => {
    const payload = {
      userName: inputValues.userName,
      password: inputValues.password,
    };

    fetch("http://localhost:8080/inventory/unSelected/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then((resp) => resp.json())
      .then((resp) => {
        dispatch(setPersonDetail(resp));
        navigate("/dashboard", { replace: true });
      })
      .catch((err) => console.log(err));
  };

  const handleAccountCreationClick = () => {
    navigate("/signup", { replace: true });
  };

  return (
    <Container maxWidth="sm">
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardHeader
          title="Login"
          sx={{
            textAlign: "center",
          }}
        />
        <CardContent>
          <Grid2 container spacing={2}>
            <Grid2 size={12}>
              <TextField
                name="userName"
                id="userName"
                label="userName"
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
                name="password"
                id="password"
                label="password"
                onChange={handleInputChange}
                fullWidth
                variant="outlined"
                required
                value={inputValues.password}
                type="password"
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
  );
};

export default LoginSignInPage;
