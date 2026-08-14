import React from "react";
import Header from "./components/Header";
import { Outlet } from "react-router";

const PageLayout = () => {
  return (
    <React.Fragment>
      <Header />
      <main>
        <Outlet />
      </main>
    </React.Fragment>
  );
};

export default PageLayout;
