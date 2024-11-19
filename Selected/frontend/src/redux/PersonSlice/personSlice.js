import { createSlice } from "@reduxjs/toolkit";

export const personSlice = createSlice({
  name: "Person Details",
  initialState: {
    userName: "",
    token: "",
  },
  reducers: {
    setPersonDetail: (state, actions) => {
      state.userName = actions.payload.userName;
      state.token = actions.payload.token;
    },
    resetPersonDetail: (state) => {
      state.userName = "";
      state.token = "";
    }
  },
});

export const { setPersonDetail, resetPersonDetail } = personSlice.actions;

export default personSlice.reducer;
