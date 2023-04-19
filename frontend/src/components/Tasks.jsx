import { useEffect, useState } from "react";
import { deleteTaskByName, getAllTasks } from "../functions/fetch";
import Task from "./Task";

export default function Tasks() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    getTasks();
  }, []);

  const getTasks = async () => {
    const tasks = await getAllTasks();
    setTasks(tasks);
  };

  const deleteTask = async (task) => {
    const name = task.name;
    const response = await deleteTaskByName(name);
    if (response.status === 200) {
      const updatedTasks = tasks.filter(element => element.name !== task.name);
      setTasks(updatedTasks);
    }
  };

  return (
    <div className="content">
      <h2><center>Saved Tasks:</center></h2>
      {
        tasks.map(task => (
          <Task key={ task.id } task={ task } deleteTask={ deleteTask } />
        ))
      }
    </div>
  );
}
