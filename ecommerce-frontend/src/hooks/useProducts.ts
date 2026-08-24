import type { ProductSearchQuery } from "@/models/product";
import type { UUID } from "@/models/uuid";
import productService from "@/services/productService";
import { keepPreviousData, useQuery } from "@tanstack/react-query";

export const productKeys = {
  all: () => ["products"] as const,
  lists: () => [...productKeys.all(), "lists"] as const,
  list: (query: Partial<ProductSearchQuery>) =>
    [...productKeys.lists(), query] as const,
  detail: (id: UUID) => [...productKeys.all(), "detail", id] as const,
};

export const useSearchProducts = (query: Partial<ProductSearchQuery>) => {
  return useQuery({
    queryKey: productKeys.list(query),
    queryFn: () => productService.searchProducts(query),
    placeholderData: keepPreviousData,
    staleTime: 60 * 60 * 1000,
  });
};
