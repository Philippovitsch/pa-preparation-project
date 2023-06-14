import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Buffer } from 'buffer';
import { getBearerToken } from "../functions/fetch";
import { UserMessage } from "../model/UserMessage";

const useLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userMessage, setUserMessage] = useState<UserMessage>();

  const navigate = useNavigate();

  const performLogin = async (setUser: (user: string | null) => void) => {
    if (username === "" || password === "") {
      setUserMessage({level: "error", text: "Username and/ or Password cannot be empty!"});
      return;
    }

    const token = `${username}:${password}`;
    const encodedToken = Buffer.from(token).toString('base64');
    const response = await getBearerToken(encodedToken);

    if (response.status === 200) {
      localStorage.setItem("bearerToken", response.data.token);
      localStorage.setItem("user", username);
      localStorage.setItem("isAdmin", response.data.roles.includes("ADMIN"));
      setUser(username);
      setUserMessage({level: "success", text: "Login successful!"});
      setTimeout(() => navigate("/"), 1500);
    } else {
      setUserMessage({level: "error", text: "Login failed!"});
    }
  };

  return [{setUsername, setPassword, performLogin, userMessage}];
};

export default useLogin;
