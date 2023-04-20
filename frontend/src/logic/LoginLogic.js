import { useState } from "react";
import { useNavigate } from "react-router-dom";

const loginLogic = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const performLogin = () => {
    localStorage.setItem("username", username);
    localStorage.setItem("password", password);
    navigate("/");
  };

  return [{setUsername, setPassword, performLogin}];
};

export default loginLogic;
