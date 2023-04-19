export default function Task(props) {
  return (
    <div className="card-element">
      <b>{ props.task.name }</b>: { props.task.description }
      <br />
      <i>{ new Date(props.task.timestamp).toDateString() }</i>
      <img
        className="delete-icon"
        src="/delete.png"
        alt="delete.png"
        height="18px"
        title="Delete task"
        onClick={ () => props.deleteTask(props.task) } 
      />
    </div>
  );
}
