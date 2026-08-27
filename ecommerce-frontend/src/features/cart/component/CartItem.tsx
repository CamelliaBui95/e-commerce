import React from "react";
import {
  Item,
  ItemMedia,
  ItemTitle,
  ItemContent,
  ItemActions,
  ItemDescription,
} from "@/components/ui/item";
import { MinusIcon, PlusIcon, TrashIcon } from "lucide-react";
import productService from "@/services/productService";
import type { OrderItem } from "@/models/order";
import { Button } from "@/components/ui/button";
import { ImageSize } from "@/enums/ImageSize";

export interface CartItemProps {
  item: OrderItem;
  onAdd: () => void;
  onDecrement: () => void;
}

const CartItem: React.FC<CartItemProps> = ({ item, onAdd, onDecrement }) => {
  return (
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
        <ItemTitle className="line-clamp-1">{item.product_name}</ItemTitle>
        <ItemDescription>{item.unit_price} €</ItemDescription>
        <ItemActions>
          <Button
            size="icon"
            className={import.meta.env.VITE_BUTTON_STYLE}
            onClick={onDecrement}
          >
            <MinusIcon />
          </Button>
          <span>{item.quantity}</span>
          <Button
            size="icon"
            className={import.meta.env.VITE_BUTTON_STYLE}
            onClick={onAdd}
          >
            <PlusIcon />
          </Button>
          <Button size="icon" className={import.meta.env.VITE_BUTTON_STYLE}>
            <TrashIcon />
          </Button>
        </ItemActions>
      </ItemContent>
    </Item>
  );
};

export default CartItem;
