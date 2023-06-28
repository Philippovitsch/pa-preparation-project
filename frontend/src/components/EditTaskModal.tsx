import { Modal } from "semantic-ui-react";
import { Dispatch, SetStateAction } from "react";
import { TaskResponseModel } from "../model/TaskResponseModel.ts";
import TaskForm from "./TaskForm.tsx";
import { updateTask } from "../functions/fetch.ts";

export default function EditTaskModal(props:
  {
    openModal: boolean,
    setOpenModal: Dispatch<SetStateAction<boolean>>
    getTasks: Function,
    task: TaskResponseModel
  }
) {
  return (
    <Modal
      onClose={() => props.setOpenModal(false)}
      open={ props.openModal }
    >
      <Modal.Header>Edit task</Modal.Header>
      <Modal.Content>
        <TaskForm
          setOpenModal={ props.setOpenModal }
          task={ props.task }
          getTasks={props.getTasks}
          saveTask={ updateTask }
          buttonTitle="Edit task"
        />
      </Modal.Content>
    </Modal>
  );
}
