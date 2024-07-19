import Actions from "@/components/user-profile-actions";
import { deleteAdvert } from "@/lib/adverts/actions";
import { getAdvertByUserId } from "@/lib/adverts/data";
import { getUser } from "@/lib/user/data";
import { cookies } from "next/headers";
import Link from "next/link";
import { FaEye, FaPen, FaTrash } from "react-icons/fa";

const UserProfile = async () => {
  const cookieStore = cookies();
  const userId = cookieStore.get("userId")?.value || "";

  const data = await getAdvertByUserId(userId);
  const userData = await getUser(userId);

  console.log("USER ADVERTSS", data);

  // Date hesaplamaları
  const today = new Date();
  const endDate = new Date(userData.endDateOfPackage);
  const timeDiff = endDate - today;
  const daysLeft = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

  return (
    <div className="py-10 px-20">
      <div className="mb-6 flex flex-col gap-2 float-end text-gray-400">
        <h2 className="text-xl font-bold">User Package Details</h2>
        <p>
          <strong>Listing Rights:</strong> {userData.listingRights}
        </p>
        <p>
          <strong>Package End Date:</strong> {userData.endDateOfPackage} (
          {daysLeft} days left)
        </p>
      </div>
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
                  <Actions advert={advert} />
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
