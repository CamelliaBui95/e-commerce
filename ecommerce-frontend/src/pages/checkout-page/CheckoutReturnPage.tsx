import { PaymentStatus } from "@/enums/paymentStatus";
import { usePaymentStatus } from "@/hooks/usePaymentStatus";
import { CheckCircle2Icon, CircleX, LoaderCircle } from "lucide-react";
import React, { useMemo } from "react";
import { useSearchParams } from "react-router";

const CheckoutReturnPage = () => {
  const [searchParams] = useSearchParams();
  const sessionId: string = searchParams.get("session_id");

  const { payment } = usePaymentStatus(sessionId);

  const msg = useMemo(() => {
    console.log(payment);
    if (!payment || payment.status === PaymentStatus.PROCESSING) {
      return (
        <>
          <LoaderCircle /> <h3>Payment is processing...</h3>
        </>
      );
    }

    if (payment.status === PaymentStatus.SUCCEEDED) {
      return (
        <>
          <CheckCircle2Icon className="text-green-600" />{" "}
          <h3>Payment Succeeded</h3>!
        </>
      );
    }

    if (payment.status === PaymentStatus.FAILED) {
      return (
        <>
          <CircleX className="text-red-500" /> <h3>Payment Failed</h3>
        </>
      );
    }
  }, [payment]);

  return (
    <div>
      <div className="flex flex-row gap-2 text-xl justify-center items-center p-8">
        {msg}
      </div>
    </div>
  );
};

export default CheckoutReturnPage;
