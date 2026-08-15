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
      return "fixed top-0 left-0 bg-full shadow-sm";
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
          "header top-0 p-2 z-50 flex flex-row items-center w-full transition-colors",
          getHeaderStyle()
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
