"use server";
import Cookies from "js-cookie";
import { revalidatePath, revalidateTag } from "next/cache";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export const login = async (formData) => {
  //Burada cachleme mekanizması kullanılıyor ve kullanıcı server a ihtiyaç olmadan da alınıyor

  try {
    const res = await fetch(
      "http://localhost:8080/emlakburada/api/v1/auth/login",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      }
    );

    if (!res.ok) {
      const errorData = await res.json();
      throw new Error(errorData.message || "Login failed");
    }
    const data = await res.json();
    const { userId, token } = data;
    console.log(data);

    const cookieStore = cookies();
    cookieStore.set("token", token, {
      httpOnly: true,
      secure: true,
      path: "/",
    });
    cookieStore.set("userId", userId, {
      httpOnly: true,
      secure: true,
      path: "/",
    });
  } catch (error) {
    console.log("Error during login:", error);
    throw error;
  }

  revalidateTag("/");
  redirect("/");
};

export async function logout() {
  // Clear the cookies
  cookies().set("token", "", { maxAge: -1, path: "/" });
  cookies().set("userId", "", { maxAge: -1, path: "/" });

  // Redirect to login page
  redirect("/login");
}
