import { useEffect, useState } from "react";
import { getAllTags, saveNewTask } from "../functions/fetch";
import { useNavigate } from "react-router-dom";
import { TagModel } from "../model/TagModel";
import { TaskModel } from "../model/TaskModel";
import { UserMessage } from "../model/UserMessage";

const useCreateTask = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [userMessage, setUserMessage] = useState<UserMessage>();
  const [tags, setTags] = useState<TagModel[]>([]);
  
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
    if (name === "" || description === "") {
      setUserMessage({level: "error", text: "Please enter a name and a description!"});
      return;
    }

    const checkedTags = document.querySelectorAll(".checked");
    
    const tags: TagModel[] = [];
    checkedTags.forEach(tag => {
      const id = tag.getAttribute("data-id");
      const name = tag.getAttribute("data-name");
      const newTag: TagModel = {
        id: (id !== null) ? parseInt(id) : -1,
        name:  (name !== null) ? name : ""
      }
      tags.push(newTag)
    })
    const newTask: TaskModel = {
      username: <string> localStorage.getItem("username"),
      name: name,
      description: description,
      timestamp: new Date().toISOString(),
      tags: tags
    };

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setUserMessage({level: "success", text: "Successfully saved card!"});
      setTimeout(() => navigate("/"), 1500);
    } else {
      setUserMessage({level: "error", text: "Error while saving card"});
    }
  };

  return [{setName, setDescription, saveTask, tags, userMessage}];
};

export default useCreateTask;
