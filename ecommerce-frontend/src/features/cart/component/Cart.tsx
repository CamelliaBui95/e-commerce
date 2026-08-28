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
import { useSelector } from "react-redux";
import { cartItemsCountSelector, cartTotalSelector } from "../cartSelector";
import { cn } from "@/lib/utils";
import { useNavigate } from "react-router";
import CartItems from "./CartItems";

const Cart = () => {
  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartTotal = useSelector(cartTotalSelector);
  const navigate = useNavigate();

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
            <CartItems />
            <ul className="py-2">
              <li className="text-lg font-bold flex flex-row justify-between my-1">
                <span>Total</span>
                <span>{cartTotal} euro</span>
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
