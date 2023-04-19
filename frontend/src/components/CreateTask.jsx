import { useState } from "react";
import { saveNewTask } from "../functions/fetch";

export default function CreateTask() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const saveTask = async () => {
    const newTask = {
      name: name,
      description: description,
      timestamp: new Date().toISOString()
    };

    const response = await saveNewTask(newTask);
    
    if (response.status == 200) {
      setSuccessMessage("Successfully saved card!");
    } else {
      setSuccessMessage("Error while saving card");
    }
  };

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
