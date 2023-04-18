export default function Task(props) {
  return (
    <div className="task">
      <b>{ props.task.name }</b>: { props.task.description }
      <br />
      <i>{ new Date(props.task.timestamp).toDateString() }</i>
    </div>
  );
}
