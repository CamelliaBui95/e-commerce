import type { Category } from "@/enums/category";
import type { UUID } from "./uuid";
import type { ProductSortBy } from "@/enums/productSortBy";
import type { SortDirection } from "@/enums/sortDirection";

export type Product = {
  id: UUID;
  name: string;
  stock: number;
  number_reserved: number;
  price: number;
  category: Category;
  image_name?: string;
  created_at: Date;
};

export type ProductSearchQuery = {
  pageNumber: number;
  pageSize: number;
  name: string;
  sortBy: ProductSortBy;
  direction: SortDirection;
  category: Category;
};
