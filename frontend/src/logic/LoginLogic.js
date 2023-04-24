import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getBearerToken } from "../functions/fetch";

const useLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();

  const performLogin = async () => {
    const loginData = {
      username: username,
      password: password
    };

    const response = await getBearerToken(loginData);

    if (response.status === 200) {
      setSuccessMessage("Successfully logged in!");
      localStorage.setItem("bearerToken", response.data);
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Could not log in!");
    }
  };

  return [{setUsername, setPassword, performLogin, successMessage}];
};

export default useLogin;
