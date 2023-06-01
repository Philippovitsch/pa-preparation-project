import { Container, Header } from "semantic-ui-react";

export default function User() {
  return (
    <Container text style={{ paddingTop: "100px", paddingBottom: "100px" }}>
      <Header as="h2" style={{ marginBottom: "25px" }}>User Details</Header>
      <p>Blablabla...</p>
    </Container>
  )
}
