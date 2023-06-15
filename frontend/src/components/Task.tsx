import { Message } from "semantic-ui-react"
import { TaskResponseModel } from "../model/TaskResponseModel.ts";
import {useState} from "react";
import ImageModal from "./ImageModal.tsx";

export default function Task(props:
  {
    task: TaskResponseModel,
    deleteTask: (task: TaskResponseModel) => Promise<void>
  }
) {
  const [openModal, setOpenModal] = useState(false);

  return (
    <>
      { openModal &&
        <ImageModal
            openModal={openModal}
            setOpenModal={setOpenModal}
            imageName={ props.task.imageName }
            imageType={ props.task.imageType }
            imageData={ props.task.imageData }
        />
      }
      <Message>
        <Message.Header>{ props.task.name }</Message.Header>
        <p>{ props.task.description }</p>
        { props.task.imageName &&
            <div>
                <img
                    style={{ cursor: "pointer" }}
                    src={"data:" + props.task.imageType + ";base64, " + props.task.imageData}
                    alt={ props.task.imageName }
                    height="64px"
                    onClick={() => setOpenModal(true)}
                />
            </div>
        }
        { props.task.tags.length !== 0 &&
          <p><b>Tags</b>: { props.task.tags.map(task => task.name).join(", ") }</p>
        }
        <i>Saved on { new Date(props.task.timestamp).toDateString() } by <b>{ props.task.user.username }</b></i>
        { (props.task.user.username === localStorage.getItem("user") || localStorage.getItem("isAdmin") === "true") &&
          <img
              className="delete-icon"
              src="/delete.png"
              alt="delete.png"
              height="18px"
              title="Delete task"
              onClick={ () => props.deleteTask(props.task) }
          />
        }
      </Message>

    </>
  );
}
