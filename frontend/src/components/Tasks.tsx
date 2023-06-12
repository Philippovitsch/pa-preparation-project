import { Container, Header } from "semantic-ui-react";
import Task from "./Task";
import useTasks from "../logic/TaskLogic";
import { Link } from "react-router-dom";

export default function Tasks() {
  const [{ tasks, triggerUserTasks, deleteTask }] = useTasks();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>Saved Tasks</Header>
      { !localStorage.getItem("user")
        ? <p> Please <Link to="/login">login</Link> or <Link to="/sign-up">sign up</Link> to see the tasks...</p>
        : <>
          <span className="task-option" onClick={() => triggerUserTasks(false)}>All Tasks</span>
          <span className="task-option" onClick={() => triggerUserTasks(true)}>Your tasks</span>
          { tasks.map(task => <Task key={ task.id } task={ task } deleteTask={ deleteTask } />) }
        </>
      }
    </Container>
  );
}
