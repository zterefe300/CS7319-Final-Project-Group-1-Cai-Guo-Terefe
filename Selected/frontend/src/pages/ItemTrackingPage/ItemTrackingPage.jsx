import React, { useEffect, useState } from "react";
import { Button, Typography } from "@mui/material";
import { Table } from "antd";
import { useSelector } from "react-redux";

// const data = [
//   {
//     key: "1",
//     itemName: "Item A",
//     vendorId: 12,
//     status: "Reordered",
//   },
//   {
//     key: "2",
//     itemName: "Item B",
//     vendorId: 12,
//     status: "Reordered",
//   },
//   {
//     key: "3",
//     itemName: "Item C",
//     vendorId: 12,
//     status: "Reordered",
//   },
//   {
//     key: "4",
//     itemName: "Item D",
//     vendorId: 12,
//     status: "Low",
//   },
//   {
//     key: "5",
//     itemName: "Item E",
//     vendorId: 12,
//     status: "Completed",
//   },
// ];

function ItemTrackingPage() {
  const { token = "" } = useSelector((state) => state.personDetail);

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(
      "http://localhost:8080/inventory/selected/api/items/reorderTrackerData",
      {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      }
    )
      .then((resp) => resp.json())
      .then((resp) => {
        setLoading(false);
        setData(resp);
      })
      .catch((err) => console.log(err));
  }, []);

  const handleUpdateButton = (itemId) => {
    const payload = {
      itemId: itemId,
      reorderStatus: "Fulfilled",
    };
    
    fetch("http://localhost:8080/inventory/selected/api/items", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
      body: JSON.stringify(payload),
    })
      .then(() => {})
      .catch((err) => console.log(err));
  };

  const columns = [
    {
      title: "Item Name",
      dataIndex: "itemName",
      key: "itemName",
    },
    {
      title: "Vendor Id",
      dataIndex: "vendorId",
      key: "vendorId",
    },
    {
      title: "Status",
      dataIndex: "reorderStatus",
      key: "reorderStatus",
    },
    {
      title: "Update",
      render: ({ itemId }) => (
        <Button onClick={() => handleUpdateButton(itemId)} varient="Contained">
          Fulfilled
        </Button>
      ),
    },
  ];

  if (loading) {
    return (
      <Typography variant="h3" gutterBottom>
        Loading...
      </Typography>
    );
  }

  return <Table columns={columns} dataSource={data} />;
}

export default ItemTrackingPage;
