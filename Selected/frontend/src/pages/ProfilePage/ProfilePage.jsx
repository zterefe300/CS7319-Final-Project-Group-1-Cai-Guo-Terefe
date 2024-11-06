import React, { useEffect, useState } from "react";
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  Container,
  Grid2,
  Typography,
} from "@mui/material";
import { useParams } from "react-router-dom";

import EditableTextInput from "../../components/EditableTextInput/EditableTextInput";
import mockData from "./mockData";
import "./ProfilePage.css";

function ProfilePage() {
  const { id } = useParams();
  const [editMode, setEditMode] = useState(false);
  const [editInputs, setEditInputs] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState({
    firstName: false,
    lastName: false,
    userName: false,
    email: false,
    password: false,
    confirmPassword: false,
  });

  useEffect(() => {
    setEditInputs((prevState) => ({
      ...prevState,
      ...mockData,
    }));
  }, []);

  const isValidInput = () => {
    let errorField = {};
    const {
      firstName = "",
      lastName = "",
      userName = "",
      email = "",
    } = editInputs;

    const isValidEmail = email.match(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g);

    firstName
      ? (errorField = { ...errorField, firstName: false })
      : (errorField = { ...errorField, firstName: true });
    lastName
      ? (errorField = { ...errorField, lastName: false })
      : (errorField = { ...errorField, lastName: true });
    userName
      ? (errorField = { ...errorField, userName: false })
      : (errorField = { ...errorField, userName: true });
    isValidEmail
      ? (errorField = { ...errorField, email: false })
      : (errorField = { ...errorField, email: "Enter a correct email" });

    setErrors(errorField);
    return Boolean(firstName && lastName && userName && isValidEmail);
  };

  const handleEditButtonCLick = (name) => {
    setEditMode((prevState) => !prevState);
  };

  const handleCancelButtonClick = () => {
    // TODO: Reset the data to original api call data
    setEditInputs({
      ...mockData,
      password: "",
      confirmPassword: "",
    });
    setEditMode((prevState) => !prevState);
  };

  const handleSaveButtonClick = () => {
    // TODO: Connect the save button to API calls to save the data, and the fetch the new data.
    const isValid = isValidInput();
    if (!isValid) {
      alert("Something wrong");
    } else {
      setEditMode((prevState) => !prevState);
    }
  };

  const handleInputChanges = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setEditInputs((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <Container maxWidth="md">
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardHeader
          title="Profile Page"
          sx={{
            textAlign: "center",
          }}
        />
        <CardContent>
          <Grid2 container spacing={2}>
            <Grid2 size={12}>
              <EditableTextInput
                editMode={editMode}
                label="First Name"
                value={editInputs.firstName}
                name="firstName"
                id="firstName"
                onChange={handleInputChanges}
                error={errors.firstName}
              />
            </Grid2>
            <Grid2 size={12}>
              <EditableTextInput
                editMode={editMode}
                label="Last Name"
                value={editInputs.lastName}
                name="lastName"
                id="lastName"
                onChange={handleInputChanges}
                error={errors.lastName}
              />
            </Grid2>
            <Grid2 size={12}>
              <EditableTextInput
                editMode={editMode}
                label="User Name"
                value={editInputs.userName}
                name="userName"
                id="userName"
                onChange={handleInputChanges}
                error={errors.userName}
              />
            </Grid2>
            <Grid2 size={12}>
              <EditableTextInput
                editMode={editMode}
                label="Email"
                value={editInputs.email}
                name="email"
                id="email"
                onChange={handleInputChanges}
                error={errors.email}
              />
            </Grid2>
            <Grid2 size={12}>
              <EditableTextInput
                editMode={editMode}
                label="Password"
                value={editInputs.password}
                name="password"
                id="password"
                onChange={handleInputChanges}
                type="password"
              />
            </Grid2>
            {editMode && (
              <Grid2 size={12}>
                <EditableTextInput
                  editMode={editMode}
                  label="Confirm Password"
                  value={editInputs.confirmPassword}
                  name="confirmPassword"
                  id="confirmPassword"
                  onChange={handleInputChanges}
                  type="password"
                />
              </Grid2>
            )}
            {errors.email && (
              <Grid2 size={12}>
                <Typography color="error">{errors.email}</Typography>
              </Grid2>
            )}
            <Grid2 size={12}>
              <div className="ProfilePage__div__flex">
                {!editMode && (
                  <Button variant="contained" onClick={handleEditButtonCLick}>
                    Edit
                  </Button>
                )}
                {editMode && (
                  <Button variant="contained" onClick={handleSaveButtonClick}>
                    Save Changes
                  </Button>
                )}
                {editMode && (
                  <Button variant="text" onClick={handleCancelButtonClick}>
                    Cancel
                  </Button>
                )}
              </div>
            </Grid2>
          </Grid2>
        </CardContent>
      </Card>
    </Container>
  );
}

export default ProfilePage;
