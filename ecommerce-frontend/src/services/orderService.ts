import { orderApi } from "@/api/api";
import type { Order } from "@/models/order";

const ORDERS_PREFIX = "/orders";

const createOrder = async (order: Partial<Order>): Promise<Order> => {
  const res = await orderApi.post<Order>(`${ORDERS_PREFIX}/create`, {
    ...order,
  });
  return res.data;
};

const getURL = () => {
  return orderApi.defaults.baseURL + ORDERS_PREFIX;
};

export default {
  createOrder,
  getURL,
};
