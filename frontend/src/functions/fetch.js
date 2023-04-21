import axios from "axios";

const axiosInstance = axios.create();

function getAxiosConfig(subDomain, requestMethod, requestBody) {
  const encodedToken = localStorage.getItem("encodedToken");
  const config = {
    method: requestMethod,
    url: "http://localhost:8080" + subDomain,
    headers: {
      "Authorization": "Basic " + encodedToken
    },
    data: requestBody
  };
  return config;
}

export function getAllTasks() {
  const axiosConfig = getAxiosConfig("/api/tasks/all", "GET", {});
  return axiosInstance(axiosConfig)
    .then(response => {
      return response.data;
    })
    .catch(error => {
      console.log("Error: " + error);
      return [];
    });
}

export function saveNewTask(task) {
  const axiosConfig = getAxiosConfig("/api/tasks", "POST", task);
  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function deleteTaskByName(name) {
  const axiosConfig = getAxiosConfig(`/api/tasks/${name}`, "DELETE", {});
  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}
