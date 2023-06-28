import { useEffect, useState } from "react";
import {deleteTaskByName, getAllTasks, getTasksByUsername} from "../functions/fetch";
import { TaskResponseModel } from "../model/TaskResponseModel.ts";

const useTasks = () => {
  const [tasks, setTasks] = useState<TaskResponseModel[]>([]);

  useEffect(() => {
    getTasks();
  }, []);

  const getTasks = async () => {
    const response = await getAllTasks();
    if (response.status === 200) {
      setTasks(response.data);
    }
  };

  const getUserTasks = async () => {
    const response = await getTasksByUsername();
    if (response.status === 200) {
      setTasks((response.data));
    }
  }

  const triggerUserTasks = (triggerUserTasks = false) => {
    if (triggerUserTasks) {
      getUserTasks();
    } else {
      getTasks();
    }
  };

  const deleteTask = async (task: TaskResponseModel) => {
    const name = task.name;
    const response = await deleteTaskByName(name);
    if (response.status === 200) {
      const updatedTasks = tasks.filter(element => element.name !== task.name);
      setTasks(updatedTasks);
    }
  };

  return [{ tasks, triggerUserTasks, getTasks, deleteTask }];
};

export default useTasks;
