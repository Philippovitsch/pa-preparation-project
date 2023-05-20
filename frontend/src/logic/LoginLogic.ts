import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Buffer } from 'buffer';
import { getBearerToken } from "../functions/fetch";

const useLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();

  const performLogin = async (setUser: (user: string | null) => void) => {
    const token = `${username}:${password}`;
    const encodedToken = Buffer.from(token).toString('base64');
    const response = await getBearerToken(encodedToken);

    if (response.status === 200) {
      localStorage.setItem("bearerToken", response.data.token);
      localStorage.setItem("user", username);
      setUser(username);
      setSuccessMessage("Login successful!");
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Login failed!")
    }
  };

  return [{setUsername, setPassword, performLogin, successMessage}];
};

export default useLogin;
