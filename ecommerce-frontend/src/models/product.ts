import type { Category } from "@/enums/category";
import type { UUID } from "./uuid";

export type Product = {
  id: UUID;
  name: string;
  stock: number;
  number_reserved: number;
  price: number;
  category: Category;
  image_path?: string;
};
