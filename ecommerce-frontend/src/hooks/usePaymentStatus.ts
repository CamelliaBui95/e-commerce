import { PaymentStatus } from "@/enums/paymentStatus";
import type { Payment } from "@/models/payment";
import paymentService from "@/services/paymentService";
import { useEffect, useState } from "react";

const POLL_INTERVAL_MS = 1500;
const POLL_TIMEOUT_MS = 30000;

const isTerminal = (payment: Payment) =>
  payment !== null &&
  [PaymentStatus.SUCCEEDED, PaymentStatus.FAILED].includes(payment.status);

export function usePaymentStatus(sessionId: string) {
  const [payment, setPayment] = useState<Payment>(null);

  useEffect(() => {
    if (!sessionId) {
      return;
    }

    let cancelled = false;
    const deadline = Date.now() + POLL_TIMEOUT_MS;

    async function poll() {
      try {
        if (cancelled) {
          return;
        }

        const foundPayment = await paymentService.getPaymentBySessionId(
          sessionId
        );

        if (foundPayment) {
          setPayment(foundPayment);
        }

        if (isTerminal(foundPayment)) {
          return;
        }

        if (Date.now() >= deadline) {
          return;
        }
      } catch (error) {
        console.log(error);
      }
    }

    const timer: ReturnType<typeof setTimeout> = setTimeout(
      poll,
      POLL_INTERVAL_MS
    );

    return () => {
      cancelled = true;
      console.log("Clearning timer...");
      clearTimeout(timer);
    };
  }, [sessionId]);

  return {
    payment,
  };
}
