import React from "react";
import "./homePage.scss";

const HomePage = () => {
  return (
    <div className="h-screen">
      <section className="hero-section bg-light-vanilla-cream w-screen h-3/4 flex flex-row items-center justify-between">
        <div className="hero-content">
          <p>
            <span className="text-dark-brown font-bold bg-light-orange rounded-md py-1 px-3">
              New Season
            </span>
          </p>
          <h1 className="text-6xl font-bold">
            Discover products that elevate your everyday
          </h1>
          <p>Curated fashion, home essentials, and more - all in one place.</p>
        </div>

        <img
          className="h-full"
          src="/images/hero-products.png"
          alt="hero-products"
        />
      </section>
    </div>
  );
};

export default HomePage;
