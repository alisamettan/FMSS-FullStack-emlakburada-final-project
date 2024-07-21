import Link from "next/link";
import LoginForm from "@/components/login/loginForm";
import { FaArrowRight } from "react-icons/fa";

export default function LoginPage() {
  return (
    <div className="relative flex items-center justify-center h-screen bg-gray-100">
      <header className="absolute top-4 right-4">
        <Link href="/">
          <button className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 flex items-center gap-2">
            <p>Homepage</p>
            <FaArrowRight />
          </button>
        </Link>
      </header>
      <LoginForm />
    </div>
  );
}
