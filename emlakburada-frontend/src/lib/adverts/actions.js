"use server";
import { revalidatePath, revalidateTag } from "next/cache";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { toast } from "react-toastify";

export const deleteAdvert = async (id, token) => {
  try {
    const res = await fetch(
      `http://localhost:8080/emlakburada/api/v1/adverts/delete/${id}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
      }
    );

    if (!res.ok) {
      const errorData = await res.json();
      throw new Error(errorData.message || "Failed to delete advert");
    }
    const data = await res.json();
    console.log("Advert deleted:", data);
  } catch (error) {
    console.error("Error during deleting:", error);
    throw error;
  }

  revalidatePath("/user/profile");
  revalidatePath("/");
};

export const updateAdvert = async (formData) => {
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value || "";
  const {
    id,
    city,
    district,
    homeType,
    advertType,
    title,
    description,
    numberOfRooms,
    numberOfBath,
    squareMeters,
    price,
    images,
    advertStatus,
  } = Object.fromEntries(formData);
  try {
    const payload = {
      city,
      district,
      homeType,
      advertType,
      title,
      description,
      numberOfRooms,
      numberOfBath,
      squareMeters,
      price,
      images: images.split(",").map((url) => url.trim()),
    };
    const response = await fetch(
      `http://localhost:8080/emlakburada/api/v1/adverts/update/${id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
        body: JSON.stringify(payload),
      }
    );
    const responseData = await response.json(); // Parse response JSON

    if (!response.ok) {
      throw new Error(responseData.message || "Update unsuccessful!");
    }
    console.log("Form updated successfully!");

    //İkinci istek: Statu değişikliğini güncelle
    let statusEndpoint;
    if (advertStatus === "ACTIVE") {
      statusEndpoint = `http://localhost:8080/emlakburada/api/v1/adverts/update-status-active/${id}`;
    } else if (advertStatus === "PASSIVE") {
      statusEndpoint = `http://localhost:8080/emlakburada/api/v1/adverts/update-status-passive/${id}`;
    }

    const statusResponse = await fetch(statusEndpoint, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
      },
    });
    if (!statusResponse.ok) {
      throw new Error("Status updating unsuccessfull!");
    }

    console.log("Status updated!");
  } catch (error) {
    console.error("Update error:", error);
  }

  revalidatePath("/user/profile");
  revalidatePath(`/advert/${id}`);
};

export const saveAdvert = async (formData) => {
  const {
    city,
    district,
    homeType,
    advertType,
    title,
    description,
    numberOfRooms,
    numberOfBath,
    squareMeters,
    price,
    images,
  } = Object.fromEntries(formData);
  const cookieStore = cookies();
  const token = cookieStore.get("token")?.value || "";
  const userId = cookieStore.get("userId")?.value || "";
  try {
    const payload = {
      userId,
      city,
      district,
      homeType,
      advertType,
      title,
      description,
      numberOfRooms,
      numberOfBath,
      squareMeters,
      price,
      images: images.split(",").map((url) => url.trim()),
    };
    const res = await fetch(
      `http://localhost:8080/emlakburada/api/v1/adverts`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
        body: JSON.stringify(payload),
      }
    );

    if (!res.ok) {
      const errorData = await res.json();
      throw new Error(errorData.message || "Failed to add advert");
    }
    const data = await res.json();
    console.log("Advert added:", data);
  } catch (error) {
    console.error("Error during adding:", error);
    throw error;
  }

  revalidatePath("/");
  revalidatePath("/user/profile");
};
