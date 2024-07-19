"use client";
import { Button } from "@mui/material";
import { usePathname, useRouter, useSearchParams } from "next/navigation";

export function PaginationComp({ count, page, size }) {
  const searchParams = useSearchParams();
  const { replace } = useRouter();
  const pathname = usePathname();

  const hasPrev = page > 0;
  const hasNext = (page + 1) * size < count;

  const handleChangePage = (type) => {
    const newPage = type === "prev" ? page - 1 : page + 1;
    const params = new URLSearchParams(searchParams);
    params.set("page", newPage);

    replace(`${pathname}?${params.toString()}`);
  };

  return (
    <div>
      <Button
        className="text-lg"
        disabled={!hasPrev}
        onClick={() => handleChangePage("prev")}
      >
        Previous
      </Button>
      <Button
        className="text-lg"
        disabled={!hasNext}
        onClick={() => handleChangePage("next")}
      >
        Next
      </Button>
    </div>
  );
}
