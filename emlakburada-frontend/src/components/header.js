import { getUser } from "@/lib/user/data";
import Link from "next/link";
import UserDropDown from "./userdropdown";
import { cookies } from "next/headers";

const Header = async () => {
  const cookieStore = cookies();
  const userId = cookieStore.get("userId")?.value || "";

  let fullName = "";

  if (userId) {
    const data = await getUser(userId);
    fullName = data.fullName || "";
  }

  console.log("ALOOOO", userId);

  console.log(fullName);
  return (
    <div className="bg-slate-200">
      <div className="flex justify-between py-5 px-20">
        <Link href="/" className="text-xl text-blue-400">
          EmlakBurada
        </Link>
        {userId ? (
          <div>
            <UserDropDown fullName={fullName} />
          </div>
        ) : (
          <Link
            href="/login"
            className="text-white hover:text-red-500 cursor-pointer bg-slate-400 p-1 rounded-md shadow-md hover:bg-slate-500"
          >
            Sign In
          </Link>
        )}
      </div>
    </div>
  );
};

export default Header;
