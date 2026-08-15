import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu";
import { cn } from "@/lib/utils";
import { useEffect, useRef, useState } from "react";
import { Link } from "react-router";

const Header = () => {
  const [isPastHero, setPastHero] = useState<boolean>(false);
  const [headerHeight, setHeaderHeight] = useState<number>(0);
  const headerRef = useRef(null);

  useEffect(() => {
    if (headerRef.current) {
      setHeaderHeight(headerRef.current.offsetHeight);
    }
  }, []);

  useEffect(() => {
    const heroSection = document.querySelector(".hero-section");

    const handleScroll = () => {
      if (heroSection && heroSection.getBoundingClientRect().bottom <= 0) {
        setPastHero(true);
      } else {
        setPastHero(false);
      }
    };

    handleScroll();
    window.addEventListener("scroll", handleScroll, { passive: true });
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    console.log(isPastHero);
  }, [isPastHero]);

  return (
    <>
      <div
        ref={headerRef}
        className={cn(
          "header top-0 p-2 z-50 flex flex-row items-center w-full transition-colors",
          isPastHero ? "fixed top-0 left-0 bg-full shadow-sm" : "absolute"
        )}
      >
        <div className="logo text-xl font-bold text-pink-800">
          My E-commerce
        </div>

        <NavigationMenu className="ml-auto">
          <NavigationMenuList>
            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                Home
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuTrigger>Products</NavigationMenuTrigger>
              <NavigationMenuContent>
                <NavigationMenuLink render={<Link to="/products" />}>
                  Link
                </NavigationMenuLink>
              </NavigationMenuContent>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
      </div>
    </>
  );
};

export default Header;
