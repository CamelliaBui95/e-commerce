import { OrderStatus } from "@/enums/orderStatus";
import {
  cartItemsCountSelector,
  cartTotalSelector,
} from "@/features/cart/cartSelector";
import CartItems from "@/features/cart/component/CartItems";
import OrderItems from "@/features/order/component/OrderItems";
import {
  orderItemsCountSelector,
  orderStatusSelector,
  orderTotalSelector,
} from "@/features/order/orderSelector";
import ContactForm from "./ContactForm";
import { useSelector } from "react-redux";

const PAYMENT_STEP_STATUSES: OrderStatus[] = [
  OrderStatus.PAYMENT_PENDING,
  OrderStatus.PAYMENT_SUCCEEDED,
  OrderStatus.PAYMENT_FAILED,
];

const ORDER_PENDING_STATUSES: OrderStatus[] = [
  OrderStatus.ORDER_CREATING,
  /**TODO: Remove this status */
  OrderStatus.INVENTORY_PENDING,
  OrderStatus.INVENTORY_RESERVED,
];

const CheckoutPage = () => {
  const orderStatus = useSelector(orderStatusSelector);
  const isPaymentStep =
    !!orderStatus && PAYMENT_STEP_STATUSES.includes(orderStatus);
  const isOrderPending =
    !!orderStatus && ORDER_PENDING_STATUSES.includes(orderStatus);

  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartTotal = useSelector(cartTotalSelector);
  const orderItemCount = useSelector(orderItemsCountSelector);
  const orderTotal = useSelector(orderTotalSelector);

  const itemCount = isPaymentStep ? orderItemCount : cartItemCount;
  const total = isPaymentStep ? orderTotal : cartTotal;

  return (
    <div className="wrapper grid grid-cols-1 place-items-center py-8">
      <div className="grid grid-cols-2 gap-4 min-w-3/4">
        <ul className="border-2 rounded-lg p-4 relative grid grid-rows-12 gap-1 min-h-[60vh] max-h-[60vh]">
          <li className="row-span-1 font-bold z-2 bg-white w-full ">
            {isPaymentStep || isOrderPending
              ? `Your order (${itemCount})`
              : `Cart (${itemCount})`}
          </li>
          <li className="row-span-10 overflow-y-auto">
            {isOrderPending || isPaymentStep ? <OrderItems /> : <CartItems />}
          </li>
          <li className="row-span-1 flex items-center justify-end font-bold">
            Total: {total} euros
          </li>
        </ul>

        <div className="min-h-[60vh] max-h-[60vh]">
          <ContactForm />
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
