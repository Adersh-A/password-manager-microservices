import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

const ErrorPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
console.log(location.state);
  const { status = 404, message = "Something went wrong", buttonTxt = "Home"} = location.state || {};

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-6">
      <h1 className="text-6xl font-extrabold text-red-600 mb-4">{status}</h1>
      <p className="text-xl text-gray-700 mb-6">{message}</p>
      <button
        onClick={() => navigate("/")}
        className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition"
      >
        {buttonTxt}
      </button>
    </div>
  );
};

export default ErrorPage;
