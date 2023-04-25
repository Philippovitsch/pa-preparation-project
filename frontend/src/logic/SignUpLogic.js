import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Buffer } from 'buffer';
import { getBearerToken, signUp } from "../functions/fetch";

const useSignUp = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();

  const performSignUp = async () => {
    const userData = {
        username: username,
        password: password
    }
    const response = await signUp(userData);

    if (response.status === 200) {
      setSuccessMessage("Sign up successful!")
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Sign up failed!")
    }
  };

  return [{setUsername, setPassword, performSignUp, successMessage}];
};

export default useSignUp;
