import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { ShoppingCartIcon } from "lucide-react";
import { useMemo } from "react";
import { useSelector } from "react-redux";
import { cartItemsCountSelector, cartItemsSelector } from "../cartSelector";
import {
  Item,
  ItemGroup,
  ItemMedia,
  ItemTitle,
  ItemContent,
} from "@/components/ui/item";
import productService from "@/services/productService";
import { ImageSize } from "@/enums/ImageSize";

const Cart = () => {
  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartItems = useSelector(cartItemsSelector);

  const cartButton = useMemo(() => {
    return (
      <Button className="bg-transparent text-primary rounded-full p-3 hover:bg-primary/70 hover:text-warm-cream relative">
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
        <SheetHeader>
          <SheetTitle>{`My Cart (${cartItemCount})`}</SheetTitle>
          <div className="flex w-full max-w-md flex-col gap-6">
            <ItemGroup className="gap-4">
              {cartItems.map((item) => (
                <Item key={item.product_id} variant="outline" role="listitem">
                  <ItemMedia variant="image" className="h-20 w-16">
                    <img
                      src={productService.getProductImageUrl(
                        item?.image_name,
                        ImageSize.SMALL
                      )}
                      alt={item?.product_name}
                      className="object-cover"
                    />
                  </ItemMedia>
                  <ItemContent>
                    <ItemTitle className="line-clamp-1">
                      {item.product_name}
                    </ItemTitle>
                  </ItemContent>
                </Item>
              ))}
            </ItemGroup>
          </div>
        </SheetHeader>
      </SheetContent>
    </Sheet>
  );
};

export default Cart;
