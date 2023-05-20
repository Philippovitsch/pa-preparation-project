import useCreateTask from "../logic/CreateTaskLogic";

export default function CreateTask() {
  const [{setName, setDescription, saveTask, tags, successMessage}] = useCreateTask();

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
          {
            tags.map(tag => (
              <label key={tag.id} className="mr-10">
                <input type="checkbox" data-id={ tag.id } data-name={ tag.name } />
                <span>{ tag.name }</span>
              </label>
            ))
          }
          <input type="button" value="Save task" className="button" onClick={ saveTask } />
          <div>{ successMessage }</div>
        </div>
      </center>
    </div>
  );
}
