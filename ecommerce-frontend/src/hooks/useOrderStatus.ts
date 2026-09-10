import {
  orderSelector,
  orderStatusSelector,
} from "@/features/order/orderSelector";
import {
  setOrderStatus,
  setUnavailableItems,
} from "@/features/order/orderSlice";
import type { OrderStatusEvent } from "@/models/order";
import orderService from "@/services/orderService";
import { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export function useOrderStatus() {
  const currentOrder = useSelector(orderSelector);
  const currentOrderStatus = useSelector(orderStatusSelector);
  const orderId = currentOrder?.id;

  const [connected, setConnected] = useState<boolean>(false);

  const dispatch = useDispatch();

  // Keeps the latest status readable inside the SSE handler without making it
  // an effect dependency - otherwise every event would reopen the connection.
  const statusRef = useRef(currentOrderStatus);

  useEffect(() => {
    statusRef.current = currentOrderStatus;
  }, [currentOrderStatus]);

  useEffect(() => {
    if (!orderId) {
      return;
    }

    const eventSource = new EventSource(
      `${orderService.getURL()}/${orderId}/events`
    );

    eventSource.onopen = () => {
      setConnected(true);
    };

    eventSource.addEventListener("order-status", (event) => {
      const msg = event as MessageEvent;
      const data: OrderStatusEvent = JSON.parse(msg.data);

      if (data.order_id !== orderId || data.status === statusRef.current) {
        return;
      }

      dispatch(setOrderStatus(data.status));
      dispatch(setUnavailableItems(data.unavailable_items));
    });

    eventSource.onerror = () => {
      setConnected(false);
    };

    return () => {
      setConnected(false);
      eventSource.close();
    };
  }, [orderId, dispatch]);

  return {
    connected,
    status: currentOrderStatus,
  };
}
