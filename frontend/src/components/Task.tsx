import { Message } from "semantic-ui-react"
import { TaskModel } from "../model/TaskModel";

export default function Task(props:
  {
    task: TaskModel,
    deleteTask: (task: TaskModel) => Promise<void>
  }
) { 
  return (
    <Message>
    <Message.Header>{ props.task.name }</Message.Header>
    <p>{ props.task.description }</p>
    { props.task.tags.length !== 0 &&
      <p><b>Tags</b>: { props.task.tags.map(task => task.name).join(", ") }</p>
    }
    <i className="fs-15">Saved on { new Date(props.task.timestamp).toDateString() } by <b>{ props.task.username }</b></i>
    <img
      className="delete-icon"
      src="/delete.png"
      alt="delete.png"
      height="18px"
      title="Delete task"
      onClick={ () => props.deleteTask(props.task) } 
    />
  </Message>
  );
}
