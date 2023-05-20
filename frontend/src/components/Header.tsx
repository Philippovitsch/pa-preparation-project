export default function Header(props:
  {
    user: string | null
  }
) {
  return (
    <div className="header">
      <img className="logo" src="/icon.png" alt="logo" height="60px" />
      <h1>Task Tracker</h1>
      <div className='login-status'>
        { !props.user
          ? <i>Not logged in</i>
          : <i>Logged in as: <b>{ props.user }</b></i>
        }
      </div>
    </div>
  );
}
