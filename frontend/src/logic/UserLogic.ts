import { useEffect, useState } from "react";
import { getTasksByUsername, getUserData } from "../functions/fetch";
import { UserDetailsModel } from "../model/UserDetailsModel";
import { TaskResponseModel } from "../model/TaskResponseModel.ts";

const useUser = () => {
  const [userDetails, setUserDetails] = useState<UserDetailsModel>();
  const [tasks, setTasks] = useState<TaskResponseModel[]>([]);

  useEffect(() => {
    getUser();
    getTasks();
  }, []);

  const getUser = async () => {
    const response = await getUserData();
    if (response.status === 200) {     
      setUserDetails(response.data);
    }
  };

  const getTasks = async () => {
    const response = await getTasksByUsername();
    if (response.status === 200) {
      setTasks(response.data);
    }
  };

  return [{userDetails, tasks}];
}

export default useUser;