import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div className="navbar">
      <Link to="/">Home</Link>
      <Link to="login">Login</Link>
      <Link to="logout">Logout</Link>
      <Link to="sign-up">Sign up</Link>
      <Link to="new-task">Add new task</Link>
    </div>
  )
}
