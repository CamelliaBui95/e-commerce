import type { RootState } from "@/app/store";

export const orderSelector = (root: RootState) => root.order.order;
export const orderStatusSelector = (root: RootState) => root.order.order.status;
