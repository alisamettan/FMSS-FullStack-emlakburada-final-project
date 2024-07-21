"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Error({ error, reset }) {
  const router = useRouter();

  useEffect(() => {
    // Log the error to an error reporting service
    console.error(error);
  }, [error]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="bg-white p-6 rounded-lg shadow-md text-center">
        <h2 className="text-2xl font-semibold mb-4 text-red-600">
          Something went wrong!
        </h2>
        <p className="mb-4 text-gray-700">
          An error occurred. Please try again or go back to the previous page.
        </p>
        <p className="mb-4 text-sm text-red-500">{error.message}</p>
        <div className="flex space-x-4">
          <button
            onClick={() => reset()}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300"
          >
            Try Again
          </button>
          <button
            onClick={() => router.back()}
            className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-300"
          >
            Go Back
          </button>
        </div>
      </div>
    </div>
  );
}
