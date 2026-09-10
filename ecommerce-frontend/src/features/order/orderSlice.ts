import { OrderStatus } from "@/enums/orderStatus";
import type { Order } from "@/models/order";
import type { UUID } from "@/models/uuid";
import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

type OrderState = {
  order: Order | null;
  unavailable_items: UUID[];
};

const initialState: OrderState = {
  order: {
    items: [],
    status: null,
  },
  unavailable_items: [],
};

export const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setOrder: (state, action: PayloadAction<Order>) => {
      state.order = action.payload;
    },
    setOrderStatus: (state, action: PayloadAction<OrderStatus>) => {
      state.order.status = action.payload;
    },
    setUnavailableItems: (state, action: PayloadAction<UUID[]>) => {
      state.unavailable_items = action.payload;
    },
  },
});

export const { setOrder, setOrderStatus, setUnavailableItems } =
  orderSlice.actions;
export default orderSlice.reducer;
