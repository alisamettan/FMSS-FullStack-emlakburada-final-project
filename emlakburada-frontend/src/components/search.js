import { TextField } from "@mui/material";
import { FaPlus } from "react-icons/fa";

const Search = () => {
  return (
    <div className="bg-blue-200 py-4">
      <div className="text-center flex justify-center items-center gap-10">
        <TextField id="outlined-basic" label="Search" variant="outlined" />
        <div className="bg-slate-400 p-2 rounded-lg text-white flex justify-center items-center gap-1">
          <FaPlus />
          <button>Add Advert</button>
        </div>
      </div>
    </div>
  );
};

export default Search;
