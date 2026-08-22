import { Button } from "@/components/ui/button";
import { Card, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ImageSize } from "@/enums/ImageSize";
import productService from "@/services/productService";
import { ShoppingCartIcon } from "lucide-react";
import React from "react";

const PLACEHOLDER_IMAGE = "https://avatar.vercel.sh/shadcn1";

interface ProductCardProps {
  name: string;
  price: number;
  currency: "euro" | "dollar";
  imageName?: string;
}

const currencySymbols: Record<string, string> = {
  euro: "€",
  dollar: "$",
};

const ProductCard: React.FC<ProductCardProps> = ({
  name,
  price,
  currency = "euro",
  imageName,
}) => {
  const imageUrl = productService.getProductImageUrl(
    imageName,
    ImageSize.MEDIUM
  );

  return (
    <Card className="relative w-full pt-0">
      <img
        src={imageUrl ?? PLACEHOLDER_IMAGE}
        alt={name}
        loading="lazy"
        className="relative z-20 aspect-3/4 w-full object-cover"
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
