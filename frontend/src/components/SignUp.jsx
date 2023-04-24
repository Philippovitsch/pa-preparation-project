import useSignUp from "../logic/SignUpLogic"

export default function SignUp() {
  const [{setUsername, setPassword, performSignUp, successMessage}] = useSignUp();

  return (
    <div className="content">
      <center>
        <h2>Sign Up:</h2>
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
          <input type="button" value="Sign Up" className="button" onClick={ performSignUp } />
          <div>{ successMessage }</div>
        </div>
      </center>
    </div>
  )
}
