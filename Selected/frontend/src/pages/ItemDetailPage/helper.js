export const itemsMapper = (item) => [
  {
    key: "1",
    label: "Item Name",
    children: item.name,
  },
  {
    key: "2",
    label: "Quantity",
    children: item.quantity,
  },
  {
    key: "3",
    label: "Item Description",
    children: item.description,
  },
  {
    key: "4",
    label: "Vendor Id",
    children: item.vendorId,
  },
  // {
  //   key: "5",
  //   label: "Name",
  //   children: "Vendor A",
  // },
  // {
  //   key: "6",
  //   label: "email",
  //   children: "vendor@email.com",
  // },
  // {
  //   key: "7",
  //   label: "phone",
  //   children: "+1 (555) 555-5555",
  // },
];