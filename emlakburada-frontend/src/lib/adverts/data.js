export const getAllAdverts = async () => {
  try {
    const res = await fetch(
      `http://localhost:8080/emlakburada/api/v1/adverts`,
      {
        next: {
          revalidate: 5,
        },
      }
    );

    if (!res.ok) {
      throw new Error("Failed to fetch adverts");
    }

    const data = await res.json();
    console.log(data);

    return data;
  } catch (error) {
    console.error("Error fetching adverts:", error);
  }
};

export const getAdvertById = async (id) => {
  try {
    const res = await fetch(
      `http://localhost:8080/emlakburada/api/v1/adverts/${id}`,
      {
        next: {
          revalidate: 5,
        },
      }
    );

    if (!res.ok) {
      throw new Error("Failed to fetch adverts");
    }

    const data = await res.json();
    console.log(data);

    return data;
  } catch (error) {
    console.error("Error fetching adverts:", error);
  }
};

export const getAdvertByUserId = async (userId) => {
  try {
    const res = await fetch(
      `
            http://localhost:8080/emlakburada/api/v1/adverts/find-advert-byUserId/${userId}`,
      {
        next: {
          revalidate: 5,
        },
      }
    );

    if (!res.ok) {
      throw new Error("Failed to fetch adverts");
    }

    const data = await res.json();

    return data;
  } catch (error) {
    console.error("Error fetching adverts:", error);
  }
};
