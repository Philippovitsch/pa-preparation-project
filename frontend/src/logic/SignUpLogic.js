import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signUp } from "../functions/fetch";

const useSignUp = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const navigate = useNavigate();

  const performSignUp = async () => {
    const signUpData = {
      username: username,
      password: password
    };

    const responseStatus = await signUp(signUpData);

    if (responseStatus == 200) {
      setSuccessMessage("Successfully saved user!");
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Error while saving user!");
    }
  };

  return [{setUsername, setPassword, performSignUp, successMessage}];
}

export default useSignUp;
