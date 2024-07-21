export const signup = async (formData) => {
  try {
    const res = await fetch(
      "http://localhost:8080/emlakburada/api/v1/auth/signup",
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(formData),
      }
    );

    if (!res.ok) {
      const errorData = await res.json();
      throw new Error(errorData.message || "Signup failed");
    }

    const data = await res.json();

    console.log(data);
  } catch (error) {
    console.log("Error during signup:", error);
    throw error;
  }
};
