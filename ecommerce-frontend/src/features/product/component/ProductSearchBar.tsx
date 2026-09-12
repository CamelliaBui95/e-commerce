import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ProductSortBy } from "@/enums/productSortBy";
import { SortDirection } from "@/enums/sortDirection";
import { cn } from "@/lib/utils";
import {
  ArrowDownWideNarrow,
  ArrowUpNarrowWide,
  Search,
  X,
} from "lucide-react";
import React from "react";

const SORT_OPTIONS: { sortBy: ProductSortBy; label: string }[] = [
  { sortBy: ProductSortBy.NAME, label: "Name" },
  { sortBy: ProductSortBy.PRICE, label: "Price" },
  { sortBy: ProductSortBy.CREATED_AT, label: "Newest" },
];

interface ProductSearchBarProps {
  searchTerm?: string;
  sortBy?: ProductSortBy;
  direction?: SortDirection;
  onSearchTermChange?: (name: string) => void;
  onSubmit?: (name: string) => void;
  onSortByChange?: (sortBy: ProductSortBy) => void;
  onDirectionChange?: (direction: SortDirection) => void;
  className?: string;
}

const ProductSearchBar: React.FC<ProductSearchBarProps> = ({
  searchTerm = "",
  sortBy = ProductSortBy.CREATED_AT,
  direction = SortDirection.DESC,
  onSearchTermChange,
  onSubmit,
  onSortByChange,
  onDirectionChange,
  className,
}) => {
  const isAscending = direction === SortDirection.ASC;

  return (
    <form
      onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSubmit?.(searchTerm);
      }}
      className={cn(
        "flex flex-col gap-4 md:flex-row md:items-center md:justify-between",
        className
      )}
    >
      <div className="flex flex-row gap-8">
        <div className="relative w-full md:max-w-sm">
          <Search
            strokeWidth={1}
            className="pointer-events-none absolute top-1/2 left-3 size-4 -translate-y-1/2 text-muted-foreground"
          />
          <Input
            type="search"
            value={searchTerm}
            placeholder="Search products..."
            aria-label="Search products"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              onSearchTermChange?.(e.target.value)
            }
            className="h-10 rounded-lg pr-9 pl-9 [&::-webkit-search-cancel-button]:hidden"
          />
          {searchTerm && (
            <Button
              type="button"
              variant="ghost"
              size="icon-sm"
              aria-label="Clear search"
              onClick={() => onSearchTermChange?.("")}
              className="absolute top-1/2 right-1.5 -translate-y-1/2 hover:cursor-pointer"
            >
              <X strokeWidth={1} />
            </Button>
          )}
        </div>
        <div className="flex flex-row items-center gap-3">
          <span className="hidden text-sm text-muted-foreground sm:inline">
            Sort by
          </span>

          <ul className="flex flex-row items-center gap-1 rounded-lg border p-1">
            {SORT_OPTIONS.map((option) => (
              <li key={option.sortBy}>
                <Button
                  type="button"
                  variant={sortBy === option.sortBy ? "secondary" : "ghost"}
                  size="xs"
                  aria-pressed={sortBy === option.sortBy}
                  onClick={() => onSortByChange?.(option.sortBy)}
                  className="hover:cursor-pointer"
                >
                  {option.label}
                </Button>
              </li>
            ))}
          </ul>

          <Button
            type="button"
            variant="outline"
            size="icon-lg"
            title={isAscending ? "Ascending" : "Descending"}
            aria-label={isAscending ? "Sort ascending" : "Sort descending"}
            onClick={() =>
              onDirectionChange?.(
                isAscending ? SortDirection.DESC : SortDirection.ASC
              )
            }
            className="hover:cursor-pointer"
          >
            {isAscending ? (
              <ArrowUpNarrowWide strokeWidth={1} />
            ) : (
              <ArrowDownWideNarrow strokeWidth={1} />
            )}
          </Button>
        </div>
      </div>
    </form>
  );
};

export default ProductSearchBar;
