import React, { useState } from "react";
import { Flex, InputNumber, Modal, Typography } from "antd";
import PropTypes from "prop-types";
import { Button, Grid2, TextField } from "@mui/material";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import { styled } from "@mui/material/styles";
import { useSelector } from "react-redux";

const VisuallyHiddenInput = styled("input")({
  clip: "rect(0 0 0 0)",
  clipPath: "inset(50%)",
  height: 1,
  overflow: "hidden",
  position: "absolute",
  bottom: 0,
  left: 0,
  whiteSpace: "nowrap",
  width: 1,
});

function ModalWindow({ modalState, handleModalPopup }) {
  const { token = "" } = useSelector((state) => state.personDetail);

  const [inputValues, setInputValues] = useState({
    itemName: "",
    vendorId: null,
    itemDescription: "",
    itemQuantity: 0,
    quantityThreshold: 0,
    alarmThreshold: 0,
    picture: null,
  });

  const handleInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;

    if (name === "vendorId") value = Number(e.target.value) ? Number(e.target.value) : null;
    setInputValues((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleInputNumberChange = (value, name) => {
    setInputValues((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleCancelModal = () => {
    handleModalPopup();
    setInputValues({
      itemName: "",
      vendorId: null,
      itemDescription: "",
      itemQuantity: 0,
      quantityThreshold: 0,
      alarmThreshold: 0,
      picture: null,
    });
  };

  const handleCreateButton = () => {
    const formData = new FormData();
    formData.append("itemName", inputValues.itemName)
    formData.append("vendorId", Number(inputValues.vendorId))
    formData.append("itemDescription", inputValues.itemDescription)
    formData.append("itemQuantity", inputValues.itemQuantity)
    formData.append("quantityThreshold", inputValues.quantityThreshold)
    formData.append("alarmThreshold", inputValues.alarmThreshold)
    formData.append("picture", inputValues.picture)
    // const payload = {
    //   itemName: inputValues.itemName,
    //   vendorId: Number(inputValues.vendorId),
    //   itemDescription: inputValues.itemDescription,
    //   itemQuantity: inputValues.itemQuantity,
    //   quantityThreshold: inputValues.quantityThreshold,
    //   alarmThreshold: inputValues.alarmThreshold,
    //   picture: inputValues.picture,
    // };
    
    fetch("http://localhost:8080/inventory/selected/api/items", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": token,
      },
      body: formData,
    })
      .then(() => {
        handleModalPopup();
      })
      .catch((err) => console.log(err));
  };

  return (
    <Modal
      title="Add new item"
      open={modalState}
      onOk={() => {
        handleCreateButton()
        handleModalPopup()
      }}
      onCancel={handleCancelModal}
      okText="Add"
      cancelText="Cancel"
    >
      <Grid2 container spacing={2}>
        <Grid2 size={6}>
          <TextField
            name="itemName"
            id="itemName"
            label="Item Name"
            onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            value={inputValues.itemName}
            type="input"
          />
        </Grid2>
        <Grid2 size={6}>
          <TextField
            name="vendorId"
            id="venderId"
            label="Vendor Id"
            onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            value={inputValues.vendorId}
            type="input"
          />
        </Grid2>
        <Grid2 size={12}>
          <TextField
            name="itemDescription"
            id="itemDescription"
            label="Item Description"
            onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            value={inputValues.itemDescription}
            type="input"
          />
        </Grid2>
        <Grid2 size={4}>
          <Flex>
            <Typography
              variant="overline"
              gutterBottom
              sx={{ display: "block" }}
            >
              Item Quantity
            </Typography>
            <InputNumber
              style={{ marginLeft: "3px" }}
              onChange={(e) => handleInputNumberChange(e, "itemQuantity")}
              value={inputValues.itemQuantity}
            />
          </Flex>
        </Grid2>
        <Grid2 size={4}>
          <Flex>
            <Typography
              variant="overline"
              gutterBottom
              sx={{ display: "block" }}
            >
              Quantity Threshold
            </Typography>
            <InputNumber
              style={{ marginLeft: "3px" }}
              onChange={(e) => handleInputNumberChange(e, "quantityThreshold")}
              value={inputValues.quantityThreshold}
            />
          </Flex>
        </Grid2>
        <Grid2 size={4}>
          <Flex>
            <Typography
              variant="overline"
              gutterBottom
              sx={{ display: "block" }}
            >
              Alarm Threshold
            </Typography>
            <InputNumber
              style={{ marginLeft: "3px" }}
              onChange={(e) => handleInputNumberChange(e, "alarmThreshold")}
              value={inputValues.alarmThreshold}
            />
          </Flex>
        </Grid2>
        <Grid2 size={12}>
          <Button
            component="label"
            role={undefined}
            variant="contained"
            tabIndex={-1}
            startIcon={<CloudUploadIcon />}
          >
            Upload files
            <VisuallyHiddenInput
              type="file"
              onChange={(event) =>
                setInputValues((prevState) => ({
                  ...prevState,
                  picture: event.target.files,
                }))
              }
              multiple
            />
          </Button>
        </Grid2>
      </Grid2>
    </Modal>
  );
}

ModalWindow.prototype = {
  modalState: PropTypes.bool,
  handleModalPopup: PropTypes.func,
};

ModalWindow.defaultProps = {
  modalState: false,
  handleModalPopup: () => {},
};

export default ModalWindow;
