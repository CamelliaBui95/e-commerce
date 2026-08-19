import React, { type JSX } from "react";
import "./homePage.scss";
import { Button } from "@/components/ui/button";
import { ArrowRight, RotateCcw, ShieldCheck, Truck } from "lucide-react";
import ServiceHighlight from "../components/service-highlight/ServiceHighlight";

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
  return (
    <div className="h-screen">
      <section className="hero-section bg-soft-ivory w-screen h-3/4 flex flex-row items-center justify-between overflow-hidden">
        <div className="hero-content">
          <p>
            <span className="text-dark-brown font-bold bg-warm-cream rounded-md py-1 px-3">
              New Season
            </span>
          </p>
          <h1 className="text-6xl font-bold text-charcoal">
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

      <section className="h-full">TEST</section>
    </div>
  );
};

export default HomePage;
