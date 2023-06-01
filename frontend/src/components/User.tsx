import { useEffect, useState } from "react";
import { Container, Header, Message, Table } from "semantic-ui-react";
import { getTasksByUsername, getUserData } from "../functions/fetch";
import { UserDetailsModel } from "../model/UserDetailsModel";
import { TaskModel } from "../model/TaskModel";
import { Link } from "react-router-dom";

export default function User() {
  const [userDetails, setUserDetails] = useState<UserDetailsModel>();
  const [tasks, setTasks] = useState<TaskModel[]>([]);

  useEffect(() => {
    getUser();
    getTasks();
  }, []);

  const getUser = async () => {
    const response = await getUserData();
    if (response.status === 200) {     
      setUserDetails(response.data);
    }
  };

  const getTasks = async () => {
    const response = await getTasksByUsername();
    if (response.status === 200) {
      setTasks(response.data);
    }
  };

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
                <Table.Cell><b>{ userDetails?.authorities.map(authority => authority.substring(5)).join(", ") }</b></Table.Cell>
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
