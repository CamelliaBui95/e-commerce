import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { ShoppingCartIcon } from "lucide-react";
import { useMemo } from "react";
import { useDispatch, useSelector } from "react-redux";
import { cartItemsCountSelector, cartItemsSelector } from "../cartSelector";
import { ItemGroup } from "@/components/ui/item";
import { cn } from "@/lib/utils";
import { addToCart, decrementItem, removeFromCart } from "../cartSlice";
import type { OrderItem } from "@/models/order";
import CartItem from "./CartItem";
import { useNavigate } from "react-router";

const Cart = () => {
  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartItems = useSelector(cartItemsSelector);
  const navigate = useNavigate();

  const dispatch = useDispatch();

  const handleAddItem = (item: OrderItem) => {
    dispatch(addToCart(item));
  };

  const handleDecrementItem = (item: OrderItem) => {
    dispatch(decrementItem(item));
  };

  const handleRemoveItem = (item: OrderItem) => {
    dispatch(removeFromCart(item));
  };

  const totalPrice = useMemo(() => {
    return cartItems.reduce(
      (acc, item) => acc + item.unit_price * item.quantity,
      0
    );
  }, [cartItems]);

  const cartButton = useMemo(() => {
    return (
      <Button className={cn("relative", import.meta.env.VITE_BUTTON_STYLE)}>
        <ShoppingCartIcon className="size-5" />
        {cartItemCount > 0 && (
          <Badge className="bg-red-500 text-[0.7rem] p-[0.4rem] rounded-full absolute translate-x-3 translate-y-3">
            {cartItemCount}
          </Badge>
        )}
      </Button>
    );
  }, [cartItemCount]);

  return (
    <Sheet>
      <SheetTrigger render={cartButton} />
      <SheetContent>
        <SheetHeader className="h-full">
          <SheetTitle className="font-bold">{`My Cart (${cartItemCount})`}</SheetTitle>
          <div className="flex w-full max-w-md flex-col gap-1 py-2 h-full">
            <ItemGroup className="gap-4 min-h-9/10 border-2 rounded-lg p-2">
              {cartItems.map((item) => (
                <CartItem
                  item={item}
                  onAdd={() => handleAddItem(item)}
                  onDecrement={() => handleDecrementItem(item)}
                  onRemove={() => handleRemoveItem(item)}
                />
              ))}
            </ItemGroup>
            <ul className="py-2">
              <li className="text-lg font-bold flex flex-row justify-between my-1">
                <span>Total</span>
                <span>{totalPrice.toFixed(2)} euro</span>
              </li>
              <li className="text-center">
                <SheetClose
                  render={
                    <Button
                      className="py-2 px-4"
                      onClick={() => navigate("/checkout")}
                    >
                      Checkout
                    </Button>
                  }
                />
              </li>
            </ul>
          </div>
        </SheetHeader>
      </SheetContent>
    </Sheet>
  );
};

export default Cart;
