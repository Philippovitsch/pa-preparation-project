import { useState } from "react";
import { toggleIsDone } from "../functions/fetch";

const useTask = () => {
  const [imageModal, setImageModal] = useState(false);
  const [editModal, setEditModal] = useState(false);

  const performToggleIsDone = (taskName: string, isChecked: boolean | undefined) => {
      const isDone = isChecked ? true : false;
      toggleIsDone(taskName, isDone);
  };

  return [{ imageModal, setImageModal, editModal, setEditModal, performToggleIsDone }];
};

export default useTask;
