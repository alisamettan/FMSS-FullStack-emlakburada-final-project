"use client";
import { signup } from "@/lib/signup/actions";
import { useRouter } from "next/navigation";
import React from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const SignupForm = () => {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm({
    defaultValues: {
      fullName: "",
      email: "",
      password: "",
    },
    mode: "all",
  });

  const onSubmit = async (formData) => {
    try {
      await signup(formData);
      router.push("/login");
    } catch (error) {
      console.error("Signup failed:", error.message);
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
          <label className="text-xl text-gray-700">Full Name</label>
          <input
            className={`h-12 rounded-md px-4 mt-2 border ${
              errors.fullName ? "border-red-500" : "border-gray-300"
            }`}
            type="text"
            {...register("fullName", {
              required: "Name field is required!",
            })}
          />
          {errors.fullName && (
            <p className="text-sm text-red-700 mt-1">
              {errors.fullName.message}
            </p>
          )}
        </div>
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
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default SignupForm;
