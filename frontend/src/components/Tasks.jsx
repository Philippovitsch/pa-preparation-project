import { useEffect, useState } from "react";
import { getAllTasks } from "../functions/fetch";
import Task from "./Task";

export default function Tasks() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const fetchTasks = async () => {
      const tasks = await getAllTasks();
      setTasks(tasks);
    };
    fetchTasks();
  }, []);

  return (
    <div className="content">
      <h2><center>Saved Tasks:</center></h2>
      {
        tasks.map(task => (
          <Task key={ task.id } task={ task } />
        ))
      }
    </div>
  );
}
