import loginLogic from "../logic/LoginLogic";

export default function Login() {
  const [{setUsername, setPassword, performLogin}] = loginLogic();
  
  return (
    <div className="content">
      <center>
        <h2>Login:</h2>
        <div className="card-element">
          <table>
            <tbody>
              <tr>
                <td>Username:</td>
                <td><input type="text" id="task-name" onChange={ (event) => setUsername(event.target.value) }/></td>
              </tr>
              <tr>
                <td>Password:</td>
                <td><input type="password" onChange={ (event) => setPassword(event.target.value) }/></td>
              </tr>
            </tbody>  
          </table>
          <input type="button" value="Login" className="button" onClick={ performLogin } />
        </div>
      </center>
    </div>
  );
}
