import "./homePage.scss";
import { Button } from "@/components/ui/button";
import { ArrowRight, RotateCcw, ShieldCheck, Truck } from "lucide-react";
import ServiceHighlight from "../components/service-highlight/ServiceHighlight";
import ProductCard from "../components/ProductCard";
import { useSearchProducts } from "@/hooks/useProducts";
import { SortDirection } from "@/enums/sortDirection";
import { useDispatch } from "react-redux";
import { addToCart } from "@/features/cart/cartSlice";
import type { OrderItem } from "@/models/order";
import type { Product } from "@/models/product";

const serviceHighlights = [
  {
    icon: <Truck size={25} strokeWidth={1} />,
    title: "Free Shipping",
    description: "On orders over 50$",
  },
  {
    icon: <ShieldCheck size={25} strokeWidth={1} />,
    title: "Secure Payment",
    description: "100% protected",
  },
  {
    icon: <RotateCcw size={25} strokeWidth={1} />,
    title: "Easy Returns",
    description: "30-day return policy",
  },
];

const HomePage = () => {
  const { data } = useSearchProducts({
    pageNumber: 0,
    pageSize: 4,
    direction: SortDirection.DESC,
  });

  const dispatch = useDispatch();

  const handleAddToCart = (product: Product) => {
    const orderItem: OrderItem = {
      product_id: product.id,
      product_name: product.name,
      image_name: product.image_name,
      quantity: 1,
      unit_price: product.price,
    };

    dispatch(addToCart(orderItem));
  };

  return (
    <div className="h-screen">
      <section className="hero-section bg-soft-ivory w-screen h-3/4 flex flex-row items-center justify-between overflow-hidden">
        <div className="hero-content">
          <p>
            <span className="text-dark-brown font-bold bg-warm-cream rounded-md py-1 px-3">
              New Season
            </span>
          </p>
          <h1 className="text-6xl font-bold text-charcoal font-accent">
            Discover products that elevate your everyday
          </h1>
          <p className="text-xl text-dark-brown">
            Curated fashion, home essentials, and more - all in one place.
          </p>
          <Button size="lg" className="w-40">
            Shop now
            <ArrowRight />
          </Button>

          <div className="service-highlights flex flex-row gap-2 items-center">
            {serviceHighlights.map((sh) => (
              <ServiceHighlight
                icon={sh.icon}
                title={sh.title}
                description={sh.description}
              />
            ))}
          </div>
        </div>

        <img
          className="h-140 self-end translate-x-15 translate-y-15"
          src="/images/hero-products.png"
          alt="hero-products"
        />
      </section>

      <section className="">
        <div className="w-fit my-6 mx-auto">
          <h2
            id="new-arrivals"
            className="text-xl text-charcoal text-center font-bold"
          >
            New Arrivals
          </h2>
          <div className="w-1/2 mx-auto mt-2 border-t-4 border-charcoal" />
        </div>

        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
          {data?.content.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
              currency="euro"
              onAddToCart={handleAddToCart}
            />
          ))}
        </div>
      </section>
    </div>
  );
};

export default HomePage;
