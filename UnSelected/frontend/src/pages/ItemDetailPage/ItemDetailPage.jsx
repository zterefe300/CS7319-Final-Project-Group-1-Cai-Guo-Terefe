import React, { useState, useEffect } from "react";
import { Button, Card, CardContent, CardMedia, Typography } from "@mui/material";
import { Descriptions } from "antd";
import { useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";

import { itemsMapper } from "./helper";
import ModalWindow from "./ModalWindow";

function ItemDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { token = "" } = useSelector((state) => state.personDetail);

  const [data, setData] = useState(null);
  const [modalState, setModalState] = useState(false);
  const [photo, setPhoto] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch(`http://localhost:8080/inventory/unSelected/api/items`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
    })
      .then((resp) => resp.json())
      .then((resp) => {
        const getItemData = resp.filter(item => item.itemId === Number(id))[0]
        setData(itemsMapper(getItemData))
        setLoading(false)

        if(getItemData.pictureUrl.length > 0) {
          fetch(`http://localhost:8080/inventory/unSelected${getItemData.pictureUrl}`, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token}`,
            },
          })
            .then((resp) => resp.blob())
            .then(blob => {
              const imageObjectUrl = URL.createObjectURL(blob); 
              setPhoto(imageObjectUrl)
            })
            .catch((err) => console.log(err));
        }
      })
      .catch((err) => console.log(err));
  }, []);

  const handleModalPopup = () => {
    setModalState((prevState) => !prevState);
  };

  const handleDeleteButton = () => {
    fetch(`http://localhost:8080/inventory/unSelected/api/items/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
    })
      .then(() => navigate("/dashboard", { replace: true }))
      .catch((err) => console.log(err));
  };

  if(loading){
    return (
      <Typography variant="h3" gutterBottom>Loading...</Typography>
    )
  }

  return (
    <>
      <Card Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardMedia component="img" height="350" image={photo} src={photo} />
        <CardContent>
          <Descriptions title="Item info" items={data} />
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
