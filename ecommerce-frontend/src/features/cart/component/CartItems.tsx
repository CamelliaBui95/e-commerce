import { ItemGroup } from "@/components/ui/item";
import CartItem from "./CartItem";
import { useDispatch, useSelector } from "react-redux";
import { addToCart, decrementItem, removeFromCart } from "../cartSlice";
import type { OrderItem } from "@/models/order";
import { cartItemsSelector } from "../cartSelector";
import { cn } from "@/lib/utils";
import type { UUID } from "@/models/uuid";

interface CartItemsProps {
  className?: string;
  unavailableItems?: UUID[];
}

const CartItems: React.FC<CartItemsProps> = ({
  className,
  unavailableItems = [],
}) => {
  const cartItems = useSelector(cartItemsSelector);
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

  return (
    <ItemGroup className={cn("gap-4", className)}>
      {cartItems.map((item) => (
        <CartItem
          key={item.product_id}
          item={item}
          onAdd={() => handleAddItem(item)}
          onDecrement={() => handleDecrementItem(item)}
          onRemove={() => handleRemoveItem(item)}
          variant={
            unavailableItems.includes(item.product_id) ? "danger" : "outline"
          }
        />
      ))}
    </ItemGroup>
  );
};

export default CartItems;
