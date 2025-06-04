import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const [username, setUsername] = useState("");
  const [services, setServices] = useState([]);
  const [adding, setAdding] = useState(false);
  const [newService, setNewService] = useState({ serviceName: "",userName: "", password: "" });
  const navigate = useNavigate();

  const logoutAndRedirect = () => {
    localStorage.removeItem("jwt");
    localStorage.removeItem("username");
    navigate("/login");
  };

  const fetchWithAuth = async (url, options = {}) => {
    const jwt = localStorage.getItem("jwt");
    console.log("final url ::"+url)
    const res = await fetch(url, {
      ...options,
      headers: {
        ...(options.headers || {}),
        Authorization: `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
    });

    if (res.status === 401) {
      logoutAndRedirect();
      throw new Error("Session expired");
    }

    return res;
  };

  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    const name = localStorage.getItem("username");

    if (!jwt) {
      navigate("/login");
      return;
    }

    setUsername(name || "User");

    fetchWithAuth("http://localhost:8080/api/v1/passwords",{})
      .then((res) => res.json())
      .then(setServices)
      .catch((err) => console.error("Error fetching services", err));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewService((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = async () => {
    const { serviceName,userName, password } = newService;
    if (!serviceName || !userName || !password) {
      alert("Please fill in all fields");
      return;
    }

    try {
      const res = await fetchWithAuth("http://localhost:8080/api/v1/passwords", {
        method: "POST",
        body: JSON.stringify(newService),
      });

      if (!res.ok) {
        const errorData = await res.json();
        alert("Error saving service: " + (errorData.message || res.statusText));
        return;
      }

      const saved = await res.json();
      setServices((prev) => [...prev, saved]);
      setNewService({ service: "", password: "" });
      setAdding(false);
    } catch (error) {
      alert("Network error: " + error.message);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <header className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Password Manager</h1>
        <div className="flex items-center gap-4">
          <span className="text-gray-600 font-medium">{username}</span>
          <button
            onClick={logoutAndRedirect}
            className="bg-red-500 text-white px-4 py-2 rounded-xl hover:bg-red-600 transition"
          >
            Logout
          </button>
        </div>
      </header>

      <div className="bg-white rounded-2xl shadow-md p-6 mb-6">
        <h2 className="text-lg font-semibold text-gray-700 mb-4">Your Services</h2>

        <table className="w-full text-left border-collapse mb-4">
          <thead>
            <tr>
              <th className="border-b p-2">Service Name</th>
              <th className="border-b p-2">User ID</th>
              <th className="border-b p-2">Password</th>
            </tr>
          </thead>
          <tbody>
            {services.map((item, index) => (
              <tr key={index} className="hover:bg-gray-50">
                <td className="border-b p-2">{item.serviceName}</td>
                <td className="border-b p-2">{item.userName}</td>
                <td className="border-b p-2">{item.password}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {!adding ? (
          <button
            onClick={() => setAdding(true)}
            className="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700 transition"
          >
            Add Service
          </button>
        ) : (
          <div className="mt-4 space-y-4 max-w-md">
            <input
              type="text"
              name="serviceName"
              value={newService.serviceName}
              onChange={handleChange}
              placeholder="Service Name"
              className="w-full p-2 border rounded"
              aria-label="Service Name"
            />
            <input
              type="text"
              name="userName"
              value={newService.userName}
              onChange={handleChange}
              placeholder="User Name"
              className="w-full p-2 border rounded"
              aria-label="User Name"
            />
            <input
              type="password"
              name="password"
              value={newService.password}
              onChange={handleChange}
              placeholder="Password"
              className="w-full p-2 border rounded"
              aria-label="Password"
            />
            <div className="flex gap-4">
              <button
                onClick={handleSave}
                className="bg-green-600 text-white px-4 py-2 rounded-xl hover:bg-green-700 transition"
              >
                Save
              </button>
              <button
                onClick={() => {
                  setAdding(false);
                  setNewService({ serviceName: "", userName: "", password: "" });
                }}
                className="bg-gray-300 px-4 py-2 rounded-xl hover:bg-gray-400 transition"
              >
                Cancel

                
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
