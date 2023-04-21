import useCreateTask from "../logic/CreateTaskLogic";

export default function CreateTask() {
  const [{setName, setDescription, saveTask, successMessage}] = useCreateTask();
  
  return (
    <div className="content">
      <center>
        <h2>Create new task:</h2>
        <div className="card-element">
          <table>
            <tbody>
              <tr>
                <td>Task name:</td>
                <td><input type="text" id="task-name" onChange={ (event) => setName(event.target.value) }/></td>
              </tr>
              <tr>
                <td>Task description:</td>
                <td><input type="text" onChange={ (event) => setDescription(event.target.value) }/></td>
              </tr>
            </tbody>  
          </table>
          <input type="button" value="Save task" className="button" onClick={ saveTask } />
          <div>{ successMessage }</div>
        </div>
      </center>
    </div>
  );
}
