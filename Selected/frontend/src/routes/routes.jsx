import { createBrowserRouter } from "react-router-dom";

import Navbar from "../components/navbar"
import LoginSignInPage from "../pages/LoginSignInPage/LoginSignInPage";
import SignUpPage from "../pages/SignUpPage/SignUpPage";

const routes = createBrowserRouter([
  {
    path: "/",
    element: <Navbar />,
    children: [
      {
        path: "/",
        errorElement: <>404 Page not found</>,
        element: <LoginSignInPage />
      },
      {
        path: "/signup",
        element: <SignUpPage />
      },
    ]
  }
])

export default routes; 