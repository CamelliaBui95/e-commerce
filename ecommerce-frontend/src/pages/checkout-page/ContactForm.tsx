import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";
import { OrderStatus } from "@/enums/orderStatus";
import {
  cartItemsCountSelector,
  cartItemsSelector,
} from "@/features/cart/cartSelector";
import { setOrder, setOrderStatus } from "@/features/order/orderSlice";
import { useOrderStatus } from "@/hooks/useOrderStatus";
import type { Client } from "@/models/client";
import type { Order } from "@/models/order";
import orderService from "@/services/orderService";
import { ArrowRight, Loader, LogInIcon } from "lucide-react";
import { useMemo, useState, type ChangeEvent, type FormEvent } from "react";
import { useDispatch, useSelector } from "react-redux";

const initialClient: Client = {
  first_name: "",
  last_name: "",
  address: "",
  email: "",
  phone_number: "",
};

const ContactForm = () => {
  const cartItemCount = useSelector(cartItemsCountSelector);
  const cartItems = useSelector(cartItemsSelector);
  const { connected, status: orderStatus } = useOrderStatus();

  const [client, setClient] = useState<Client>(initialClient);

  const dispatch = useDispatch();

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setClient((previous) => ({ ...previous, [name]: value }));
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    dispatch(setOrderStatus(OrderStatus.ORDER_CREATING));

    const order: Order = {
      items: cartItems,
      client: client,
    };

    try {
      const orderCreated = await orderService.createOrder(order);
      if (orderCreated) {
        dispatch(setOrder(orderCreated));
      }
    } catch {
      dispatch(setOrderStatus(OrderStatus.ORDER_FAILED));
    }
  };

  const isOrderPending = useMemo(() => {
    switch (orderStatus) {
      case OrderStatus.INVENTORY_PENDING:
        return true;
      case OrderStatus.ORDER_CREATING:
        return true;
      default:
        return false;
    }
  }, [orderStatus]);

  return (
    <form
      onSubmit={handleSubmit}
      className="border-2 rounded-lg p-4 flex h-full flex-col gap-4"
    >
      <h2 className="font-bold">Contact details</h2>

      <div className="flex flex-1 flex-col gap-4 overflow-y-auto p-4 border-1 rounded-lg">
        <div className="grid grid-cols-2 gap-4">
          <div className="flex flex-col gap-2">
            <Label htmlFor="first_name">
              First name<span className="text-red-500">*</span>
            </Label>
            <Input
              id="first_name"
              name="first_name"
              value={client.first_name}
              onChange={handleChange}
              autoComplete="given-name"
              required
            />
          </div>

          <div className="flex flex-col gap-2">
            <Label htmlFor="last_name">
              Last name<span className="text-red-500">*</span>
            </Label>
            <Input
              id="last_name"
              name="last_name"
              value={client.last_name}
              onChange={handleChange}
              autoComplete="family-name"
              required
            />
          </div>
        </div>

        <div className="flex flex-col gap-2">
          <Label htmlFor="email">
            Email<span className="text-red-500">*</span>
          </Label>
          <Input
            id="email"
            name="email"
            type="email"
            value={client.email}
            onChange={handleChange}
            autoComplete="email"
            required
          />
        </div>

        <div className="flex flex-col gap-2">
          <Label htmlFor="phone_number">
            Phone number<span className="text-red-500">*</span>
          </Label>
          <Input
            id="phone_number"
            name="phone_number"
            type="tel"
            value={client.phone_number}
            onChange={handleChange}
            autoComplete="tel"
            required
          />
        </div>

        <div className="flex flex-col gap-2">
          <Label htmlFor="address">
            Address<span className="text-red-500">*</span>
          </Label>
          <Input
            id="address"
            name="address"
            value={client.address}
            onChange={handleChange}
            autoComplete="street-address"
            required
          />
        </div>
      </div>
      <div>
        <Button
          type="submit"
          size="lg"
          className="w-full"
          disabled={cartItemCount === 0 || isOrderPending}
        >
          {isOrderPending ? "Confirming" : "Next"}
          {isOrderPending ? <Loader /> : <ArrowRight />}
        </Button>
        <div className="flex items-center gap-3 py-1">
          <Separator className="flex-1" />
          <span className="text-sm text-muted-foreground">Or</span>
          <Separator className="flex-1" />
        </div>
        <Button
          size="lg"
          className="w-full"
          disabled={cartItemCount === 0 || isOrderPending}
          variant="secondary"
        >
          Sign In
          <LogInIcon />
        </Button>
      </div>
    </form>
  );
};

export default ContactForm;
