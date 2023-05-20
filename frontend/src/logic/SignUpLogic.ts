import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signUp } from "../functions/fetch";
import { UserDataModel } from "../model/UserDataModel";

const useSignUp = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();

  const performSignUp = async () => {
    const userData: UserDataModel = {
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
