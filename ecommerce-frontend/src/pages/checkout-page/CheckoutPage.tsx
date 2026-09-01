import { OrderStatus } from "@/enums/orderStatus";
import {
  cartItemsCountSelector,
  cartTotalSelector,
} from "@/features/cart/cartSelector";
import CartItems from "@/features/cart/component/CartItems";
import OrderItems from "@/features/order/component/OrderItems";
import {
  clientSelector,
  orderIdSelector,
  orderItemsCountSelector,
  orderTotalSelector,
} from "@/features/order/orderSelector";
import ContactForm from "./ContactForm";
import { useSelector } from "react-redux";
import CheckoutForm from "./CheckoutForm";
import { useOrderStatus } from "@/hooks/useOrderStatus";
import paymentService from "@/services/paymentService";
import { useEffect, useState } from "react";
import type { UUID } from "@/models/uuid";

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
  const { connected, status: orderStatus } = useOrderStatus();
  const isPaymentStep =
    !!orderStatus && PAYMENT_STEP_STATUSES.includes(orderStatus);
  const isOrderPending =
    !!orderStatus && ORDER_PENDING_STATUSES.includes(orderStatus);

  const isPayable = orderStatus === OrderStatus.PAYMENT_PENDING;

  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartTotal = useSelector(cartTotalSelector);

  const orderId = useSelector(orderIdSelector);
  const orderItemCount = useSelector(orderItemsCountSelector);
  const orderTotal = useSelector(orderTotalSelector);
  const client = useSelector(clientSelector);

  const itemCount = isPaymentStep ? orderItemCount : cartItemCount;
  const total = isPaymentStep ? orderTotal : cartTotal;

  const [paymentId, setPaymentId] = useState<UUID>(null);

  useEffect(() => {
    if (orderId && isPayable) {
      paymentService.getPaymentIdByOrderId(orderId).then(setPaymentId);
    }
  }, [isPayable, orderId]);

  useEffect(() => {
    console.log(orderStatus);
  }, [orderStatus]);

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

        <div className="min-h-[60vh] max-h-[60vh] overflow-auto">
          {isPayable && paymentId ? (
            <CheckoutForm paymentId={paymentId} client={client} />
          ) : (
            <ContactForm />
          )}
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
