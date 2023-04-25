import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const useLogout = () => {
  const navigate = useNavigate();
  const performLogout = () => {
    localStorage.removeItem("bearerToken");
    setTimeout(() => navigate("/"), 1500);
  };

  useEffect(() => {
    performLogout();
  }, []);
  
  return [];
}

export default useLogout;
