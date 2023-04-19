import { Routes, Route } from "react-router-dom";
import Tasks from "./components/Tasks";
import Footer from "./components/Footer";
import Header from "./components/Header";
import CreateTask from "./components/CreateTask";

export default function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" exact element={ <Tasks /> } />
        <Route path="/new-task" element={ <CreateTask /> } />
      </Routes>
      <Footer />
    </>
  );
}
