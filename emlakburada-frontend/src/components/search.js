import { TextField } from "@mui/material";
import { cookies } from "next/headers";
import { FaPlus } from "react-icons/fa";
import { Tooltip } from "@mui/material";
import { Dialog, DialogTrigger } from "./ui/dialog";
import { Button } from "./ui/button";

import ModalAdd from "./modal-add";

const Search = () => {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value || "";

  const isTokenAvailable = token ? true : false; // Check if token is available

  return (
    <div className="bg-blue-200 py-4">
      <div className="text-center flex justify-center items-center gap-10">
        <TextField id="outlined-basic" label="Search" variant="outlined" />
        <div
          className={`bg-slate-400 p-2 rounded-lg text-white flex justify-center items-center gap-1`}
        >
          {isTokenAvailable ? (
            <Dialog>
              <DialogTrigger asChild>
                <Button variant="outline">
                  <div className="flex items-center gap-1 text-black">
                    <FaPlus />
                    Add Advert
                  </div>
                </Button>
              </DialogTrigger>
              <ModalAdd />
            </Dialog>
          ) : (
            <Tooltip title="You need to login to add advert" arrow>
              <div className="flex items-center gap-1">
                <FaPlus />
                <button disabled>Add Advert</button>
              </div>
            </Tooltip>
          )}
        </div>
      </div>
    </div>
  );
};

export default Search;
