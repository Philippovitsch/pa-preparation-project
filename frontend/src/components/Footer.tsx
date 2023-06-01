import { Container, Segment } from "semantic-ui-react"

export default function Footer() {
  return (
  <Segment inverted vertical style={{ margin: "10px 0 0", padding: "20px", position: "fixed", bottom: 0, width: "100%" }}>
    <Container textAlign="center">
      &copy; 2023 - A Java Spring and React demo project for PA preparation.
    </Container>
  </Segment>
  );
}
