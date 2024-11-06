import React from "react";
import PropTypes from "prop-types";
import { TextField, Typography } from "@mui/material";

function DisplayField({ label, value }) {
  return (
    <div style={{ display: "block" }}>
      <Typography variant="h6" gutterBottom sx={{ display: "block" }}>
        {label}
      </Typography>
      <Typography variant="body1" gutterBottom sx={{ display: "block" }}>
        {value}
      </Typography>
    </div>
  );
}

function TextInput({
  name,
  id,
  label,
  onChange,
  value,
  type = "input",
  error,
}) {
  return (
    <TextField
      name={name}
      id={id}
      label={label}
      onChange={onChange}
      fullWidth
      variant="outlined"
      value={value}
      type={type}
      error={error}
    />
  );
}

function EditableTextInput(props) {
  const { editMode, label, value, name, id, onChange, type, error } = props;
  return editMode ? (
    <TextInput
      name={name}
      id={id}
      label={label}
      onChange={onChange}
      value={value}
      type={type}
      error={error}
    />
  ) : (
    <DisplayField label={label} value={value} />
  );
}

EditableTextInput.prototype = {
  editMode: PropTypes.bool,
  label: PropTypes.string,
  value: PropTypes.string,
  name: PropTypes.string,
  id: PropTypes.string,
  onChange: PropTypes.func,
  type: PropTypes.string,
  error: PropTypes.bool,
};

EditableTextInput.defaultProps = {
  editMode: false,
  label: "",
  value: "",
  name: "",
  id: "",
  onChange: () => {},
  type: "",
  error: false,
};

export default EditableTextInput;
