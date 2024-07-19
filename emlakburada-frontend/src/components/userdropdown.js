"use client";
import { logout } from "@/lib/login/actions";
import { Button, Fade, Menu, MenuItem } from "@mui/material";
import Cookies from "js-cookie";
// import { cookies } from "next/headers";
import { useRouter } from "next/navigation";
import { startTransition, useState } from "react";
import { CiLogout } from "react-icons/ci";
import { IoPersonOutline } from "react-icons/io5";

const UserDropDown = ({ fullName }) => {
  const router = useRouter();
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    startTransition(() => {
      logout();
    });
  };

  return (
    <div className="flex items-center gap-1">
      <IoPersonOutline />
      <div>
        <Button
          id="fade-button"
          aria-controls={open ? "fade-menu" : undefined}
          aria-haspopup="true"
          aria-expanded={open ? "true" : undefined}
          onClick={handleClick}
        >
          {fullName}
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
          <MenuItem
            onClick={() => {
              router.push("/user/profile");
            }}
          >
            Properties
          </MenuItem>
          <MenuItem
            onClick={() => {
              router.push("/subscribe");
            }}
          >
            Subscribe
          </MenuItem>
          <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </Menu>
      </div>
    </div>
  );
};

export default UserDropDown;
