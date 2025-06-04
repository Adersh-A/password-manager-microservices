import { useNavigate } from "react-router-dom";
import { useEffect } from "react";


const LoginPage = () => { 
  const navigate = useNavigate();
  useEffect(() => {
    window.google.accounts.id.initialize({
      client_id: "916609941802-vnoruvntot40m1m100p8b0724dnj9k57.apps.googleusercontent.com",
      callback: handleCredentialResponse,
    });

    window.google.accounts.id.renderButton(
      document.getElementById("google-login-btn"),
      { theme: "outline", size: "medium", width: "200",shape: "pill",text: "signin",logo_alignment: "center" }
    );
  }, []);

  const handleCredentialResponse = async (response) => {
    console.log(response)
    console.log(response.credential)
    const idToken = response.credential;

    try {
      const res = await fetch("http://localhost:8080/api/v1/authservice", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idToken }),
      });

      const data = await res.json();

      if (res.ok) {
        localStorage.setItem("jwt", data.jwt);
        console.log("Login success!");
        localStorage.setItem("username", data.userName);
        navigate("/home");
      } else {
        console.error("Authentication failed", data);
      }
    } catch (error) {
      navigate("/error",{
    state: { status: 500, message: "Something went wrong while logging in.", buttonTxt : "Login" },
  })
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-100 to-gray-300 px-4">
      <div className="bg-white p-8 rounded-3xl shadow-xl max-w-md w-full text-center">
        <h1 className="text-2xl font-bold text-gray-800 mb-6">Password Manager</h1>
        <p className="text-gray-500 text-sm mb-8">
          Sign in to continue to your account
        </p>
        <div id="google-login-btn" className="flex justify-center w-full mb-4"></div> 
      </div>
    </div>
  );
};

export default LoginPage;
