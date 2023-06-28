import { Container, Header } from "semantic-ui-react";
import { Link } from "react-router-dom";
import TaskForm from "./TaskForm.tsx";
import { saveNewTask } from "../functions/fetch.ts";

export default function CreateTask() {
  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>Create new task</Header>

      { !localStorage.getItem("user")
        ? <p> Please <Link to="/login">login</Link> or <Link to="/sign-up">sign up</Link> to save new tasks...</p>
        : <TaskForm
          saveTask={ saveNewTask }
          buttonTitle="Add task"
        />
      }
    </Container>
  );
}
