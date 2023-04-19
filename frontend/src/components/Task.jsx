export default function Task(props) {
  return (
    <div className="card-element">
      <b>{ props.task.name }</b>: { props.task.description }
      <br />
      <i>{ new Date(props.task.timestamp).toDateString() }</i>
    </div>
  );
}
