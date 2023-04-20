import logoutLogic from "../logic/LogoutLogic";

export default function Logout() {
  const [] = logoutLogic();

  return (
    <div className="content">
      <center>
        <h2>Logout:</h2>
        Performing logout...
      </center>
    </div>
  );
}
