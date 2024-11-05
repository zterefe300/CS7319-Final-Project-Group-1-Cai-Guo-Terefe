import React, { useState } from "react";
import {
  Card,
  CardContent,
  CardHeader,
  Container,
  Grid2,
  IconButton,
} from "@mui/material";
import ModeIcon from "@mui/icons-material/Mode";
import { useParams } from "react-router-dom";

import EditableTextInput from "../../components/EditableTextInput/EditableTextInput";
import "./ProfilePage.css";

function ProfilePage() {
  const { id } = useParams();
  const [editMode, setEditMode] = useState({
    firstName: false,
    lastName: false,
  });

  const handleEditButtonCLick = (name) => {
    setEditMode((prevState) => ({
      ...prevState,
      [name]: !prevState.name,
    }));
  };

  return (
    <Container maxWidth="sm">
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
              <div className="ProfilePage__div__flex">
                <EditableTextInput
                  editMode={editMode.firstName}
                  label="First Name"
                  value="firstName"
                  name="firstName"
                  id="firstName"
                  onChange={() => {}}
                />
                <IconButton
                  onClick={() => handleEditButtonCLick("firstName")}
                  sx={{ p: 0 }}
                  color="inherit"
                >
                  <ModeIcon />
                </IconButton>
              </div>
            </Grid2>
            <Grid2 size={12}>
              <div className="ProfilePage__div__flex">
                <EditableTextInput
                  editMode={editMode.lastName}
                  label="Last Name"
                  value="lastName"
                  name="lastName"
                  id="lastName"
                  onChange={() => {}}
                />
                <IconButton
                  onClick={() => handleEditButtonCLick("lastName")}
                  sx={{ p: 0 }}
                  color="inherit"
                >
                  <ModeIcon />
                </IconButton>
              </div>
            </Grid2>
          </Grid2>
        </CardContent>
      </Card>
    </Container>
  );
}

export default ProfilePage;
