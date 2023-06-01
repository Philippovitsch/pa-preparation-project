import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signUp } from "../functions/fetch";
import { UserDataModel } from "../model/UserDataModel";
import { UserMessage } from "../model/UserMessage";

const useSignUp = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userMessage, setUserMessage] = useState<UserMessage>();

  const navigate = useNavigate();

  const performSignUp = async () => {
    if (username === "" || password === "") {
      setUserMessage({level: "error", text: "Username and/ or Password cannot be empty!"});
      return;
    }
    
    const checkbox = <HTMLInputElement> document.querySelector("#tos");
    if (!checkbox.checked) {
      setUserMessage({level: "error", text: "You have to accept the Terms and Conditions!"});
      return;
    }

    const userData: UserDataModel = {
        username: username,
        password: password
    }
    const response = await signUp(userData);

    if (response.status === 200) {
      setUserMessage({level: "success", text: "Sign up successful!"});
      setTimeout(() => navigate("/"), 1500);
    } else {
      setUserMessage({level: "error", text: "Sign up failed!"});
    }
  };

  return [{setUsername, setPassword, performSignUp, userMessage}];
};

export default useSignUp;
