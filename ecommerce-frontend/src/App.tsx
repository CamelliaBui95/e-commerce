import { Route, Routes } from "react-router";
import "./App.css";
import HomePage from "./pages/home-page/HomePage";
import Products from "./pages/ProductsPage";
import Product from "./pages/ProductPage";
import PageLayout from "./pages/PageLayout";

function App() {
  return (
    <Routes>
      <Route element={<PageLayout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/products" element={<Products />} />
        <Route path="/products/:productId" element={<Product />} />
      </Route>
    </Routes>
  );
}

export default App;
