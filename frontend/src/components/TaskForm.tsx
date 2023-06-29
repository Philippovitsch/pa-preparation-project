import {Button, Checkbox, Form, Icon, Message, Segment} from "semantic-ui-react";
import useTaskForm from "../logic/TaskFormLogic";
import { TaskResponseModel } from "../model/TaskResponseModel.ts";
import { Dispatch, SetStateAction, useEffect } from "react";

export default function TaskForm(props:
  {
    setOpenModal?: Dispatch<SetStateAction<boolean>>,
    task?: TaskResponseModel
    getTasks?: Function,
    saveTask: Function,
    buttonTitle: string
  }
  ) {
  const [{
    setName,
    setDescription,
    handleImageUpload,
    saveTask,
    fetchTags,
    isChecked,
    setDefaultValues,
    imagePreview,
    tags,
    userMessage
  }] = useTaskForm();

  useEffect(  () => {
    fetchTags();
  }, [])

  useEffect(() => {
    setDefaultValues(props.task)
  }, [tags])

  return (
    <>
      { userMessage !== undefined && userMessage.level === "success" &&
          <Message success content={ userMessage.text } /> }
      { userMessage !== undefined && userMessage.level === "error" &&
          <Message error content={ userMessage.text } /> }
      <Form>
        <Form.Field>
          <label>Task name:</label>
          <input
            placeholder="Task name"
            type="text"
            id="task-name"
            onChange={ (event) => setName(event.target.value.trim()) }
          />
        </Form.Field>
        <Form.Field>
          <label>Task description</label>
          <input
            placeholder="Task description"
            id="task-description"
            type="text" onChange={ (event) => setDescription(event.target.value.trim()) }
          />
        </Form.Field>
        <Form.Field style={{ marginTop: "16px", marginBottom: "18px" }}>
          <label>Tags</label>
          <Segment style={{ marginTop: "0" }}>
            {
              tags.map(tag => (
                <Checkbox
                  key={tag.id}
                  style={{ marginRight: "25px" }}
                  data-id={ tag.id }
                  data-name={ tag.name }
                  label={ tag.name }
                  defaultChecked={ isChecked(tag, props.task?.tags) }
                />
              ))
            }
          </Segment>
        </Form.Field>
        <Form.Field>
          <label>Image upload</label>
          <input type="file" id="image-upload" onChange={ (event) => handleImageUpload(event.target.files) } />
          { (imagePreview !== "") &&
            <div style={{ position: "relative" }}>
              <img
                src={imagePreview}
                id="image-preview"
                style={{ width: "100px", marginTop: "10px" }}
                alt="Preview"
              />
              <div onClick={() => handleImageUpload(null)} style={{ position: "absolute", left: "2px", top: "12px" }}>
                  <Icon circular inverted name='delete' size="small" />
              </div>
            </div>
          }
        </Form.Field>
        <Button
          type="submit"
          style={{ marginTop: "15px" }}
          value="Save task"
          onClick={ () => saveTask(props.saveTask, props.getTasks, props.setOpenModal) }
        >
          { props.buttonTitle }
        </Button>
      </Form>
    </>
  )
}
