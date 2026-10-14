import { configureStore } from "@reduxjs/toolkit";
import configurationReducer from "@/app-configuration/configurationSlice";
import cartReducer from "@/features/cart/cartSlice";
import orderReducer from "@/features/order/orderSlice";

export const store = configureStore({
  reducer: {
    configuration: configurationReducer,
    cart: cartReducer,
    order: orderReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
