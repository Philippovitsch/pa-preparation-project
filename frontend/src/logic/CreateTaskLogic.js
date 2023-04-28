import { useEffect, useState } from "react";
import { getAllTags, saveNewTask } from "../functions/fetch";
import { useNavigate } from "react-router-dom";

const useCreateTask = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [tags, setTags] = useState([]);
  
  useEffect(() => {
    const fetchTags = async () => {
      const response = await getAllTags();
      
      if (response.status === 200) {
        setTags(response.data);
      }
    };
    fetchTags();
  }, [])

  const navigate = useNavigate();

  const saveTask = async () => {
    const checkedTags = document.querySelectorAll('input[type=checkbox]:checked');
    const tags = [];
    checkedTags.forEach(tag => {
      tags.push({
        id: tag.getAttribute("data-id"),
        name: tag.getAttribute("data-name") 
      })
    })
    const newTask = {
      name: name,
      description: description,
      timestamp: new Date().toISOString(),
      tags: tags
    };

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setSuccessMessage("Successfully saved card!");
      setTimeout(() => navigate("/"), 1500);
    } else {
      setSuccessMessage("Error while saving card");
    }
  };

  return [{setName, setDescription, saveTask, tags, successMessage}];
};

export default useCreateTask;
