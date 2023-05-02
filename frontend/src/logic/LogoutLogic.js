import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const useLogout = (setUser) => {
  const navigate = useNavigate();
  const performLogout = () => {
    localStorage.removeItem("bearerToken");
    localStorage.removeItem("user");
    setUser(null);
    setTimeout(() => navigate("/"), 1500);
  };

  useEffect(() => {
    performLogout();
  }, []);
  
  return [];
}

export default useLogout;
