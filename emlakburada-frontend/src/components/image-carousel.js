import { Card, CardContent } from "@/components/ui/card";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";
import Image from "next/image";

const ImageCarousel = ({ images }) => {
  return (
    <div className="relative">
      <Carousel>
        <CarouselContent>
          {images.map((image, index) => (
            <CarouselItem key={index}>
              <div className="p-1">
                <Card>
                  <CardContent className="flex items-center aspect-square justify-center p-6">
                    <div className="relative w-full h-[100%]">
                      <Image
                        src={image.url}
                        layout="fill"
                        alt={`Image ${index}`}
                        className="rounded-lg object-contain"
                      />
                    </div>
                  </CardContent>
                </Card>
              </div>
            </CarouselItem>
          ))}
        </CarouselContent>
        <CarouselPrevious className="absolute top-1/2 transform -translate-y-1/2 left-2" />
        <CarouselNext className="absolute top-1/2 transform -translate-y-1/2 right-2" />
      </Carousel>
    </div>
  );
};

export default ImageCarousel;
