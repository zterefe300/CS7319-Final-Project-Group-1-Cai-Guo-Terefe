import React, { useEffect, useState } from "react";
import {
  Button,
  Grid2,
  Typography,
} from "@mui/material";
import { Alert, Flex } from "antd";
import { useSelector } from "react-redux";

import ModalWindow from "./ModalWindow";
import CardRender from "./CardRender";

function DashboardPage() {
  const { token = "" } = useSelector((state) => state.personDetail);
  const [modalState, setModalState] = useState(false);
  const [data, setData] = useState(null);
  const [triggerFetch, setTriggerFetch] = useState(true);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (triggerFetch) {
      setTriggerFetch(false);
      fetch("http://localhost:8080/inventory/ununSelected/api/items", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
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

  const handleModalPopup = () => {
    setModalState((prevState) => !prevState);
  };

  const renderItemDetailCard = data?.map((item) => {
    return <CardRender data={item} key={item.itemId} />
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
