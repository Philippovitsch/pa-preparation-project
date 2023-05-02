import useLogout from "../logic/LogoutLogic";

export default function Logout(props) {
  const [] = useLogout(props.setUser);

  return (
    <div className="content">
      <center>
        <h2>Logout:</h2>
        Performing logout...
      </center>
    </div>
  );
}
