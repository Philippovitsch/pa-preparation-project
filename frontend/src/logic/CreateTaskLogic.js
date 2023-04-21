import { useState } from "react";
import { saveNewTask } from "../functions/fetch";
import { useNavigate } from "react-router-dom";

const useCreateTask = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  
  const navigate = useNavigate();

  const saveTask = async () => {
    const newTask = {
      name: name,
      description: description,
      timestamp: new Date().toISOString()
    };

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setSuccessMessage("Successfully saved card!");
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Error while saving card");
    }
  };

  return [{setName, setDescription, saveTask, successMessage}];
};

export default useCreateTask;
