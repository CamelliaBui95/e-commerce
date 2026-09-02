import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ArrowRight, Mail, MapPin, Phone, ShoppingBag } from "lucide-react";
import { Link } from "react-router";

const shopLinks = [
  { label: "All Products", to: "/products" },
  { label: "New Arrivals", to: "/products" },
  { label: "Best Sellers", to: "/products" },
  { label: "Clothing", to: "/products" },
  { label: "Home & Living", to: "/products" },
];

const companyLinks = [
  { label: "About Us", to: "/" },
  { label: "Our Story", to: "/" },
  { label: "Careers", to: "/" },
  { label: "Press", to: "/" },
];

const supportLinks = [
  { label: "Shipping & Delivery", to: "/" },
  { label: "Returns & Refunds", to: "/" },
  { label: "Track My Order", to: "/" },
  { label: "FAQ", to: "/" },
];

/* lucide dropped its brand icons, so the social marks are inlined */
const BrandIcon = ({ path }: { path: string }) => (
  <svg
    viewBox="0 0 24 24"
    fill="currentColor"
    aria-hidden="true"
    className="size-4"
  >
    <path d={path} />
  </svg>
);

const socials = [
  {
    label: "Instagram",
    path: "M12 2c2.7 0 3.1 0 4.1.06 1 .04 1.7.2 2.3.44.6.24 1.1.56 1.6 1.06.5.5.82 1 1.06 1.6.24.6.4 1.3.44 2.3.06 1 .06 1.4.06 4.1s0 3.1-.06 4.1c-.04 1-.2 1.7-.44 2.3a4.4 4.4 0 0 1-1.06 1.6c-.5.5-1 .82-1.6 1.06-.6.24-1.3.4-2.3.44-1 .06-1.4.06-4.1.06s-3.1 0-4.1-.06c-1-.04-1.7-.2-2.3-.44a4.4 4.4 0 0 1-1.6-1.06 4.4 4.4 0 0 1-1.06-1.6c-.24-.6-.4-1.3-.44-2.3C2 15.1 2 14.7 2 12s0-3.1.06-4.1c.04-1 .2-1.7.44-2.3.24-.6.56-1.1 1.06-1.6.5-.5 1-.82 1.6-1.06.6-.24 1.3-.4 2.3-.44C8.9 2 9.3 2 12 2Zm0 1.8c-2.66 0-2.98.01-4.03.06-.97.04-1.5.2-1.85.34-.47.18-.8.4-1.15.75-.35.35-.57.68-.75 1.15-.14.35-.3.88-.34 1.85C3.83 9 3.8 9.34 3.8 12s.01 2.98.06 4.03c.04.97.2 1.5.34 1.85.18.47.4.8.75 1.15.35.35.68.57 1.15.75.35.14.88.3 1.85.34 1.05.05 1.37.06 4.03.06s2.98-.01 4.03-.06c.97-.04 1.5-.2 1.85-.34.47-.18.8-.4 1.15-.75.35-.35.57-.68.75-1.15.14-.35.3-.88.34-1.85.05-1.05.06-1.37.06-4.03s-.01-2.98-.06-4.03c-.04-.97-.2-1.5-.34-1.85a3.1 3.1 0 0 0-.75-1.15 3.1 3.1 0 0 0-1.15-.75c-.35-.14-.88-.3-1.85-.34-1.05-.05-1.37-.06-4.03-.06Zm0 3.07a5.13 5.13 0 1 1 0 10.26 5.13 5.13 0 0 1 0-10.26Zm0 1.8a3.33 3.33 0 1 0 0 6.66 3.33 3.33 0 0 0 0-6.66Zm5.33-3.2a1.2 1.2 0 1 1 0 2.4 1.2 1.2 0 0 1 0-2.4Z",
  },
  {
    label: "Facebook",
    path: "M22 12a10 10 0 1 0-11.56 9.88v-6.99H7.9V12h2.54V9.8c0-2.51 1.5-3.9 3.77-3.9 1.1 0 2.24.2 2.24.2v2.46h-1.26c-1.24 0-1.63.77-1.63 1.56V12h2.78l-.45 2.89h-2.33v6.99A10 10 0 0 0 22 12Z",
  },
  {
    label: "X",
    path: "M18.24 2.25h3.31l-7.23 8.26L22.83 21.75h-6.66l-5.21-6.82-5.97 6.82H1.68l7.73-8.84L1.17 2.25h6.83l4.71 6.23 5.53-6.23Zm-1.16 17.52h1.83L7.01 4.13H5.05l12.03 15.64Z",
  },
];

