import { Route, Routes } from "react-router";
import "./App.css";
import HomePage from "./pages/HomePage";
import Products from "./pages/ProductsPage";
import Product from "./pages/ProductPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/products" element={<Products />} />
      <Route path="/products/:productId" element={<Product />} />
    </Routes>
  );
}

export default App;
