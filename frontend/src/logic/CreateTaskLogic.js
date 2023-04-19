import { useState } from "react";
import { saveNewTask } from "../functions/fetch";

const createTaskLogic = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const saveTask = async () => {
    const newTask = {
      name: name,
      description: description,
      timestamp: new Date().toISOString()
    };

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setSuccessMessage("Successfully saved card!");
    } else {
      setSuccessMessage("Error while saving card");
    }
  };

  return [{setName, setDescription, saveTask, successMessage}];
};

export default createTaskLogic;
