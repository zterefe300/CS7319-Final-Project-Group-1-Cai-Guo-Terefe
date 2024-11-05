import React from "react";
import PropTypes from "prop-types";
import { TextField, Typography } from "@mui/material";

function DisplayField({ label, value }) {
  return (
    <div style={{ display: "block" }}>
      <Typography variant="overline" gutterBottom sx={{ display: "block" }}>
        {label}
      </Typography>
      <Typography variant="caption" gutterBottom sx={{ display: "block" }}>
        {value}
      </Typography>
    </div>
  );
}

function TextInput({ name, id, label, onChange }) {
  return (
    <TextField
      name={name}
      id={id}
      label={label}
      onChange={onChange}
      fullWidth
      variant="outlined"
      // value={inputValues.adminCode}
      type="input"
    />
  );
}

function EditableTextInput(props) {
  const { editMode, label, value, name, id, onChange } = props;
  console.log("editMode", editMode);
  return editMode ? (
    <TextInput name={name} id={id} label={label} onChange={onChange} />
  ) : (
    <DisplayField label={label} value={value} />
  );
}

EditableTextInput.prototype = {
  editMode: PropTypes.bool,
  label: PropTypes.string,
  value: PropTypes.string,
};

EditableTextInput.defaultProps = {
  editMode: false,
  label: "",
  value: "",
};

export default EditableTextInput;
