import Adverts from "@/components/adverts";
import Header from "@/components/header";
import Loading from "@/components/loading";
import Search from "@/components/search";
import { Suspense } from "react";

export default function Home() {
  return (
    <div>
      <Header />
      <Search />
      <Suspense fallback={<Loading />}>
        <Adverts />
      </Suspense>
    </div>
  );
}
