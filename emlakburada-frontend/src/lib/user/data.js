export const getUser = async (userId) => {
  try {
    const res = await fetch(
      `http://localhost:8080/emlakburada/api/v1/user/${userId}`
    );

    if (!res.ok) {
      throw new Error("Failed to fetch user");
    }

    const user = await res.json();
    console.log(user);

    return user;
  } catch (error) {
    console.error("Error fetching user:", error);
  }
};
