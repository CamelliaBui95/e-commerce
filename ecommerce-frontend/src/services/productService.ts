import { inventoryApi } from "@/api/api";
import type { Page } from "@/models/page";
import type { Product, ProductSearchQuery } from "@/models/product";

const searchProducts = async (
  query: Partial<ProductSearchQuery>
): Promise<Page<Product>> => {
  const response = await inventoryApi.get<Page<Product>>("/products/search", {
    params: query,
  });

  return response.data;
};

const getProductImageUrl = (imageName?: string): string | undefined => {
  if (!imageName) return undefined;

  const baseUrl = inventoryApi.defaults.baseURL ?? "";

  return `${baseUrl}/product-image?imageName=${encodeURIComponent(imageName)}`;
};

export default {
  searchProducts,
  getProductImageUrl,
};
