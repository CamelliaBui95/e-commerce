import type { RootState } from "@/app/store";

export const cartItemsSelector = (root: RootState) => root.cart.items;
export const cartItemsCountSelector = (root: RootState) =>
  root.cart.items.length;
