import { getAdvertByUserId } from "@/lib/adverts/data";
import { cookies } from "next/headers";
import React from "react";
import { FaEye, FaPen, FaTrash } from "react-icons/fa";

const UserProfile = async () => {
  const cookieStore = cookies();
  const userId = cookieStore.get("userId")?.value || "";

  const data = await getAdvertByUserId(userId);

  console.log("USER ADVERTSS", data);

  return (
    <div className="py-10 px-20">
      <table className="w-full shadow-md">
        <thead className="border-b-2">
          <tr className=" text-red-600">
            <td>NAME</td>
            <td>PRICE</td>
            <td>TYPE</td>
            <td>STATUS</td>
            <td>ACTIONS</td>
          </tr>
        </thead>
        <tbody>
          {data.map((advert, index) => {
            return (
              <tr className="border-b" key={index}>
                <td>
                  <p>{advert.title}</p>
                </td>
                <td>
                  <p>{advert.price} TRY</p>
                </td>
                <td>
                  <p>{advert.homeType}</p>
                </td>
                <td>{advert.advertStatus}</td>

                <td>
                  <div className="flex gap-5">
                    <FaEye className="cursor-pointer hover:text-gray-400" />
                    <FaPen className="cursor-pointer hover:text-gray-400" />
                    <FaTrash className="cursor-pointer hover:text-red-400 text-red-300" />
                  </div>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default UserProfile;
