import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardMedia,
  Divider,
  Grid2,
  Typography,
} from "@mui/material";
import { Alert, Flex } from "antd";
import { useSelector } from "react-redux";

import ModalWindow from "./ModalWindow";
import { useNavigate } from "react-router-dom";

const itemDetails = [
  {
    id: 0,
    name: "Item A",
    detail: "Description of Item A",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender A",
    quantity: 10,
  },
  {
    id: 1,
    name: "Item B",
    detail: "Description of Item B",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender B",
    quantity: 10,
  },
  {
    id: 2,
    name: "Item C",
    detail: "Description of Item C",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender C",
    quantity: 5,
  },
];

function DashboardPage() {
  const { token = "" } = useSelector((state) => state.personDetail);
  const navigate = useNavigate();
  const [modalState, setModalState] = useState(false);
  const [data, setData] = useState(null);
  const [triggerFetch, setTriggerFetch] = useState(true);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (triggerFetch) {
      setTriggerFetch(false);
      fetch("http://localhost:8080/inventory/selected/api/items", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((resp) => resp.json())
        .then((resp) => {
          setData(resp);
          setLoading(false);
        })
        .catch((err) => console.log(err));
    }
  }, [triggerFetch]);

  const handleViewMoreButton = (id) => {
    //TODO: handle reroute to item detail page
    navigate(`/item/${id}`, { replace: true });
  };

  const handleModalPopup = () => {
    setModalState((prevState) => !prevState);
  };

  const renderItemDetailCard = data?.map((item) => {
    return (
      <Grid2 size={4} key={item.id}>
        <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
          <CardMedia component="img" height="140" image={item.pictureUrl} />
          <CardContent>
            <Flex justify="space-between">
              <Typography gutterBottom variant="h5" component="div">
                {item.name}
              </Typography>
              {item.quantity < item.quantityThreshold && (
                <Alert type="error" message="Low stock" />
              )}
            </Flex>
            <Typography
              gutterBottom
              variant="body2"
              sx={{ color: "text.secondary" }}
            >
              {item.name}
            </Typography>
            <Divider />
            <Box
              sx={{
                mt: 3,
              }}
            />
            <Button
              variant="contained"
              onClick={() => handleViewMoreButton(item.itemId)}
              size="medium"
            >
              View More
            </Button>
          </CardContent>
        </Card>
      </Grid2>
    );
  });

  return (
    <>
      <Flex justify="flex-end" align="center">
        <Button
          variant="contained"
          style={{ margin: "10px" }}
          onClick={handleModalPopup}
        >
          Add Item
        </Button>
      </Flex>
      <Grid2 container spacing={2}>
        {loading ? (
          <Typography variant="h3" gutterBottom>
            Loading...
          </Typography>
        ) : (
          renderItemDetailCard
        )}
      </Grid2>
      <ModalWindow
        setTriggerFetch={setTriggerFetch}
        modalState={modalState}
        handleModalPopup={handleModalPopup}
      />
    </>
  );
}

export default DashboardPage;
