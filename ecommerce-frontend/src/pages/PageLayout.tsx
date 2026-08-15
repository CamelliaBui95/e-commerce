import React from "react";
import Header from "./components/Header";
import { Outlet } from "react-router";

const PageLayout = () => {
  return (
    <div className="relative">
      <Header />
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default PageLayout;
