"use client";
import { usePathname, useRouter, useSearchParams } from "next/navigation";

export function PaginationComp({ totalCount }) {
  const searchParams = useSearchParams();
  const { replace } = useRouter();
  const pathname = usePathname();

  const page = searchParams.get("page") || 1;

  const params = new URLSearchParams(searchParams);
  console.log("faaaaa", searchParams);

  const ITEM_PER_PAGE = 2;

  const hasPrev = ITEM_PER_PAGE * (parseInt(page) - 1) > 0;
  const hasNext =
    ITEM_PER_PAGE * (parseInt(page) - 1) + ITEM_PER_PAGE < totalCount;

  const handleChangePage = (type) => {
    type === "prev"
      ? params.set("page", parseInt(page) - 1)
      : params.set("page", parseInt(page) + 1);

    replace(`${pathname}?${params}`);
  };

  return (
    <div>
      <button disabled={!hasPrev} onClick={() => handleChangePage("prev")}>
        Previous
      </button>
      <button disabled={!hasNext} onClick={() => handleChangePage("next")}>
        Next
      </button>
    </div>
  );
}
