import { useEffect, useState } from "react";
import {deleteTaskByName, getAllTasks, getTasksByUsername} from "../functions/fetch";
import { TaskModel } from "../model/TaskModel";

const useTasks = () => {
  const [tasks, setTasks] = useState<TaskModel[]>([]);

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

  const deleteTask = async (task: TaskModel) => {
    const name = task.name;
    const response = await deleteTaskByName(name);
    if (response.status === 200) {
      const updatedTasks = tasks.filter(element => element.name !== task.name);
      setTasks(updatedTasks);
    }
  };

  return [{ tasks, triggerUserTasks, deleteTask }];
};

export default useTasks;
