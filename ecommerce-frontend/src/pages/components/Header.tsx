import React from "react";
import { Link } from "react-router";

const Header = () => {
  return (
    <div className="header p-4 flex flex-row w-full">
      <div className="w-1/4">Ecommerce</div>
      <div className="nav-bar w-3/4 flex flex-row justify-end-safe gap-8">
        <Link to={"/"}>Home</Link>
        <Link to={""}>About</Link>
        <Link to={"/products"}>Products</Link>
      </div>
    </div>
  );
};

export default Header;
