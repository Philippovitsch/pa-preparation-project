import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const useLogout = (setUser: (user: string | null) => void) => {
  const navigate = useNavigate();
  const performLogout = () => {
    localStorage.removeItem("bearerToken");
    localStorage.removeItem("user");
    localStorage.removeItem("isAdmin");
    setUser(null);
    setTimeout(() => navigate("/"), 1500);
  };

  useEffect(() => {
    performLogout();
  }, []);
}

export default useLogout;
