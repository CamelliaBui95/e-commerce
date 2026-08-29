import { ItemGroup } from "@/components/ui/item";
import CartItem from "@/features/cart/component/CartItem";
import { cn } from "@/lib/utils";
import { useSelector } from "react-redux";
import { orderItemsSelector } from "../orderSelector";

interface OrderItemsProps {
  className?: string;
}

const OrderItems: React.FC<OrderItemsProps> = ({ className }) => {
  const orderItems = useSelector(orderItemsSelector);

  return (
    <ItemGroup className={cn("gap-4", className)}>
      {orderItems.map((item) => (
        <CartItem key={item.product_id} item={item} readOnly />
      ))}
    </ItemGroup>
  );
};

export default OrderItems;
