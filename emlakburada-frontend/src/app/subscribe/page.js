import ModalPay from "@/components/modal-pay";
import { Button } from "@/components/ui/button";
import { Dialog, DialogTrigger } from "@/components/ui/dialog";
import { getPackages } from "@/lib/packages/data";

const Packages = async () => {
  const data = await getPackages();

  console.log(data);

  return (
    <div className="p-20">
      <div className="flex flex-col gap-12">
        <h1 className="text-3xl">Packages</h1>
        <div className="flex gap-4 flex-wrap justify-center">
          {data.map((item, index) => {
            const isSuperUltimate = item.packageType === "SUPER_ULTIMATE";
            return (
              <div
                key={index}
                className="flex flex-col gap-6 shadow-md w-[30%] px-6 py-10 border rounded-md relative"
              >
                {isSuperUltimate && (
                  <div className="absolute top-0 left-0 bg-yellow-300 text-black p-2 rounded-br-lg">
                    This package makes your adverts prioritized..!
                  </div>
                )}
                <h1 className="text-lg text-red-300">{item.name}</h1>
                <h2 className="text-lg">{item.listingRights} Listing Rights</h2>
                <h2>
                  <span className="text-3xl">₺{item.price}</span>/ay
                </h2>

                <Dialog>
                  <DialogTrigger asChild>
                    <Button className="bg-blue-200 hover:bg-blue-300">
                      Buy
                    </Button>
                  </DialogTrigger>
                  <ModalPay packageId={item.id} />
                </Dialog>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default Packages;
