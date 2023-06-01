import { Container, Header, Message } from 'semantic-ui-react';
import useLogout from "../logic/LogoutLogic";

export default function Logout(props:
  {
    setUser: (user: string | null) => void
  }
) {
  useLogout(props.setUser);

  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>User Logout</Header>
      <Message info content="Performing logout..." />
    </Container>
  );
}
