import { deleteAdvert } from "@/lib/adverts/actions";
import { cookies } from "next/headers";
import Link from "next/link";
import { redirect } from "next/navigation";
import React from "react";
import { FaEye, FaPen, FaTrash } from "react-icons/fa";
import { Dialog, DialogTrigger } from "./ui/dialog";
import { Button } from "./ui/button";
import ModalUpdate from "./modal-update";

const Actions = async ({ advert }) => {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value || "";

  const deleteAction = async () => {
    "use server";
    try {
      await deleteAdvert(advert.id, token);
    } catch (e) {
      throw e;
    }
  };

  return (
    <div className="flex gap-5 items-center">
      <Link href={`/advert/${advert.id}`}>
        <FaEye className="cursor-pointer hover:text-gray-400" />
      </Link>
      <Dialog>
        <DialogTrigger asChild>
          <Button variant="outline">
            <FaPen className="cursor-pointer hover:text-gray-400" />
          </Button>
        </DialogTrigger>
        <ModalUpdate advert={advert} token={token} />
      </Dialog>

      <form action={deleteAction}>
        <button type="submit">
          <FaTrash className="cursor-pointer hover:text-red-400 text-red-300" />
        </button>
      </form>
    </div>
  );
};

export default Actions;
