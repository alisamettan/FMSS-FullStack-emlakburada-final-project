"use client";
import { saveAdvert } from "@/lib/adverts/actions";
import { Button } from "./ui/button";
import {
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "./ui/dialog";
import { toast } from "react-toastify";
import { useState } from "react";
import "react-toastify/dist/ReactToastify.css";

const ModalAdd = () => {
  const [isOpen, setIsOpen] = useState(true);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);

    try {
      await saveAdvert(formData);
      setIsOpen(false);
      toast.success("Advert added successfully !!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      });
    } catch (error) {
      toast.error(error.message, {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      });
    }
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  if (!isOpen) {
    return null;
  }
  return (
    <DialogContent className="max-w-[70rem]">
      <form onSubmit={handleSubmit}>
        <DialogHeader>
          <DialogTitle>Add Advert</DialogTitle>
          <DialogDescription>
            Fill the blanks to list your adverts. Click save changes when you
            are done.
          </DialogDescription>
        </DialogHeader>
        <div className="grid gap-4 py-4 grid-cols-1 sm:grid-cols-2">
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="city" className="text-right col-span-1">
              City :
            </label>
            <input name="city" className="col-span-3 border-2" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="district" className="text-right col-span-1">
              District :
            </label>
            <input name="district" className="col-span-3 border-2" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="homeType" className="text-right col-span-1">
              Home Type :
            </label>
            <input
              name="homeType"
              className="col-span-3 border-2"
              placeholder="GARDEN_FLOOR, ROOF_DUPLEX, APARTMENT, DUPLEX, TRIPLEX,"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="advertType" className="text-right col-span-1">
              Advert Type :
            </label>
            <input
              name="advertType"
              className="col-span-3 border-2"
              placeholder="FOR_RENT, FOR_DAILY_RENT, FOR_SALE"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="title" className="text-right col-span-1">
              Title :
            </label>
            <input name="title" className="col-span-3 border-2" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="description" className="text-right col-span-1">
              Description :
            </label>
            <textarea name="description" className="col-span-3 border-2" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="numberOfRooms" className="text-right col-span-1">
              Number of Rooms :
            </label>
            <input
              name="numberOfRooms"
              type="number"
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="numberOfBath" className="text-right col-span-1">
              Number of Baths :
            </label>
            <input
              name="numberOfBath"
              type="number"
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="squareMeters" className="text-right col-span-1">
              Square Meters :
            </label>
            <input
              name="squareMeters"
              type="number"
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="price" className="text-right col-span-1">
              Price :
            </label>
            <input name="price" type="number" className="col-span-3 border-2" />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="images" className="text-right col-span-1">
              Images (comma separated URLs) :
            </label>
            <textarea name="images" className="col-span-3 border-2" />
          </div>
        </div>
        <DialogFooter>
          <Button type="submit">Save changes</Button>
          <Button onClick={handleClose}>Cancel</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  );
};

export default ModalAdd;
