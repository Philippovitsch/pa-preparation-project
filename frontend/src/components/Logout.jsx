import { useEffect } from "react"
import { useNavigate } from "react-router-dom";

export default function Logout() {
  const navigate = useNavigate();
  const performLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    setTimeout(() => navigate("/"), 1500);
  };

  useEffect(() => {
    performLogout();
  }, []);
  
  return (
    <div className="content">
      <center>
        <h2>Logout:</h2>
        Performing logout...
      </center>
    </div>
  );
}
