import { Container, Header } from "semantic-ui-react";
import Task from "./Task";
import useTasks from "../logic/TaskLogic";

export default function Tasks() {
  const [{ tasks, deleteTask }] = useTasks();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>Saved Tasks</Header>
      {
        tasks.map(task => (
          <Task key={ task.id } task={ task } deleteTask={ deleteTask } />
        ))
      }
    </Container>
  );
}
