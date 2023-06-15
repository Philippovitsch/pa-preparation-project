import { Message } from "semantic-ui-react"
import { TaskResponseModel } from "../model/TaskResponseModel.ts";

export default function Task(props:
  {
    task: TaskResponseModel,
    deleteTask: (task: TaskResponseModel) => Promise<void>
  }
) {
  return (
    <Message>
      <Message.Header>{ props.task.name }</Message.Header>
      <p>{ props.task.description }</p>
      { props.task.tags.length !== 0 &&
        <p><b>Tags</b>: { props.task.tags.map(task => task.name).join(", ") }</p>
      }
      <i>Saved on { new Date(props.task.timestamp).toDateString() } by <b>{ props.task.user.username }</b></i>
      { (props.task.user.username === localStorage.getItem("user") || localStorage.getItem("isAdmin") === "true") &&
        <img
          className="delete-icon"
          src="/delete.png"
          alt="delete.png"
          height="18px"
          title="Delete task"
          onClick={ () => props.deleteTask(props.task) }
        />
      }
    </Message>
  );
}
