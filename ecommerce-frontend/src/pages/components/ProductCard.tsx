import { Button } from "@/components/ui/button";
import { Card, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ImageSize } from "@/enums/ImageSize";
import type { Product } from "@/models/product";
import productService from "@/services/productService";
import { ShoppingCartIcon } from "lucide-react";
import React from "react";

const PLACEHOLDER_IMAGE = "https://avatar.vercel.sh/shadcn1";

interface ProductCardProps {
  currency: "euro" | "dollar";
  product: Product;
  onAddToCart: (product: Product) => void;
}

const currencySymbols: Record<string, string> = {
  euro: "€",
  dollar: "$",
};

const ProductCard: React.FC<ProductCardProps> = ({
  currency = "euro",
  product,
  onAddToCart,
}) => {
  const imageUrl = productService.getProductImageUrl(
    product?.image_name,
    ImageSize.MEDIUM
  );

  return (
    <Card className="relative w-full pt-0">
      <img
        src={imageUrl ?? PLACEHOLDER_IMAGE}
        alt={product.name}
        loading="lazy"
        className="relative z-20 aspect-3/4 w-full object-cover"
      />
      <CardHeader>
        <CardTitle className="flex flex-row justify-between">
          <span>{product.name}</span>
          <span>
            {product.price}
            {currencySymbols[currency]}
          </span>
        </CardTitle>
      </CardHeader>
      <CardFooter>
        <Button
          className="w-full h-8"
          onClick={(_: React.MouseEvent<HTMLButtonElement, MouseEvent>) =>
            onAddToCart(product)
          }
        >
          <ShoppingCartIcon data-icon="inline-start" /> Add to cart
        </Button>
      </CardFooter>
    </Card>
  );
};

export default ProductCard;
