import axios from "axios";

const url = "http://localhost:8080";
const axiosInstance = axios.create();

function getAxiosConfig(subDomain, requestMethod, requestBody) {
  const bearerToken = localStorage.getItem("bearerToken");
  const config = {
    method: requestMethod,
    url: url + subDomain,
    headers: {
      "Authorization": "Bearer " + bearerToken
    },
    data: requestBody
  };
  return config;
}

export function getBearerToken(loginData) {
  return axiosInstance.post(url + "/api/auth/authenticate", loginData)
    .then(response => {
      return response;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response;
    });
}

export function signUp(signUpData) {
  return axiosInstance.post(url + "/api/auth/sign-up", signUpData)
    .then(response => {
      return response.status;
    })
    .catch(error => {
      console.log("Error: " + error);
      return error.response.status;
    });
}

export function getAllTasks() {
  if (localStorage.getItem("bearerToken") === null) {
    return [];
  }

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
  if (localStorage.getItem("bearerToken") === null) {
    return {status: -1};
  }

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
  if (localStorage.getItem("bearerToken") === null) {
    return {status: -1};
  }

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
