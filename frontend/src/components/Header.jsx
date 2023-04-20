import { Link } from 'react-router-dom';

export default function Header() {
  return (
    <div className="header">
      <img className="logo" src="/icon.png" alt="logo" height="60px" />
      <h1>Task Tracker</h1>
      <div className='navbar'>
        <Link to="/">Home</Link>
        <Link to="login">Login</Link>
        <Link to="logout">Logout</Link>
        <Link to="new-task">Add new task</Link>
      </div>
    </div>
  );
}
