import { Category } from "@/enums/category";
import { SortDirection } from "@/enums/sortDirection";
import { addToCart } from "@/features/cart/cartSlice";
import ProductCard from "@/features/product/ProductCard";
import { useSearchProducts } from "@/hooks/useProducts";
import { cn } from "@/lib/utils";
import type { OrderItem } from "@/models/order";
import type { Product } from "@/models/product";
import { Cable, Ellipsis, Handbag, Lamp, Shapes, Shirt } from "lucide-react";
import React, { useState } from "react";
import { useDispatch } from "react-redux";

const CATEGORIES = [
  {
    category: Category.HOME,
    icon: <Lamp strokeWidth={1} />,
  },
  {
    category: Category.CLOTHING,
    icon: <Shirt strokeWidth={1} />,
  },
  {
    category: Category.ACCESSORIES,
    icon: <Handbag strokeWidth={1} />,
  },
  {
    category: Category.TOYS,
    icon: <Shapes strokeWidth={1} />,
  },
  {
    category: Category.ELECTRONICS,
    icon: <Cable strokeWidth={1} />,
  },
  {
    category: Category.OTHER,
    icon: <Ellipsis strokeWidth={1} />,
  },
];

const Products = () => {
  const [category, setCategory] = useState<Category>(null);

  const { data } = useSearchProducts({
    pageNumber: 0,
    pageSize: 10,
    direction: SortDirection.DESC,
    category: category,
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
      <ul className="flex flex-row gap-10">
        {CATEGORIES.map((cat) => (
          <li
            key={cat.category}
            className=" flex flex-row gap-2 hover:cursor-pointer"
            onClick={() => setCategory(cat.category)}
          >
            {cat.icon}
            <span
              className={cn(
                "relative after:absolute after:left-0 after:-bottom-1 after:h-[1px] after:w-0 after:bg-current after:transition-all after:duration-300 hover:after:w-full",
                `${category === cat.category && "after:w-full"}`
              )}
            >
              {cat.category}
            </span>
          </li>
        ))}
      </ul>

      {data?.content?.length > 0 ? (
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
      ) : (
        <div>
          <h3 className="text-xl">Nothing yet...</h3>
        </div>
      )}
    </div>
  );
};

export default Products;
