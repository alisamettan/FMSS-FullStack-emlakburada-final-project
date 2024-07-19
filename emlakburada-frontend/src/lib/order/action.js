"use server";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

export const purchase = async (formData) => {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value || "";
  const userId = cookieStore.get("userId")?.value || "";

  const payload = {
    userId: Number(userId),
    packageId: Number(formData.packageId),
    cardNumber: formData.cardNumber,
    expiration: formData.expiration,
    cvc: formData.cvc,
  };
  try {
    console.log("Payload:", payload);

    const res = await fetch("http://localhost:8080/emlakburada/api/v1/orders", {
      method: "POST",
      headers: {
        Authorization: token,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      throw new Error(res.message || "Purchase unsuccessful!");
    }

    const data = await res.json();
    console.log("Purchased successfully!", data);
  } catch (error) {
    console.log("Purchase error", error.message);
  }
  revalidatePath("/user/profile");
};
