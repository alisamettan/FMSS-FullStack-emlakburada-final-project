import Link from "next/link";
import ImageCarousel from "./image-carousel";
import { getAllAdverts } from "@/lib/adverts/data";
import { PaginationComp } from "./pagination";

const Adverts = async ({ searchParams }) => {
  console.log("searccccc", searchParams);
  const page = searchParams?.page || 0;

  const { adverts, totalCount } = await getAllAdverts(page);
  console.log("lengthhhh", totalCount);
  console.log(adverts);

  return (
    <div className="p-10">
      <div className="flex flex-wrap gap-8 justify-center">
        {adverts?.map((advert, index) => {
          const isPrioritized = advert.isPrioritized;

          // Pass advertisements should not be displayed
          if (advert.advertStatus === "PASSIVE") {
            return null;
          }

          return (
            <div
              key={index}
              className={`flex flex-col gap-2 shadow-xl w-[20rem] rounded-lg ${
                isPrioritized ? "border-2 border-red-300" : ""
              }`}
            >
              <div>
                <ImageCarousel images={advert.images} />
              </div>

              <div className="px-5 flex flex-col gap-4 py-5">
                <div className="flex flex-col gap-1">
                  <h1
                    className={`text-blue-300 ${
                      isPrioritized ? "text-red-500" : ""
                    }`}
                  >
                    {advert.title}
                  </h1>
                  {isPrioritized && (
                    <span className="text-red-500 font-semibold">
                      Highlighted
                    </span>
                  )}
                  <h2>{advert.city}</h2>
                </div>
                <div className="flex justify-between bg-slate-100 p-2 rounded-md">
                  <p>{advert.price} TRY</p>
                  {advert.advertStatus === "IN_REVIEW" ? (
                    <span className="text-gray-400">IN_REVIEW</span>
                  ) : (
                    <Link
                      className="cursor-pointer hover:text-blue-300"
                      href={`/advert/${advert.id}`}
                    >
                      View Details
                    </Link>
                  )}
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="pt-6">
        <PaginationComp count={totalCount} />
      </div>
    </div>
  );
};

export default Adverts;
