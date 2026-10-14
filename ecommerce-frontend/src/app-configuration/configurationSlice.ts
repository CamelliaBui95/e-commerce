import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

export interface ConfigurationState {
  payment_mode: string;
}

const initialState: ConfigurationState = {
  payment_mode: null,
};

export const configurationSlice = createSlice({
  name: "configuration",
  initialState,
  reducers: {
    setPaymentMode: (state, action: PayloadAction<string>) => {
      state.payment_mode = action.payload;
    },
  },
});

export const { setPaymentMode } = configurationSlice.actions;
export default configurationSlice.reducer;
