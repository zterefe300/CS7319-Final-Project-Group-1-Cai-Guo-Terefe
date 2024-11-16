import { createBrowserRouter } from "react-router-dom";

import Navbar from "../components/navbar";
import LoginSignInPage from "../pages/LoginSignInPage/LoginSignInPage";
import SignUpPage from "../pages/SignUpPage/SignUpPage";
import ProfilePage from "../pages/ProfilePage";
import DashboardPage from "../pages/DashboardPage";
import ItemDetailPage from "../pages/ItemDetailPage";

const routes = createBrowserRouter([
  {
    path: "/",
    element: <Navbar />,
    children: [
      {
        path: "/",
        errorElement: <>404 Page not found</>,
        element: <LoginSignInPage />,
      },
      {
        path: "/signup",
        element: <SignUpPage />,
      },
      {
        path: "/profile/:id",
        element: <ProfilePage />,
      },
      {
        path: "/dashboard",
        element: <DashboardPage />,
      },
      {
        path: "/item/:id",
        element: <ItemDetailPage />,
      },
    ],
  },
]);

export default routes;
