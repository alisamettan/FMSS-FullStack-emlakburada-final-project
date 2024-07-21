"use client";

import { login } from "@/lib/login/actions";
import Link from "next/link";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm({
    defaultValues: {
      email: "",
      password: "",
    },
    mode: "all",
  });

  const onSubmit = async (formData) => {
    try {
      await login(formData);
    } catch (error) {
      console.error("Login failed:", error.message);
      console.log(error.message);
      toast.error(error.message, {
        position: "top-right",
        autoClose: 1500,
        hideProgressBar: false,
        closeOnClick: true,
        draggable: true,
        theme: "light",
      });
    }
  };

  return (
    <div className="w-full max-w-md p-8 bg-white rounded shadow-md">
      <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-6">
        <div className="flex flex-col">
          <label className="text-xl text-gray-700">Email</label>
          <input
            className={`h-12 rounded-md px-4 mt-2 border ${
              errors.email ? "border-red-500" : "border-gray-300"
            }`}
            type="email"
            {...register("email", {
              required: "Email field is required!",
              pattern: {
                value: /^[\w\-\.]+@([\w-]+\.)+[\w-]{2,}$/gm,
                message: "Please enter a valid email address.",
              },
            })}
          />
          {errors.email && (
            <p className="text-sm text-red-700 mt-1">{errors.email.message}</p>
          )}
        </div>
        <div className="flex flex-col">
          <label className="text-xl text-gray-700">Password</label>
          <input
            className={`h-12 rounded-md px-4 mt-2 border ${
              errors.password ? "border-red-500" : "border-gray-300"
            }`}
            type="password"
            {...register("password", {
              required: "Password field is required!",
            })}
          />
          {errors.password && (
            <p className="text-sm text-red-700 mt-1">
              {errors.password.message}
            </p>
          )}
        </div>
        <div className="pt-3 text-center">
          <button
            type="submit"
            color="primary"
            className="px-10 py-3 bg-blue-500 rounded-lg text-white disabled:opacity-50"
            disabled={!isValid}
          >
            Sign in
          </button>
        </div>
        <div className="text-center">
          <p className="text-gray-700">
            Don't have an account?{" "}
            <Link href="/signup" className="text-blue-300 hover:text-blue-500">
              Sign Up
            </Link>
          </p>
        </div>
      </form>
    </div>
  );
}
