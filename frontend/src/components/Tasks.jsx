import Task from "./Task";
import taskLogic from "../logic/TaskLogic";

export default function Tasks() {
  const [{ tasks, deleteTask }] = taskLogic();

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
