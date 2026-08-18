import React from "react";
import "./homePage.scss";

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
        </div>

        <img
          className="h-145 self-end translate-x-15 translate-y-20"
          src="/images/hero-products.png"
          alt="hero-products"
        />
      </section>

      <section className="h-full">TEST</section>
    </div>
  );
};

export default HomePage;
