import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const logoutLogic = () => {
  const navigate = useNavigate();
  const performLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    setTimeout(() => navigate("/"), 1500);
  };

  useEffect(() => {
    performLogout();
  }, []);
  
  return [];
}

export default logoutLogic;
