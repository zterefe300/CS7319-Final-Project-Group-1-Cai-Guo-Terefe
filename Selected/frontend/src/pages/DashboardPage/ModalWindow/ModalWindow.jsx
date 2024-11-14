import React from "react";
import { Flex, InputNumber, Modal, Typography } from "antd";
import PropTypes from "prop-types";
import { Button, Grid2, TextField } from "@mui/material";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import { styled } from "@mui/material/styles";

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
  return (
    <Modal
      title="Add new item"
      open={modalState}
      onOk={handleModalPopup}
      onCancel={handleModalPopup}
      okText="Add"
      cancelText="Cancel"
    >
      <Grid2 container spacing={2}>
        <Grid2 size={6}>
          <TextField
            name="itemName"
            id="itemName"
            label="Item Name"
            // onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            // value={inputValues.userName}
            type="input"
          />
        </Grid2>
        <Grid2 size={6}>
          <TextField
            name="vendorId"
            id="venderId"
            label="Vendor Id"
            // onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            // value={inputValues.password}
            type="input"
          />
        </Grid2>
        <Grid2 size={12}>
          <TextField
            name="itemDescription"
            id="itemDescription"
            label="Item Description"
            // onChange={handleInputChange}
            fullWidth
            variant="outlined"
            required
            // value={inputValues.password}
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
              min={1}
              max={10}
              defaultValue={3}
              style={{ marginLeft: "3px" }}
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
              min={1}
              max={10}
              defaultValue={3}
              style={{ marginLeft: "3px" }}
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
              min={1}
              max={10}
              defaultValue={3}
              style={{ marginLeft: "3px" }}
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
              onChange={(event) => console.log(event.target.files)}
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
