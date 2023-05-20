import Task from "./Task";
import useTasks from "../logic/TaskLogic";

export default function Tasks() {
  const [{ tasks, deleteTask }] = useTasks();

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
