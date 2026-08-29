import { OrderStatus } from "@/enums/orderStatus";
import type { Order } from "@/models/order";
import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

type OrderState = {
  order: Order | null;
};

const initialState: OrderState = {
  order: {
    items: [],
    status: null,
  },
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
  },
});

export const { setOrder, setOrderStatus } = orderSlice.actions;
export default orderSlice.reducer;
