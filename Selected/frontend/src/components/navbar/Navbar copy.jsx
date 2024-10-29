import React from "react";
import { NavLink, Outlet } from "react-router-dom";

const Navbar = () => {
  return (
    <>
      <nav className={`${Navbar.displayName}__nav`}>
        <ul>
          <li><h1>Navbar</h1></li>
        </ul>
        <ul>
          <li><NavLink to="/">Login</NavLink></li>
          <li><NavLink to="/signup">Sign Up</NavLink></li>
        </ul>
      </nav>
      <Outlet />
    </>
  )
}

Navbar.displayName = "navbar"

export default Navbar;