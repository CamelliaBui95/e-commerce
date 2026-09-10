import { OrderStatus } from "@/enums/orderStatus";
import {
  cartItemsCountSelector,
  cartTotalSelector,
} from "@/features/cart/cartSelector";
import CartItems from "@/features/cart/component/CartItems";
import OrderItems from "@/features/order/component/OrderItems";
import {
  clientSelector,
  orderItemsCountSelector,
  orderSelector,
  orderTotalSelector,
  unavailableItemsSelector,
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

  /** Order stuff */
  /**TODO: Clean up this mess */
  const currentOrder = useSelector(orderSelector);
  const orderId = currentOrder?.id;

  const orderItemCount = useSelector(orderItemsCountSelector);
  const orderTotal = useSelector(orderTotalSelector);
  const client = useSelector(clientSelector);

  /** */
  const unavailableOrderItems = useSelector(unavailableItemsSelector);
  const unavailableProducts = unavailableOrderItems
    ?.map((id) => currentOrder?.items?.find((item) => item?.id === id))
    .filter((item) => !!item)
    .map((item) => item.product_id);

  const itemCount = isPaymentStep ? orderItemCount : cartItemCount;
  const total = isPaymentStep ? orderTotal : cartTotal;

  const [paymentId, setPaymentId] = useState<UUID>(null);

  useEffect(() => {
    if (orderId && isPayable) {
      paymentService.getPaymentIdByOrderId(orderId).then(setPaymentId);
    }
  }, [isPayable, orderId]);

  return (
    <div className="wrapper grid grid-cols-1 place-items-center py-8">
      <h2 className="font-accent font-bold text-2xl mb-10">Checkout</h2>

      <div className="grid grid-cols-2 gap-4 min-w-3/4">
        <ul className="border-2 rounded-lg p-4 relative grid grid-rows-12 gap-1 min-h-[60vh] max-h-[60vh]">
          <li className="row-span-1 font-bold z-2 bg-white w-full flex flex-row justify-between">
            {isPaymentStep || isOrderPending ? (
              <span>{`Your order (${itemCount})`}</span>
            ) : (
              <span>{`Cart (${itemCount})`}</span>
            )}
            {unavailableProducts?.length > 0 && (
              <span className="text-red-500 font-light italic ">
                Some items are currently out of stock
              </span>
            )}
          </li>
          <li className="row-span-10 overflow-y-auto">
            {isOrderPending || isPaymentStep ? (
              <OrderItems />
            ) : (
              <CartItems unavailableItems={unavailableProducts} />
            )}
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
