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

  const fetchTrackingOrder = () => {
    fetch(
      "http://localhost:8080/inventory/unSelected/api/items/reorderTrackerData",
      {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      }
    )
      .then((resp) => resp.json())
      .then((resp) => {
        const filterData = resp.filter(item => item.reorderStatus === "REORDERED")
        setLoading(false);
        setData(filterData);
      })
      .catch((err) => console.log(err));
  }

  useEffect(() => {
   fetchTrackingOrder()
  }, []);

  const handleUpdateButton = (itemId) => {
    const result = data.filter(item => item.itemId === itemId)[0]
    
    const payload = {
      itemId: result.itemId,
      reorderStatus: result.reorderStatus,
      effectiveDate: new Date(result.effectiveDate)
    }

    fetch(`http://localhost:8080/inventory/unSelected/api/fulfillItemReorder/${itemId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
      body: JSON.stringify(payload),
    })
      .then(() => fetchTrackingOrder())
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
      render: (reorderStatus) =>{
        const wordSplitArray = reorderStatus.toLowerCase().split("")
        const firstLetter = wordSplitArray[0].toUpperCase();
        const restOfWord = wordSplitArray.slice(1, wordSplitArray.length)
        return (
        <>{[...firstLetter, ...restOfWord]}</>
      )},
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
