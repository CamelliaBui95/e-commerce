import type { OrderItemStatus } from "@/enums/orderItemStatus";
import type { UUID } from "./uuid";
import type { OrderStatus } from "@/enums/orderStatus";
import type { Client } from "./client";

export type OrderItem = {
  id?: UUID;
  product_id: UUID;
  product_name?: string;
  image_name?: string;
  quantity: number;
  unit_price: number;
  status?: OrderItemStatus;
};

export type Order = {
  id?: UUID;
  items: OrderItem[];
  created_at: string;
  status?: OrderStatus;
  client?: Client;
};
