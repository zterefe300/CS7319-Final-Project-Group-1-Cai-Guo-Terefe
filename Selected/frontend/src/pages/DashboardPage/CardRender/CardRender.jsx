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
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";


function CardRender({ data }) {
  const { token = "" } = useSelector((state) => state.personDetail);
  const navigate = useNavigate()
  const [photo, setPhoto] = useState("");

  const handleViewMoreButton = (id) => {
    //TODO: handle reroute to item detail page
    navigate(`/item/${id}`, { replace: true });
  };

  useEffect(() => {
    if(data.pictureUrl.length > 0) {
      fetch(`http://localhost:8080/inventory/selected${data.pictureUrl}`, {
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
  }, [])

  return (
    <Grid2 size={4}>
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardMedia component="img" height="200" image={photo} src={photo} />
        <CardContent>
          <Flex justify="space-between">
            <Typography gutterBottom variant="h5" component="div">
              {data.name}
            </Typography>
            {data.quantity < data.quantityThreshold && (
              <Alert type="error" message="Low stock" />
            )}
          </Flex>
          <Typography
            gutterBottom
            variant="body2"
            sx={{ color: "text.secondary" }}
          >
            {data.name}
          </Typography>
          <Divider />
          <Box
            sx={{
              mt: 3,
            }}
          />
          <Button
            variant="contained"
            onClick={() => handleViewMoreButton(data.itemId)}
            size="medium"
          >
            View More
          </Button>
        </CardContent>
      </Card>
    </Grid2>
  );
}

CardRender.prototype = {
  data: PropTypes.shape({})
}

CardRender.defaultProps = {
  data: {}
}

export default CardRender;