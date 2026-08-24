import type { UUID } from "./uuid";

export type Client = {
  id?: UUID;
  first_name: string;
  last_name: string;
  address: string;
  email: string;
  phone_number: string;
};
