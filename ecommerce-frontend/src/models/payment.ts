import type { PaymentStatus } from "@/enums/paymentStatus";
import type { UUID } from "./uuid";

export type PaymentSessionRes = {
  client_secret: string;
};

export type Payment = {
  id: UUID;
  order_id: UUID;
  amount: number;
  status: PaymentStatus;
  stripe_session_id: string;
  created_at: string;
  processed_at: string;
};
