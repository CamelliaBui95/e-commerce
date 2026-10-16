import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { PaymentStatus } from "@/enums/paymentStatus";
import { cn } from "@/lib/utils";
import React from "react";
import "./mockPaymentForm.scss";
import { Button } from "@/components/ui/button";

const paymentStatuses = [
  { label: PaymentStatus.SUCCEEDED, value: PaymentStatus.SUCCEEDED },
  { label: PaymentStatus.FAILED, value: PaymentStatus.FAILED },
];

const MockPaymentForm = () => {
  const handleSubmit = () => {};

  return (
    <form
      onSubmit={handleSubmit}
      className="border-2 rounded-lg p-4 flex h-full flex-col gap-4"
    >
      <h2 className="font-bold">Mock payment</h2>

      <div className="flex flex-col h-full justify-center items-center gap-3">
        <div className="flex justify-center items-center">
          <CreditCardReader
            size={75}
            strokeWidth={1}
            className="stroke-dark-brown animate-[bounce_2s_linear_infinite]"
          />

          <div className="w-80 px-3">
            <div className="mock-payment-progression"></div>
          </div>

          <div className="card-carousel">
            <div className="card-group">
              <CreditCardCheck
                size={75}
                strokeWidth={1}
                className="stroke-dark-brown"
              />
              <CreditCardX
                size={75}
                strokeWidth={1}
                className="stroke-dark-brown"
              />
            </div>
            <div aria-hidden className="card-group">
              <CreditCardCheck
                size={75}
                strokeWidth={1}
                className="stroke-dark-brown"
              />
              <CreditCardX
                size={75}
                strokeWidth={1}
                className="stroke-dark-brown"
              />
            </div>
          </div>
        </div>

        <Select
          items={paymentStatuses}
          onValueChange={(value: PaymentStatus) => {
            console.log(value);
          }}
        >
          <h3 className="font-bold">Select a payment status</h3>
          <SelectTrigger className="w-full max-w-1/2">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              {paymentStatuses.map((item) => (
                <SelectItem key={item.value} value={item.value}>
                  {item.label}
                </SelectItem>
              ))}
            </SelectGroup>
          </SelectContent>
        </Select>
        <Button type="submit" size="lg">
          Confirm
        </Button>
      </div>
    </form>
  );
};

export default MockPaymentForm;

const CreditCardReader = ({
  size,
  strokeWidth,
  className = "",
}: {
  size: number;
  strokeWidth: number;
  stroke?: string;
  className?: string;
}) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    stroke-width={strokeWidth}
    stroke-linecap="round"
    stroke-linejoin="round"
    className={cn("lucide lucide-credit-card-reader preview-icon", className)}
  >
    <path d="M15 16v1" />
    <path d="M16.963 7.734A1 1 0 0015.999 7H8.003a1 1 0 00-.964.734L4.073 18.467A2 2 0 006 21h12a2 2 0 001.927-2.532z" />
    <path d="M2.678 8.5A2 2 0 012 7V5a2 2 0 012-2h16a2 2 0 012 2v2a2 2 0 01-.676 1.499" />
    <path d="m9 21 2-14" />
  </svg>
);

const CreditCardX = ({
  size,
  strokeWidth,
  className = "",
}: {
  size: number;
  strokeWidth: number;
  stroke?: string;
  className?: string;
}) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    stroke-width={strokeWidth}
    stroke-linecap="round"
    stroke-linejoin="round"
    className={cn("lucide lucide-credit-card-x preview-icon", className)}
  >
    <path d="M12.5 19H4a2 2 0 01-2-2V7a2 2 0 012-2h16a2 2 0 012 2v3.5" />
    <path d="M2 10h20" />
    <path d="M6 14h2" />
    <path d="m16.5 14.5 5 5" />
    <path d="m21.5 14.5-5 5" />
  </svg>
);

const CreditCardCheck = ({
  size,
  strokeWidth,
  className = "",
}: {
  size: number;
  strokeWidth: number;
  stroke?: string;
  className?: string;
}) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    stroke-width={strokeWidth}
    stroke-linecap="round"
    stroke-linejoin="round"
    className={cn("lucide lucide-credit-card-check preview-icon", className)}
  >
    <path d="M12.5 19H4a2 2 0 01-2-2V7a2 2 0 012-2h16a2 2 0 012 2v4" />
    <path d="M2 10h20" />
    <path d="M6 14h2" />
    <path d="m16 17 2 2 4-4" />
  </svg>
);
