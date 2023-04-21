import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Buffer } from 'buffer';

const useLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const performLogin = () => {
    const token = `${username}:${password}`;
    const encodedToken = Buffer.from(token).toString('base64');
    localStorage.setItem("encodedToken", encodedToken);
    navigate("/");
  };

  return [{setUsername, setPassword, performLogin}];
};

export default useLogin;
