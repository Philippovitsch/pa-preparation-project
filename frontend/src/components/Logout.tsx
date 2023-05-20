import useLogout from "../logic/LogoutLogic";

export default function Logout(props:
  {
    setUser: (user: string | null) => void
  }
) {
  useLogout(props.setUser);

  return (
    <div className="content">
      <center>
        <h2>Logout:</h2>
        Performing logout...
      </center>
    </div>
  );
}
