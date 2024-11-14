import React from "react";
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
import { Alert, Flex, Statistic } from "antd";

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
  const handleViewMoreButton = (id) => {
    //TODO: handle reroute to item detail page
    console.log(id);
  };

  const renderItemDetailCard = itemDetails.map((item) => {
    return (
      <Grid2 size={4} key={item.id}>
        <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
          <CardMedia component="img" height="140" image={item.pics} />
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
              onClick={() => handleViewMoreButton(item.id)}
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
    <Grid2 container spacing={2}>
      {renderItemDetailCard}
    </Grid2>
  );
}

export default DashboardPage;
