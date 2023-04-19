import axios from "axios";

const url = "http://localhost:8080/api"
const axiosInstance = axios.create();

export function getAllTasks() {
  return axiosInstance.get(url + "/tasks/all")
    .then(response => {
      return response.data;
    })
    .catch(error => {
      console.log("Error: " + error);
      return [];
    });
}

export function saveNewTask(task) {
  return axiosInstance.post(url + "/tasks", task)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function deleteTaskByName(name) {
  return axiosInstance.delete(url + `/tasks/${name}`)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}
