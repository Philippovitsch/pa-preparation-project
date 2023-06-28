import {Dispatch, SetStateAction, useState} from "react";
import { getAllTags } from "../functions/fetch";
import { useNavigate } from "react-router-dom";
import { TagModel } from "../model/TagModel";
import { UserMessage } from "../model/UserMessage";
import { TaskResponseModel } from "../model/TaskResponseModel.ts";

const useTaskForm = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [userMessage, setUserMessage] = useState<UserMessage>();
  const [tags, setTags] = useState<TagModel[]>([]);
  const [image, setImage] = useState<File>();
  const [originalTaskName, setOriginalTaskName] = useState("");

  const fetchTags = async () => {
    const response = await getAllTags();

    if (response.status === 200) {
      setTags(response.data);
    }
  };

  const setDefaultValues = async (task?: TaskResponseModel) => {
    if (task && tags.length > 0) {
      const taskName = document.querySelector("#task-name")as HTMLInputElement;
      taskName.value = task.name;
      setOriginalTaskName(task.name);
      setName(task.name);

      const taskDescription = document.querySelector("#task-description") as HTMLInputElement;
      taskDescription.value = task.description;
      setDescription(task.description);

      const res: Response = await fetch("data:" + task.imageType + ";base64, " + task.imageData);
      const blob: Blob = await res.blob();
      const file: File = new File([blob], task.imageName, {type: task.imageType});
      const dataTransfer: DataTransfer = new DataTransfer()
      dataTransfer.items.add(file)
      handleImageUpload(dataTransfer.files)
    }
  }

  const handleImageUpload = (files: FileList | null) => {
    if (files === null || files.length === 0) {
      const userMessage: UserMessage = {
        level: "error",
        text: "You can't upload this file format!"
      }
      setUserMessage(userMessage);
      setImage(undefined);
    } else if (files[0].type.includes("image/")) {
      setUserMessage(undefined);
      setImagePreview(files[0]);
      setImage(files[0]);
    }
  };

  const setImagePreview = (image: File) => {
    const reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onload = function() {
      const imageElement = <HTMLImageElement> document.querySelector("#image-preview");
      imageElement.src = (image !== null) ? <string> reader.result : imageElement.src = "";
    }
  };

  const navigate = useNavigate();

  const saveTask = async (saveNewTask: Function, getTasks?: Function, setOpenModal?: Dispatch<SetStateAction<boolean>>) => {
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

    const response = await saveNewTask(newTask, originalTaskName);

    if (response.status == 200) {
      setUserMessage({level: "success", text: "Successfully saved card!"});
      setTimeout(() => {
        if (window.location.pathname === "/new-task") {
          navigate("/")
        } else {
          getTasks && getTasks();
          setOpenModal && setOpenModal(false);
        }
      }, 1500);
    } else {
      setUserMessage({level: "error", text: "Error while saving card"});
    }
  };

  const isChecked = (tag: TagModel, tags: TagModel[] | undefined) => {
    if (tags === undefined) {
      return false;
    }

    return tags.filter(currentTag => currentTag.id === tag.id && currentTag.name === tag.name).length > 0;
  }

  return [{
    setName,
    setDescription,
    handleImageUpload,
    saveTask,
    fetchTags,
    isChecked,
    setDefaultValues,
    image,
    tags,
    userMessage
  }];
};

export default useTaskForm;
