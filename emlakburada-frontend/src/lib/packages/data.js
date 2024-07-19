export const getPackages = async () => {
  try {
    const res = await fetch(
      "http://localhost:8080/emlakburada/api/v1/packages"
    );

    if (!res.ok) {
      throw new Error("Error fetching packages!");
    }

    const data = res.json();

    return data;
  } catch (error) {
    console.log(error.message);
  }
};
