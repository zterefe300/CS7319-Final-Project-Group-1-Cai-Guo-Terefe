import React, { useState, useEffect } from "react";
import { Button, Card, CardContent, Grid2 } from "@mui/material";
import { Descriptions } from "antd";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import EditableTextInput from "../../components/EditableTextInput";

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
  const { token = "" } = useSelector((state) => state.personDetail);
  const [data, setData] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/inventory/selected/api/item/${id}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        // "Authorization": token
      },
    })
      .then((resp) => resp.json())
      .then((resp) => {
        setData(resp);
      })
      .catch((err) => console.log(err));
  }, []);

  const renderInfoTable = () => {
    return (
      <Grid2 Grid2 container spacing={2}>
        {items.map((item) => (
          <Grid2 size={4}>
            <EditableTextInput />
          </Grid2>
        ))}
      </Grid2>
    );
  };

  return (
    <>
      <Card Card variant="outlined" sx={{ backgroundColor: "#fafafa" }}>
        <CardContent>
          <img src="https://www.colgateprofessional.com/content/dam/cp-sites/oral-care/professional2020/en-us/products/toothbrushes/colgate-360-toothbrush.png" />
          <Descriptions title="Item info" items={items} />
          <Button variant="contained" style={{ margin: "10px" }}>
            Update
          </Button>
          <Button variant="contained" style={{ margin: "10px" }}>
            Delete
          </Button>
        </CardContent>
      </Card>
    </>
  );
}

export default ItemDetailPage;
