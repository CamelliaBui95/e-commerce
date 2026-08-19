import { Button } from "@/components/ui/button";
import { Card, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ShoppingCartIcon } from "lucide-react";
import React from "react";

interface ProductCardProps {
  name: string;
  price: number;
  currency: "euro" | "dollar";
}

const currencySymbols: Record<string, string> = {
  euro: "€",
  dollar: "$",
};

const ProductCard: React.FC<ProductCardProps> = ({
  name,
  price,
  currency = "euro",
}) => {
  return (
    <Card className="relative w-[250px] pt-0">
      <img
        src="https://avatar.vercel.sh/shadcn1"
        alt="Event cover"
        className="relative z-20 aspect-video w-full object-cover brightness-60 grayscale dark:brightness-40"
      />
      <CardHeader>
        {/* <CardAction>
          <Badge variant="secondary">20$</Badge>
        </CardAction> */}
        <CardTitle className="flex flex-row justify-between">
          <span>{name}</span>
          <span>
            {price}
            {currencySymbols[currency]}
          </span>
        </CardTitle>
      </CardHeader>
      <CardFooter>
        <Button className="w-full">
          <ShoppingCartIcon data-icon="inline-start" /> Add to cart
        </Button>
      </CardFooter>
    </Card>
  );
};

export default ProductCard;
