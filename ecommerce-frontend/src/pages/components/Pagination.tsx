import React from "react";
import {
  PaginationButton,
  Pagination as PaginationContainer,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

interface PaginationProps {
  numberOfPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({
  numberOfPages,
  onPageChange,
}) => {
  const pages = getPages(numberOfPages);

  return (
    <PaginationContainer>
      <PaginationContent>
        <PaginationItem>
          <PaginationPrevious isActive={numberOfPages > 1} />
        </PaginationItem>

        {pages.map((page) => (
          <PaginationItem>
            <PaginationButton onClick={() => onPageChange(page)}>
              {page + 1}
            </PaginationButton>
          </PaginationItem>
        ))}

        {numberOfPages > 3 && (
          <PaginationItem>
            <PaginationEllipsis />
          </PaginationItem>
        )}

        <PaginationItem>
          <PaginationNext isActive={numberOfPages > 1} />
        </PaginationItem>
      </PaginationContent>
    </PaginationContainer>
  );
};

export default Pagination;

function getPages(pageCount: number) {
  const pages = [];
  for (let i = 0; i < pageCount; i++) {
    pages.push(i);
  }
  return pages;
}
