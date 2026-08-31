import type { RootState } from "@/app/store";
import type { OrderItem } from "@/models/order";

export const orderSelector = (root: RootState) => root.order.order;

export const orderIdSelector = (root: RootState) => root.order.order?.id;

export const orderStatusSelector = (root: RootState) =>
  root.order.order?.status;

export const orderItemsSelector = (root: RootState) =>
  root.order.order?.items ?? [];

export const orderItemsCountSelector = (root: RootState) =>
  orderItemsSelector(root).reduce(
    (acc: number, item: OrderItem) => acc + item.quantity,
    0
  );

export const orderTotalSelector = (root: RootState) =>
  orderItemsSelector(root)
    .reduce(
      (acc: number, item: OrderItem) => acc + item.quantity * item.unit_price,
      0
    )
    .toFixed(2);
