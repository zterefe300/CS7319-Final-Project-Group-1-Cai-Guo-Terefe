import React, { useState, useEffect } from "react";
import { Button, Card, CardContent } from "@mui/material";
import { Descriptions } from "antd";
import { useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";
import ModalWindow from "./ModalWindow";

const items = [
  {
    key: "1",
    label: "Item Name",
    children: "ToothBrush",
  },
  {
    key: "2",
    label: "Quantity",
    children: "5",
  },
  {
    key: "3",
    label: "Item Description",
    children: "Item Description",
  },
  {
    key: "4",
    label: "Vendor Id",
    children: "10",
  },
  {
    key: "5",
    label: "Name",
    children: "Vendor A",
  },
  {
    key: "6",
    label: "email",
    children: "vendor@email.com",
  },
  {
    key: "7",
    label: "phone",
    children: "+1 (555) 555-5555",
  },
];

function ItemDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { token = "" } = useSelector((state) => state.personDetail);

  const [data, setData] = useState(null);
  const [modalState, setModalState] = useState(false);

  useEffect(() => {
    fetch(`http://localhost:8080/inventory/selected/api/items`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
    })
      .then((resp) => resp.json())
      .then((resp) => {
        setData(() => resp.filter(item => item.itemId === id));
      })
      .catch((err) => console.log(err));
  }, []);

  const handleModalPopup = () => {
    setModalState((prevState) => !prevState);
  };

  const handleDeleteButton = () => {
    console.log("clicked")
    fetch(`http://localhost:8080/inventory/selected/api/items/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
    })
      .then(() => navigate("/dashboard", { replace: true }))
      .catch((err) => console.log(err));
  };

  return (
    <>
      <Card Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardContent>
          <img src="https://www.colgateprofessional.com/content/dam/cp-sites/oral-care/professional2020/en-us/products/toothbrushes/colgate-360-toothbrush.png" />
          <Descriptions title="Item info" items={items} />
          <Button
            variant="contained"
            onClick={handleModalPopup}
            style={{ margin: "10px" }}
          >
            Update
          </Button>
          <Button
            variant="contained"
            onClick={handleDeleteButton}
            style={{ margin: "10px" }}
          >
            Delete
          </Button>
        </CardContent>
      </Card>
      <ModalWindow
        modalState={modalState}
        handleModalPopup={handleModalPopup}
      />
    </>
  );
}

export default ItemDetailPage;
