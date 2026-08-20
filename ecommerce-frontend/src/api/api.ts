import axios from "axios";

export const inventoryApi = axios.create({
  baseURL: import.meta.env.VITE_INVENTORY_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});
