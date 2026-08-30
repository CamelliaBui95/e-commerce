/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_INVENTORY_API_BASE_URL: string;
  readonly VITE_ORDER_API_BASE_URL: string;
  readonly VITE_BUTTON_STYLE: string;
  readonly VITE_STRIPE_PUBLISHABLE_KEY: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
