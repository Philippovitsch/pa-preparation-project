import { Link } from "react-router-dom";
import { Container, Image, Menu } from "semantic-ui-react"

export default function Navbar(props:
  {
    user: string | null
  }
) {
  return (
    <Menu fixed="top" inverted>
      <Container>
        <Menu.Item as={Link} to="/" header>
          <Image size="mini" src="/icon.png" style={{ marginRight: "1.5em" }} />
          Task Tracker
        </Menu.Item>
        { props.user &&
          <Menu.Item as={Link} to="/new-task">Add new task</Menu.Item>
        }
        <div className='login-status'>
          { props.user
            ? <>
              <i>Logged in as: <Link to="/user"><b>{ props.user }</b></Link></i>
              <Menu.Item as={Link} to="/logout">Logout</Menu.Item>
            </>
            : <>
              <Menu.Item as={Link} to="/sign-up">Sign Up</Menu.Item>
              <Menu.Item as={Link} to="/login">Login</Menu.Item>
            </>
          }
        </div>
      </Container>
    </Menu>
  );
}
