import axios from "axios";

export const inventoryApi = axios.create({
  baseURL: import.meta.env.VITE_INVENTORY_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});

export const orderApi = axios.create({
  baseURL: import.meta.env.VITE_ORDER_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});

export const paymentApi = axios.create({
  baseURL: import.meta.env.VITE_PAYMENT_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});
