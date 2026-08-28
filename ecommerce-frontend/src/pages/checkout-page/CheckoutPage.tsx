import {
  cartItemsCountSelector,
  cartTotalSelector,
} from "@/features/cart/cartSelector";
import CartItems from "@/features/cart/component/CartItems";
import React, { useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";

const CheckoutPage = () => {
  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartTotal = useSelector(cartTotalSelector);

  return (
    <div className="wrapper grid grid-cols-1 place-items-center py-8">
      <div className="grid grid-cols-2 gap-4 min-w-3/4">
        <ul className="border-2 rounded-lg p-4 relative grid grid-rows-12 gap-1 min-h-[70vh] max-h-[70vh]">
          <li className="row-span-1 font-bold z-2 bg-white w-full ">{`Cart (${cartItemCount})`}</li>
          <li className="row-span-10 overflow-y-auto">
            <CartItems />
          </li>
          <li className="row-span-1 flex items-center justify-end font-bold">
            Total: {cartTotal} euros
          </li>
        </ul>

        <div className="min-h-[70vh] max-h-[70vh]">Hello</div>
      </div>
    </div>
  );
};

export default CheckoutPage;
