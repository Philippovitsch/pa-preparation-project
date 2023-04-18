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
