import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu";
import Cart from "@/features/cart/component/Cart";
import { cn } from "@/lib/utils";
import { ShoppingBag, User2Icon } from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { Link, useLocation } from "react-router";

const Header = () => {
  const [isPastHero, setPastHero] = useState<boolean>(false);
  const [headerHeight, setHeaderHeight] = useState<number>(0);
  const headerRef = useRef(null);

  const { pathname } = useLocation();

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
  }, [pathname]);

  const getHeaderStyle = () => {
    if (pathname !== "/" || isPastHero) {
      return "fixed top-0 left-0 bg-white shadow-sm";
    } else {
      return "absolute";
    }
  };

  return (
    <>
      {pathname !== "/" && <div style={{ height: `${headerHeight}px` }}></div>}
      <div
        ref={headerRef}
        className={cn(
          "header top-0 py-4 px-6 z-50 flex flex-row items-center w-full transition-colors",
          getHeaderStyle()
        )}
      >
        <div
          id="logo"
          className="text-xl text-charcoal font-bold flex flex-row gap-2 justify-center items-center"
        >
          <ShoppingBag width={20} />
          <span>Shopora</span>
        </div>

        <NavigationMenu className="ml-auto">
          <NavigationMenuList>
            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                Shop
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuTrigger>Categories</NavigationMenuTrigger>
              <NavigationMenuContent>
                <NavigationMenuLink render={<Link to="/products" />}>
                  Link
                </NavigationMenuLink>
              </NavigationMenuContent>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                New Arrivals
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                Best Sellers
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                About Us
              </NavigationMenuLink>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>

        <NavigationMenu className="ml-auto">
          <NavigationMenuList>
            <NavigationMenuItem>
              <NavigationMenuLink render={<Link to="/" />}>
                <User2Icon className="size-5" />
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <Cart />
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
      </div>
    </>
  );
};

export default Header;
