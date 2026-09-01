import { SortDirection } from "@/enums/sortDirection";
import { addToCart } from "@/features/cart/cartSlice";
import ProductCard from "@/features/product/ProductCard";
import { useSearchProducts } from "@/hooks/useProducts";
import type { OrderItem } from "@/models/order";
import type { Product } from "@/models/product";
import React from "react";
import { useDispatch } from "react-redux";

const Products = () => {
  const { data } = useSearchProducts({
    pageNumber: 0,
    pageSize: 10,
    direction: SortDirection.DESC,
  });

  const dispatch = useDispatch();

  const handleAddToCart = (product: Product) => {
    const orderItem: OrderItem = {
      product_id: product.id,
      product_name: product.name,
      image_name: product.image_name,
      quantity: 1,
      unit_price: product.price,
    };

    dispatch(addToCart(orderItem));
  };

  return (
    <div className="flex flex-col justify-center items-center gap-8 p-8">
      <h2 className="text-center text-3xl font-bold font-accent">
        Discover Our Products
      </h2>

      <div className="border-2 rounded-md p-8 grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        {data?.content.map((product) => (
          <ProductCard
            key={product.id}
            product={product}
            currency="euro"
            onAddToCart={handleAddToCart}
          />
        ))}
      </div>
    </div>
  );
};

export default Products;
