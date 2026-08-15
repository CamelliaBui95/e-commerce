import Header from "./components/Header";
import { Outlet } from "react-router";

const PageLayout = () => {
  return (
    <div>
      <Header />
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default PageLayout;
