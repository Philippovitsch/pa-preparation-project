import axios from "axios";

const url = "http://localhost:8080";
const axiosInstance = axios.create();

function getAxiosConfig(subDomain, requestMethod, token, requestBody) {
  const config = {
    method: requestMethod,
    url: url + subDomain,
    headers: {
      "Authorization": token
    },
    data: requestBody
  };
  return config;
}

function fetchData(axiosConfig, token) {
  if (token === "Bearer null") {
    return { status: -1 }
  }

  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function getBearerToken(encodedToken) {
  const token = "Basic " + encodedToken;
  const axiosConfig = getAxiosConfig("/api/auth/authenticate", "POST", token, {});
  return fetchData(axiosConfig, token);
}

export function signUp(userData) {
  const token = ""
  const axiosConfig = getAxiosConfig("/api/auth/sign-up", "POST", token, userData);
  return fetchData(axiosConfig, token);
}

export function getAllTasks() {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks/all", "GET", token, {});
  return fetchData(axiosConfig, token);
}

export function saveNewTask(task) {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks", "POST", token, task);
  return fetchData(axiosConfig, token);
}

export function deleteTaskByName(name) {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig(`/api/tasks/${name}`, "DELETE", token, {});
  return fetchData(axiosConfig, token);
}
