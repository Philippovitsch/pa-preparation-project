import {Modal} from "semantic-ui-react";
import {Dispatch, SetStateAction} from "react";

export default function ImageModal(props:
  {
    openModal: boolean,
    setOpenModal: Dispatch<SetStateAction<boolean>>
    imageName: string,
    imageType: string,
    imageData: string
  }
  ) {
  return (
    <Modal
      basic
      onClose={() => props.setOpenModal(false)}
      open={props.openModal}
    >
      <img
        src={"data:" + props.imageType + ";base64, " + props.imageData}
        alt={ props.imageName }
        height="600px"
        className="center"
      />
    </Modal>
  );
}
