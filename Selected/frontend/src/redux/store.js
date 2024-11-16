import { configureStore } from "@reduxjs/toolkit";
import personReducer from "./PersonSlice/personSlice";

export default configureStore({
  reducer: {
    personDetail: personReducer,
  },
});
