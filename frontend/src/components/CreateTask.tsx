import { Button, Checkbox, Container, Form, Header, Message, Segment } from "semantic-ui-react";
import useCreateTask from "../logic/CreateTaskLogic";

export default function CreateTask() {
  const [{setName, setDescription, saveTask, tags, userMessage}] = useCreateTask();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>Create new task</Header>
      { userMessage !== undefined && userMessage.level === "success" &&
        <Message success content={ userMessage.text } /> }
      { userMessage !== undefined && userMessage.level === "error" &&
        <Message error content={ userMessage.text } /> }
      <Form>
        <Form.Field>
          <label>Task name:</label>
          <input placeholder="Task name" type="text" id="task-name" onChange={ (event) => setName(event.target.value) } />
        </Form.Field>
        <Form.Field>
          <label>Task description</label>
          <input placeholder="Task description" type="text" onChange={ (event) => setDescription(event.target.value) } />
        </Form.Field>
        <Segment style={{ marginTop: "30px" }}>
          {
            tags.map(tag => (
              <Checkbox key={tag.id} style={{ marginRight: "25px" }} data-id={ tag.id } data-name={ tag.name } label={ tag.name } />
            ))
          }
        </Segment>
        <Button type="submit" style={{ marginTop: "15px" }} value="Save task" onClick={ saveTask }>Add task</Button>
      </Form>
    </Container>
  );
}