const FooterLinkColumn = ({
  title,
  links,
}: {
  title: string;
  links: { label: string; to: string }[];
}) => (
  <div>
    <h3 className="text-sm font-bold uppercase tracking-widest text-warm-cream">
      {title}
    </h3>
    <ul className="mt-4 flex flex-col gap-2.5">
      {links.map((link) => (
        <li key={link.label}>
          <Link
            to={link.to}
            className="text-sm text-muted-taupe transition-colors hover:text-soft-ivory"
          >
            {link.label}
          </Link>
        </li>
      ))}
    </ul>
  </div>
);

const Footer = () => {
  const year = new Date().getFullYear();

  return (
    <footer className="bg-charcoal text-soft-ivory">
      <div className="mx-auto max-w-7xl px-6 py-10">
        <div className="grid gap-10 md:grid-cols-2 lg:grid-cols-5">
          <div className="lg:col-span-2">
            <div className="flex flex-row items-center gap-2 text-xl font-bold">
              <ShoppingBag width={20} />
              <span>Shopora</span>
            </div>

            <p className="mt-4 max-w-sm text-sm leading-relaxed text-muted-taupe">
              Curated fashion, home essentials, and more - all in one place.
              Thoughtfully sourced pieces that elevate your everyday.
            </p>

            <ul className="mt-6 flex flex-col gap-3 text-sm text-muted-taupe">
              <li className="flex flex-row items-center gap-2.5">
                <MapPin size={16} strokeWidth={1.5} />
                <span>12 Rue de ABC, 59000 Lille, France</span>
              </li>
              <li className="flex flex-row items-center gap-2.5">
                <Phone size={16} strokeWidth={1.5} />
                <a href="tel:+33320000000" className="hover:text-soft-ivory">
                  +33 3 20 00 00 00
                </a>
              </li>
              <li className="flex flex-row items-center gap-2.5">
                <Mail size={16} strokeWidth={1.5} />
                <a
                  href="mailto:hello@shopora.com"
                  className="hover:text-soft-ivory"
                >
                  hello@shopora.com
                </a>
              </li>
            </ul>
          </div>

          <FooterLinkColumn title="Shop" links={shopLinks} />
          <FooterLinkColumn title="Company" links={companyLinks} />
          <FooterLinkColumn title="Support" links={supportLinks} />
        </div>

        <div className="mt-12 flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
          <div>
            <h3 className="font-accent text-lg font-bold text-warm-cream">
              Join our newsletter
            </h3>
            <p className="mt-1 text-sm text-muted-taupe">
              New arrivals, private sales and 10% off your first order.
            </p>
          </div>

          <form
            className="flex w-full max-w-md flex-row items-center gap-2"
            onSubmit={(e) => e.preventDefault()}
          >
            <label htmlFor="newsletter-email" className="sr-only">
              Email address
            </label>
            <Input
              id="newsletter-email"
              type="email"
              required
              placeholder="Enter your email"
              className="h-10 border-muted-taupe/40 text-soft-ivory placeholder:text-muted-taupe"
            />
            <Button type="submit" className="h-10 shrink-0">
              Subscribe
              <ArrowRight />
            </Button>
          </form>
        </div>
      </div>

      <div className="border-t border-muted-taupe/20">
        <div className="mx-auto flex max-w-7xl flex-col-reverse items-center gap-4 px-6 py-6 text-sm text-muted-taupe sm:flex-row sm:justify-between">
          <p>&copy; {year} Shopora. All rights reserved.</p>

          <div className="flex flex-row items-center gap-6">
            <div className="flex flex-row items-center gap-4">
              <Link to="/" className="hover:text-soft-ivory">
                Privacy
              </Link>
              <Link to="/" className="hover:text-soft-ivory">
                Terms
              </Link>
            </div>

            <div className="flex flex-row items-center gap-3">
              {socials.map((social) => (
                <a
                  key={social.label}
                  href="/"
                  aria-label={social.label}
                  className="rounded-full border border-muted-taupe/30 p-2 transition-colors hover:border-soft-ivory hover:text-soft-ivory"
                >
                  <BrandIcon path={social.path} />
                </a>
              ))}
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
