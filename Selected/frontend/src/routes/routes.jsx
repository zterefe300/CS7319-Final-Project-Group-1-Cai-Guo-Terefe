import { createBrowserRouter } from "react-router-dom";
import { Box } from "@mui/material";

import Navbar from "../components/navbar";
import LoginSignInPage from "../pages/LoginSignInPage";
import SignUpPage from "../pages/SignUpPage/SignUpPage";
import ProfilePage from "../pages/ProfilePage";
import DashboardPage from "../pages/DashboardPage";
import ItemDetailPage from "../pages/ItemDetailPage";
import ItemTracking from "../pages/ItemTrackingPage";

const routes = createBrowserRouter([
  {
    path: "/",
    element: <Navbar />,
    children: [
      {
        path: "/",
        errorElement: <>404 Page not found</>,
        element: (
          <>
            <Box
              sx={{
                mt: 10,
              }}
            />
            <LoginSignInPage />
          </>
        ),
      },
      {
        path: "/signup",
        element: (
          <>
            <Box
              sx={{
                mt: 10,
              }}
            />
            <SignUpPage />
          </>
        ),
      },
      {
        path: "/profile/:id",
        element: (
          <>
            <Box
              sx={{
                mt: 10,
              }}
            />
            <ProfilePage />
          </>
        ),
      },
      {
        path: "/dashboard",
        element: (
          <>
            <DashboardPage />
          </>
        ),
      },
      {
        path: "/item/:id",
        element: (
          <>
            <Box
              sx={{
                mt: 10,
              }}
            />
            <ItemDetailPage />
          </>
        ),
      },
      {
        path: "/track",
        element: (
          <>
            <Box
              sx={{
                mt: 10,
              }}
            />
            <ItemTracking />
          </>
        ),
      },
    ],
  },
]);

export default routes;
