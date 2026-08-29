import { orderApi } from "@/api/api";
import type { Order } from "@/models/order";

const createOrder = async (order: Partial<Order>): Promise<Order> => {
  const res = await orderApi.post<Order>("/order/create", {
    ...order,
  });
  return res.data;
};

const getBaseURL = () => {
  return orderApi.defaults.baseURL;
};

export default {
  createOrder,
  getBaseURL,
};
