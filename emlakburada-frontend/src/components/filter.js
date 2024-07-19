"use client";
import { Button, Fade, Menu, MenuItem, TextField } from "@mui/material";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";
import { CiFilter } from "react-icons/ci";
import { useDebouncedCallback } from "use-debounce";

const Filter = () => {
  const [anchorEl, setAnchorEl] = useState(null);
  const [sort, setSort] = useState(null);
  const searchParams = useSearchParams();
  const { replace } = useRouter();
  const pathname = usePathname();

  //title searching
  const handleSearch = useDebouncedCallback((e) => {
    const params = new URLSearchParams(searchParams);

    params.set("page", 0);

    if (e.target.value) {
      e.target.value.length > 2 && params.set("title", e.target.value);
    } else {
      params.delete("title");
    }

    if (sort) {
      params.set("sort", sort);
    } else {
      params.delete("sort");
    }

    replace(`${pathname}?${params}`);
  }, 300);

  //price sorting
  const handleSortChange = (newSort) => {
    const params = new URLSearchParams(searchParams);

    if (newSort === "default") {
      params.delete("sort");
      setSort(null);
    } else {
      params.set("sort", newSort);
      setSort(newSort);
    }

    params.set("page", 0); // Reset page to 0 on sort change

    replace(`${pathname}?${params}`);
    handleClose();
  };

  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div className="flex items-center gap-4">
      <TextField
        id="outlined-basic"
        label="Search"
        variant="outlined"
        type="text"
        onChange={handleSearch}
      />

      <div>
        <Button
          id="fade-button"
          aria-controls={open ? "fade-menu" : undefined}
          aria-haspopup="true"
          aria-expanded={open ? "true" : undefined}
          onClick={handleClick}
        >
          <CiFilter className="text-2xl" />
        </Button>
        <Menu
          id="fade-menu"
          MenuListProps={{
            "aria-labelledby": "fade-button",
          }}
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          TransitionComponent={Fade}
        >
          <MenuItem onClick={() => handleSortChange("price:asc")}>
            price:asc
          </MenuItem>
          <MenuItem onClick={() => handleSortChange("price:desc")}>
            price:desc
          </MenuItem>
          <MenuItem onClick={() => handleSortChange("default")}>
            default
          </MenuItem>
        </Menu>
      </div>
    </div>
  );
};

export default Filter;
