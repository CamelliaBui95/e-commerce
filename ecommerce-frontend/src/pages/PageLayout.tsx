import Footer from "./components/Footer";
import Header from "./components/Header";
import { Outlet } from "react-router";

const PageLayout = () => {
  return (
    <div>
      <Header />
      <main className="min-h-screen pb-5">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export default PageLayout;
