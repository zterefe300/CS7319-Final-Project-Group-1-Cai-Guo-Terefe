import React from "react";
import { Button, Card, CardContent } from "@mui/material";
import { Descriptions, Flex } from "antd";

function ItemDetailPage() {
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
  ];

  return (
    <>
      <Card Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardContent>
          <img src="https://www.colgateprofessional.com/content/dam/cp-sites/oral-care/professional2020/en-us/products/toothbrushes/colgate-360-toothbrush.png" />
          <Flex justify="space-between">
            <Flex>
              <Descriptions title="Item info" items={items} />
            </Flex>
            <Flex justify="space-between">
              <Button variant="contained">Update</Button>
              <Button variant="contained">Delete</Button>
            </Flex>
          </Flex>
        </CardContent>
      </Card>
    </>
  );
}

export default ItemDetailPage;
