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
    title: "Vendor Id",
    dataIndex: "vendorId",
    key: "vendorId",
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
    vendorId: 12,
    status: "Reordered",
  },
  {
    key: "2",
    itemName: "Item B",
    vendorId: 12,
    status: "Reordered",
  },
  {
    key: "3",
    itemName: "Item C",
    vendorId: 12,
    status: "Reordered",
  },
  {
    key: "4",
    itemName: "Item D",
    vendorId: 12,
    status: "Low",
  },
  {
    key: "5",
    itemName: "Item E",
    vendorId: 12,
    status: "Completed",
  },
];
function ItemTrackingPage() {
  return <Table columns={columns} dataSource={data} />;
}

export default ItemTrackingPage;
