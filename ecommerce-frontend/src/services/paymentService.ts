import { paymentApi } from "@/api/api";
import type { Payment, PaymentSessionRes } from "@/models/payment";
import type { UUID } from "@/models/uuid";
import axios from "axios";

const PAYMENTS_PREFIX = "/payments";

const getPaymentIdByOrderId = async (orderId: UUID): Promise<UUID | null> => {
  try {
    const res = await paymentApi.get<UUID>(PAYMENTS_PREFIX, {
      params: { order_id: orderId },
    });
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 404) {
      return null;
    }
    throw error;
  }
};

const getPaymentBySessionId = async (
  sessionId: string
): Promise<Payment | null> => {
  try {
    const res = await paymentApi.get<Payment>(
      `${PAYMENTS_PREFIX}/sessions/${sessionId}`
    );
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 404) {
      return null;
    }
    throw error;
  }
};

const createPaymentSession = async (
  paymentId: UUID
): Promise<PaymentSessionRes> => {
  const res = await paymentApi.get<PaymentSessionRes>(
    `${PAYMENTS_PREFIX}/${paymentId}/session`
  );
  return res.data;
};

const getURL = () => {
  return paymentApi.defaults.baseURL + PAYMENTS_PREFIX;
};

export default {
  getPaymentIdByOrderId,
  getPaymentBySessionId,
  createPaymentSession,
  getURL,
};
