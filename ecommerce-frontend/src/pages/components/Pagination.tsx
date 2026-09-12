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
  currentPage: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({
  numberOfPages,
  currentPage,
  onPageChange,
}) => {
  const pages = getPages(numberOfPages);

  return (
    <PaginationContainer>
      <PaginationContent>
        <PaginationItem>
          <PaginationPrevious isActive={numberOfPages > 1 && currentPage > 0} />
        </PaginationItem>

        {pages.map((page) => (
          <PaginationItem>
            <PaginationButton
              onClick={() => onPageChange(page)}
              className={
                currentPage === page ? "underline underline-offset-4" : ""
              }
            >
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
          <PaginationNext
            isActive={numberOfPages > 1 && currentPage < numberOfPages - 1}
          />
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
