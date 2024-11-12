import React from "react";
import {
  Card,
  CardContent,
  CardMedia,
  Divider,
  Grid2,
  Typography,
} from "@mui/material";
import { Col, Row, Statistic } from "antd";

const itemDetails = [
  {
    id: 0,
    name: "Item A",
    detail: "Description of Item A",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender A",
  },
  {
    id: 0,
    name: "Item B",
    detail: "Description of Item B",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender B",
  },
  {
    id: 0,
    name: "Item C",
    detail: "Description of Item C",
    pics: "",
    alarmThreshold: 5,
    quantityThreshold: 10,
    venderId: "Vender C",
  },
];

function DashboardPage() {
  const renderItemDetailCard = itemDetails.map((item) => (
    <Grid2 size={4}>
      <Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardMedia component="img" height="140" image={item.pics} />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {item.name}
          </Typography>
          <Typography
            gutterBottom
            variant="body2"
            sx={{ color: "text.secondary" }}
          >
            {item.name}
          </Typography>
          <Divider />
          <Row gutter={16}>
            <Col span={12}>
              <Statistic
                title="Stock"
                value={93}
                suffix={`/ ${item.quantityThreshold}`}
              />
            </Col>
          </Row>
        </CardContent>
      </Card>
    </Grid2>
  ));

  return (
    <Grid2 container spacing={2}>
      {renderItemDetailCard}
    </Grid2>
  );
}

export default DashboardPage;
