import { inventoryApi } from "@/api/api";
import type { Product } from "@/models/product";

const getProducts = async (
  pageNumber: number,
  pageSize: number
): Promise<Product[]> => {
  const response = await inventoryApi.get<Product[]>(
    `/products/list-products?pageNumber=${pageNumber}&pageSize=${pageSize}`
  );
  return response.data;
};

export default {
  getProducts,
};
