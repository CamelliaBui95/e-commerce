import React, { useMemo, useState } from "react";
import {
  CheckoutFormProvider,
  CheckoutForm as StripeCheckoutForm,
  useCheckoutForm,
} from "@stripe/react-stripe-js/checkout";
import { loadStripe } from "@stripe/stripe-js";
import type { StripeCheckoutFormConfirmEvent } from "@stripe/stripe-js";

import paymentService from "@/services/paymentService";
import type { UUID } from "@/models/uuid";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

const PaymentForm = () => {
  const result = useCheckoutForm();
  const [error, setError] = useState<string | null>(null);

  const handleConfirm = async (event: StripeCheckoutFormConfirmEvent) => {
    if (result.type !== "success") {
      return;
    }

    setError(null);

    const confirmation = await result.checkout.confirm({
      formConfirmEvent: event,
    });

    if (confirmation.type === "error") {
      setError(confirmation.error.message);
    }
  };

  return (
    <div className="flex h-full flex-col gap-4 overflow-y-auto">
      <StripeCheckoutForm onConfirm={handleConfirm} />
      {error && <p className="text-sm text-red-500">{error}</p>}
    </div>
  );
};

interface CheckoutFormProps {
  paymentId: UUID;
}

const CheckoutForm: React.FC<CheckoutFormProps> = ({ paymentId }) => {
  const clientSecret = useMemo(
    () =>
      paymentService
        .createPaymentSession(paymentId)
        .then((res) => res.client_secret),
    [paymentId]
  );

  return (
    <CheckoutFormProvider stripe={stripePromise} options={{ clientSecret }}>
      <PaymentForm />
    </CheckoutFormProvider>
  );
};

export default CheckoutForm;
