import type { RootState } from "@/app/store";
import type { OrderItem } from "@/models/order";

export const cartItemsSelector = (root: RootState) => root.cart.items;
export const cartItemsCountSelector = (root: RootState) =>
  root.cart.items.reduce(
    (acc: number, item: OrderItem) => acc + item.quantity,
    0
  );
