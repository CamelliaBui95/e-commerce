/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_INVENTORY_API_BASE_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
