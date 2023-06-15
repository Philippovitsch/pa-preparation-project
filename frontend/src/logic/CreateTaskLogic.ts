import { useEffect, useState } from "react";
import { getAllTags, saveNewTask } from "../functions/fetch";
import { useNavigate } from "react-router-dom";
import { TagModel } from "../model/TagModel";
import { UserMessage } from "../model/UserMessage";

const useCreateTask = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [userMessage, setUserMessage] = useState<UserMessage>();
  const [tags, setTags] = useState<TagModel[]>([]);
  const [image, setImage] = useState<File>();
  
  useEffect(() => {
    const fetchTags = async () => {
      const response = await getAllTags();
      
      if (response.status === 200) {
        setTags(response.data);
      }
    };
    fetchTags();
  }, [])

  const handleImageUpload = (files: FileList | null) => {
    if (files !== null && files[0].type.includes("image/")) {
      setUserMessage(undefined);
      setImage(files[0]);
    } else {
      const userMessage: UserMessage = {
        level: "error",
        text: "You can't upload this file format!"
      }
      setUserMessage(userMessage);
    }
  };

  const navigate = useNavigate();

  const saveTask = async () => {
    if (name === "" || description === "") {
      setUserMessage({level: "error", text: "Please enter a name and a description!"});
      return;
    }

    const checkedTags = document.querySelectorAll(".checked");
    
    const tags: String[] = [];
    checkedTags.forEach(tag => tags.push(<string> tag.getAttribute("data-name")));

    const newTask = new FormData();
    newTask.append("user",  <string> localStorage.getItem("user"));
    newTask.append("name", name);
    newTask.append("description", description);
    // @ts-ignore
    newTask.append("timestamp", new Date().getTime());
    // @ts-ignore
    newTask.append("tags", tags);
    // @ts-ignore
    newTask.append("image", image);

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setUserMessage({level: "success", text: "Successfully saved card!"});
      setTimeout(() => navigate("/"), 1500);
    } else {
      setUserMessage({level: "error", text: "Error while saving card"});
    }
  };

  return [{setName, setDescription, handleImageUpload, saveTask, tags, userMessage}];
};

export default useCreateTask;
