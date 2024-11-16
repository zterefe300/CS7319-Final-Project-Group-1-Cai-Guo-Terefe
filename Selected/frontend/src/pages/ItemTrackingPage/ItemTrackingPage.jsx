import { Button } from "@mui/material";
import { Table } from "antd";
import React from "react";

const columns = [
  {
    title: "Item Name",
    dataIndex: "itemName",
    key: "itemName",
  },
  {
    title: "Status",
    dataIndex: "status",
    key: "status",
  },
  {
    title: "Update",
    render: () => <Button varient="Contained">Update</Button>,
  },
];

const data = [
  {
    key: "1",
    itemName: "Item A",
    status: "Reordered",
  },
  {
    key: "2",
    itemName: "Item B",
    status: "Reordered",
  },
  {
    key: "3",
    itemName: "Item C",
    status: "Reordered",
  },
  {
    key: "4",
    itemName: "Item D",
    status: "Low",
  },
  {
    key: "5",
    itemName: "Item E",
    status: "Completed",
  },
];
function ItemTrackingPage() {
  return <Table columns={columns} dataSource={data} />;
}

export default ItemTrackingPage;
