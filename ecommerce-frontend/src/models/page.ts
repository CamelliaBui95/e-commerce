export type Page<T> = {
  content: T[];
  page: PageMetadata;
};

export type PageMetadata = {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
};
