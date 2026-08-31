import { Route, Routes } from "react-router";
import "./App.css";
import HomePage from "./pages/home-page/HomePage";
import Products from "./pages/ProductsPage";
import Product from "./pages/ProductPage";
import PageLayout from "./pages/PageLayout";
import CheckoutPage from "./pages/checkout-page/CheckoutPage";
import CheckoutReturnPage from "./pages/checkout-page/CheckoutReturnPage";

function App() {
  return (
    <Routes>
      <Route element={<PageLayout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/products" element={<Products />} />
        <Route path="/products/:productId" element={<Product />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/checkout/return" element={<CheckoutReturnPage />} />
      </Route>
    </Routes>
  );
}

export default App;
