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

export function getBearerToken(encodedToken) {
  const token = "Basic " + encodedToken;
  const axiosConfig = getAxiosConfig("/api/auth/authenticate", "POST", token, {});
  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function signUp(userData) {
  return axiosInstance.post(url + "/api/auth/sign-up", userData)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    })
}

export function getAllTasks() {
  if (localStorage.getItem("bearerToken") === null) {
    return { status: -1 }
  }

  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks/all", "GET", token, {});
  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function saveNewTask(task) {
  if (localStorage.getItem("bearerToken") === null) {
    return { status: -1 }
  }

  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks", "POST", token, task);
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
  if (localStorage.getItem("bearerToken") === null) {
    return { status: -1 }
  }

  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig(`/api/tasks/${name}`, "DELETE", token, {});
  return axiosInstance(axiosConfig)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}
