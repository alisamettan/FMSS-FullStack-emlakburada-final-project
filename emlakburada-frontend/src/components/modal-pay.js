"use client";
import {
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "./ui/dialog";
import { Button } from "./ui/button";
import { useState } from "react";
import "react-toastify/dist/ReactToastify.css";
import { toast } from "react-toastify";
import { useForm } from "react-hook-form";
import { purchase } from "@/lib/order/action";

const ModalPay = ({ packageId }) => {
  const [isOpen, setIsOpen] = useState(true);
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm({
    defaultValues: {
      cardNumber: "",
      expiration: "",
      cvc: "",
      packageId: packageId,
    },
    mode: "all",
  });

  const onSubmit = async (formData) => {
    try {
      await purchase(formData);
      setIsOpen(false);
      toast.success("Purchase successfull!!", {
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
      console.error("Error:", error.message);
    }
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  if (!isOpen) {
    return null;
  }
  return (
    <DialogContent className="max-w-[30rem]">
      <form onSubmit={handleSubmit(onSubmit)}>
        <DialogHeader>
          <DialogTitle>Payment Information</DialogTitle>
          <DialogDescription>
            Enter your payment details below.
          </DialogDescription>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-1 gap-4">
            <input type="hidden" name="packageId" {...register("packageId")} />
            <label htmlFor="cardNumber" className="text-right">
              Card Number:
            </label>
            <input
              type="text"
              className="border-2 p-2"
              {...register("cardNumber", {
                required: "Card number can not be empty!",
                minLength: {
                  value: 16,
                  message: "Card number must be exactly 16 digits",
                },
                maxLength: {
                  value: 16,
                  message: "Card number must be exactly 16 digits",
                },
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Card number must only contain digits",
                },
              })}
            />
            {errors.cardNumber && (
              <p className="text-sm text-red-700 mt-1">
                {errors.cardNumber.message}
              </p>
            )}
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label htmlFor="expirationDate" className="text-right">
                Expiration Date:
              </label>
              <input
                {...register("expiration", {
                  required: "Expiration date can not be empty!",
                })}
                type="text"
                placeholder="MM/YY"
                className="border-2 p-2 w-full"
              />
              {errors.expiration && (
                <p className="text-sm text-red-700 mt-1">
                  {errors.expiration.message}
                </p>
              )}
            </div>
            <div>
              <label htmlFor="cvc" className="text-right">
                CVC:
              </label>
              <input
                {...register("cvc", {
                  required: "Cvc can not be empty!",
                })}
                type="text"
                required
                className="border-2 p-2 w-full"
              />
              {errors.cvc && (
                <p className="text-sm text-red-700 mt-1">
                  {errors.cvc.message}
                </p>
              )}
            </div>
          </div>
        </div>
        <DialogFooter>
          <Button type="submit" disabled={!isValid}>
            Submit Payment
          </Button>
          <Button onClick={handleClose}>Cancel</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  );
};

export default ModalPay;
