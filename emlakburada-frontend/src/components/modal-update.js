"use client";
import {
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "./ui/dialog";
import { Button } from "./ui/button";
import { updateAdvert } from "@/lib/adverts/actions";
import { useState } from "react";
import "react-toastify/dist/ReactToastify.css";
import { toast } from "react-toastify";

const ModalUpdate = ({ advert }) => {
  const [isOpen, setIsOpen] = useState(true); // State to manage modal visibility

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);

    try {
      await updateAdvert(formData);
      setIsOpen(false);
      toast.success("Advert updated successfully !!", {
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
      throw error;
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
          <DialogTitle>Edit Advertisement</DialogTitle>
          <DialogDescription>
            Make changes to your advertisement here. Click save when you're
            done.
          </DialogDescription>
        </DialogHeader>
        <div className="grid gap-4 py-4 grid-cols-1 sm:grid-cols-2">
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <input type="hidden" name="id" value={advert?.id} />
            <label htmlFor="city" className="text-right col-span-1">
              City :
            </label>
            <input
              name="city"
              className="col-span-3 border-2"
              defaultValue={advert?.city}
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="district" className="text-right col-span-1">
              District :
            </label>
            <input
              name="district"
              className="col-span-3 border-2"
              defaultValue={advert?.district}
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="homeType" className="text-right col-span-1">
              Home Type :
            </label>
            <input
              name="homeType"
              className="col-span-3 border-2"
              defaultValue={advert?.homeType}
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="advertType" className="text-right col-span-1">
              Advert Type :
            </label>
            <input
              name="advertType"
              defaultValue={advert?.advertType}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="title" className="text-right col-span-1">
              Title :
            </label>
            <input
              name="title"
              defaultValue={advert?.title}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="description" className="text-right col-span-1">
              Description :
            </label>
            <textarea
              name="description"
              defaultValue={advert?.description}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="numberOfRooms" className="text-right col-span-1">
              Number of Rooms :
            </label>
            <input
              name="numberOfRooms"
              type="number"
              defaultValue={advert?.numberOfRooms}
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
              defaultValue={advert?.numberOfBath}
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
              defaultValue={advert?.squareMeters}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="price" className="text-right col-span-1">
              Price :
            </label>
            <input
              name="price"
              type="number"
              defaultValue={advert?.price}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="images" className="text-right col-span-1">
              Images (comma separated URLs) :
            </label>
            <textarea
              name="images"
              defaultValue={advert?.images.map((image) => image.url).join(",")}
              className="col-span-3 border-2"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4 col-span-2 sm:col-span-1">
            <label htmlFor="advertStatus" className="text-right col-span-1">
              Status :
            </label>
            <div className="col-span-3 flex gap-4">
              <label>
                <input
                  type="radio"
                  name="advertStatus"
                  value="ACTIVE"
                  defaultChecked={advert?.advertStatus === "ACTIVE"}
                />
                Active
              </label>
              <label>
                <input
                  type="radio"
                  name="advertStatus"
                  value="PASSIVE"
                  defaultChecked={advert?.advertStatus === "PASSIVE"}
                />
                Passive
              </label>
            </div>
          </div>
        </div>
        <DialogFooter>
          <Button>Save changes</Button>
          <Button onClick={handleClose}>Cancel</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  );
};

export default ModalUpdate;
