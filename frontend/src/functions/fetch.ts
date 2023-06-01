import axios from "axios";
import { TaskModel } from "../model/TaskModel";
import { UserDataModel } from "../model/UserDataModel";
import { ConfigModel } from "../model/ConfigModel";

const url = "http://localhost:8080";
const axiosInstance = axios.create();

function getAxiosConfig(subDomain: string, requestMethod: string, token: string, requestBody: object) {
  const config: ConfigModel = {
    method: requestMethod,
    url: url + subDomain,
    headers: {
      "Authorization": token
    },
    data: requestBody
  };
  return config;
}

function fetchData(axiosConfig: ConfigModel, token: string) {
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

export function getBearerToken(encodedToken: string) {
  const token = "Basic " + encodedToken;
  const axiosConfig = getAxiosConfig("/api/auth/authenticate", "POST", token, {});
  return fetchData(axiosConfig, token);
}

export function signUp(userData: UserDataModel) {
  const token = ""
  const axiosConfig = getAxiosConfig("/api/auth/sign-up", "POST", token, userData);
  return fetchData(axiosConfig, token);
}

export function getAllTasks() {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks/all", "GET", token, {});
  return fetchData(axiosConfig, token);
}

export function getTasksByUsername() {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const username = localStorage.getItem("user");
  const axiosConfig = getAxiosConfig(`/api/tasks/user/${username}`, "GET", token, {});
  return fetchData(axiosConfig, token);
}

export function saveNewTask(task: TaskModel) {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tasks", "POST", token, task);
  return fetchData(axiosConfig, token);
}

export function deleteTaskByName(name: string) {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig(`/api/tasks/${name}`, "DELETE", token, {});
  return fetchData(axiosConfig, token);
}

export function getAllTags() {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const axiosConfig = getAxiosConfig("/api/tags/all", "GET", token, {});
  return fetchData(axiosConfig, token);
}

export function getUserData() {
  const token = "Bearer " + localStorage.getItem("bearerToken");
  const username = localStorage.getItem("user");
  const axiosConfig = getAxiosConfig(`/api/user/${username}`, "GET", token, {})
  return fetchData(axiosConfig, token)
}
