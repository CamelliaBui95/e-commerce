import type { RootState } from "@/app/store";

export const paymentModeSelector = (root: RootState) => root.configuration.payment_mode;