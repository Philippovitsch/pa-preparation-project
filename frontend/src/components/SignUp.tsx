import { Button, Checkbox, Container, Form, Header, Message } from "semantic-ui-react"
import useSignUp from "../logic/SignUpLogic";

export default function SignUp() {
  const [{setUsername, setPassword, performSignUp, userMessage}] = useSignUp();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2">Sign Up</Header>
      { userMessage !== undefined && userMessage.level === "success" &&
        <Message success content={ userMessage.text } /> }
      { userMessage !== undefined && userMessage.level === "error" &&
        <Message error content={ userMessage.text } /> }
      <Form>
        <Form.Field>
          <label>First Name</label>
          <input placeholder="Username" type="text" id="task-name" onChange={ (event) => setUsername(event.target.value) } />
        </Form.Field>
        <Form.Field>
          <label>Last Name</label>
          <input placeholder="Password" type="password" onChange={ (event) => setPassword(event.target.value) } />
        </Form.Field>
        <Form.Field>
          <Checkbox id="tos" label="I agree to the Terms and Conditions" />
        </Form.Field>
        <Button type="submit" style={{ marginTop: "15px" }} value="Sign up" className="button" onClick={ performSignUp } >Submit</Button>
      </Form>
    </Container>
  )
}
