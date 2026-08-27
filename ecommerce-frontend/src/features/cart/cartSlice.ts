import type { OrderItem } from "@/models/order";
import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";

export interface CartState {
  items: OrderItem[];
}

const initialState: CartState = {
  items: [],
};

export const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action: PayloadAction<OrderItem>) => {
      const newItem = action.payload;
      const existing = state.items.find(
        (item) => item.product_id === newItem.product_id
      );
      if (existing) {
        existing.quantity += 1;
        return;
      }
      state.items.push(action.payload);
    },
    decrementItem: (state, action: PayloadAction<OrderItem>) => {
      const { product_id } = action.payload;

      const index = state.items.findIndex(
        (item) => item.product_id === product_id
      );
      if (index === -1) {
        return;
      }

      const item = state.items[index];
      item.quantity -= 1;

      if (item.quantity <= 0) {
        state.items.splice(index, 1);
      }
    },
    removeFromCart: (state, action: PayloadAction<OrderItem>) => {
      const itemToRemove = action.payload;
      const itemIndex = state.items.findIndex(
        (item) => item.product_id === itemToRemove.product_id
      );

      if (itemIndex === -1) {
        return;
      }

      state.items.splice(itemIndex, 1);
    },
  },
});

export const { addToCart, decrementItem, removeFromCart } = cartSlice.actions;
export default cartSlice.reducer;
