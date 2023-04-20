import { useState } from "react"
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const performLogin = () => {
    localStorage.setItem("username", username);
    localStorage.setItem("password", password);
    navigate("/");
  };

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
