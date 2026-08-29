import {
  orderSelector,
  orderStatusSelector,
} from "@/features/order/orderSelector";
import { setOrderStatus } from "@/features/order/orderSlice";
import type { OrderStatusEvent } from "@/models/order";
import orderService from "@/services/orderService";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export function useOrderStatus() {
  const currentOrder = useSelector(orderSelector);
  const currentOrderStatus = useSelector(orderStatusSelector);

  const [connected, setConnected] = useState<boolean>(false);

  const dispatch = useDispatch();

  useEffect(() => {
    if (!currentOrder || !currentOrder.id) {
      return;
    }

    const eventSource = new EventSource(
      `${orderService.getURL()}/${currentOrder.id}/events`
    );

    eventSource.onopen = () => {
      setConnected(true);
    };

    eventSource.addEventListener("order-status", (event) => {
      const msg = event as MessageEvent;
      const data: OrderStatusEvent = JSON.parse(msg.data);
      dispatch(setOrderStatus(data.status));
    });

    eventSource.onerror = () => {
      setConnected(false);
    };

    return () => {
      eventSource.close();
    };
  }, [currentOrder, currentOrder?.id, dispatch]);

  return {
    connected,
    status: currentOrderStatus,
  };
}
