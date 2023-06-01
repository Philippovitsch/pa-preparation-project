import { Button, Container, Form, Header, Message } from "semantic-ui-react"
import useLogin from "../logic/LoginLogic";

export default function Login(props:
  {
    setUser: (user: string | null) => void
  }
) {
  const [{setUsername, setPassword, performLogin, userMessage}] = useLogin();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>User Login</Header>
      { userMessage !== undefined && userMessage.level === "success" &&
        <Message success content={ userMessage.text } /> }
      { userMessage !== undefined && userMessage.level === "error" &&
        <Message error content={ userMessage.text } /> }
      <Form>
        <Form.Field>
          <label>Username:</label>
          <input placeholder="Username" type="text" id="task-name" onChange={ (event) => setUsername(event.target.value) }  />
        </Form.Field>
        <Form.Field>
          <label>Password</label>
          <input placeholder="Password" type="password" onChange={ (event) => setPassword(event.target.value) } />
        </Form.Field>
        <Button type="submit" style={{ marginTop: "15px" }} value="Login" onClick={() => performLogin(props.setUser) }>Login</Button>
      </Form>
    </Container>
  );
}
