import { Container, Header, Message, Table } from "semantic-ui-react";
import { Link } from "react-router-dom";
import useUser from "../logic/UserLogic";

export default function User() {
  const [{userDetails, tasks}] = useUser();

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>User Details</Header>

      { !localStorage.getItem("user")
        ? <p> Please <Link to="/login">login</Link> or <Link to="/sign-up">sign up</Link> to see user details...</p>
        : <>
          <Table celled striped>
            <Table.Body>
              <Table.Row>
                <Table.Cell>Username </Table.Cell>
                <Table.Cell><b>{ userDetails?.username }</b></Table.Cell>
              </Table.Row>
              <Table.Row>
                <Table.Cell>Roles</Table.Cell>
                <Table.Cell><b>{ userDetails?.authorities.join(", ") }</b></Table.Cell>
              </Table.Row>
              <Table.Row>
                <Table.Cell>AccountNonExpired</Table.Cell>
                <Table.Cell><b>{ userDetails?.accountNonExpired ? "true" : "false" }</b></Table.Cell>
              </Table.Row>
              <Table.Row>
                <Table.Cell>AccountNonLocked</Table.Cell>
                <Table.Cell><b>{ userDetails?.accountNonLocked ? "true" : "false" }</b></Table.Cell>
              </Table.Row>
              <Table.Row>
                <Table.Cell>CredentialsNonExpired</Table.Cell>
                <Table.Cell><b>{ userDetails?.credentialsNonExpired ? "true" : "false" }</b></Table.Cell>
              </Table.Row>
              <Table.Row>
                <Table.Cell>Enabled</Table.Cell>
                <Table.Cell><b>{ userDetails?.enabled ? "true" : "false" }</b></Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table>

          <Header as="h2" style={{ marginBottom: "25px" }}>Your tasks</Header>
          {
            tasks.map(task => (
              <Message key={task.id}>
                <ul>
                  <li><b>{ task.name }</b>: { task.description }</li>
                  { task.tags.length !== 0 &&
                    <li><b>Tags</b>: { task.tags.map(task => task.name).join(", ") }</li>
                  }
                  <li><i>Saved on { new Date(task.timestamp).toDateString() }</i></li>
                </ul>
            </Message>
            ))
          }
        </>
      }
    </Container>
  )
}
