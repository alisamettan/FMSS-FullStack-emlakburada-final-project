import ImageCarousel from "@/components/image-carousel";
import { getAdvertById } from "@/lib/adverts/data";
import { getUser } from "@/lib/user/data";

const Advert = async ({ params }) => {
  const { id } = params;
  const advert = await getAdvertById(id);
  const user = await getUser(advert.userId);

  console.log("USEEER", user);
  console.log("IDDDD", id);
  console.log("FAAALAAN", advert);
  return (
    <div className="py-8 px-10">
      <h1 className="text-2xl">{advert.title}</h1>
      <div className="flex gap-4 pt-5">
        <div className="w-[50%] shadow-xl rounded-md">
          <ImageCarousel images={advert.images} />
        </div>
        <div className="w-[50%] flex flex-col gap-6 shadow-md p-5">
          <div className="flex flex-col gap-4">
            <h1 className="text-xl border-b">Features</h1>
            <div className="flex flex-col gap-2">
              <div className="flex justify-between">
                <p>Bathrooms</p>
                <p>{advert.numberOfBath}</p>
              </div>
              <div className="flex justify-between">
                <p>Rooms</p>
                <p>{advert.numberOfRooms}</p>
              </div>
            </div>
          </div>
          <div className="flex flex-col gap-4">
            <h1 className="text-xl border-b">Address</h1>
            <div className="flex flex-col gap-2">
              <div className="flex justify-between">
                <p>City</p>
                <p>{advert.city}</p>
              </div>
              <div className="flex justify-between">
                <p>District</p>
                <p>{advert.district}</p>
              </div>
            </div>
          </div>
          <div className="flex flex-col gap-4">
            <h1 className="text-xl border-b">Owner Details</h1>
            <div className="flex flex-col gap-2">
              <div className="flex justify-between">
                <p>Name</p>
                <p>{user.fullName}</p>
              </div>
              <div className="flex justify-between">
                <p>E-mail</p>
                <p>{user.email}</p>
              </div>
            </div>
          </div>
          <div className="mt-auto">
            <p className="text-xl">
              {advert.price} TRY / {advert.advertType}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Advert;
