import Link from "next/link";
import { IoArrowBackSharp } from "react-icons/io5";

const SubHeader = ({ title }) => {
  return (
    <div className="bg-blue-200 py-6">
      <div className="text-center flex justify-between px-20">
        <p className="text-white text-lg">{title}</p>
        <Link
          href={"/"}
          className="flex items-center gap-2 hover:text-blue-800"
        >
          <IoArrowBackSharp className="text-lg text-blue-400 " />
          <p className="cursor-pointer text-white text-lg">
            Back to Properties
          </p>
        </Link>
      </div>
    </div>
  );
};

export default SubHeader;
