import { Routes, Route } from "react-router-dom";
import Tasks from "./components/Tasks";
import Footer from "./components/Footer";
import Header from "./components/Header";
import CreateTask from "./components/CreateTask";
import Login from "./components/Login";
import Logout from "./components/Logout";
import SignUp from "./components/SignUp";

export default function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" exact element={ <Tasks /> } />
        <Route path="/sign-up" element={ <SignUp /> } />
        <Route path="/new-task" element={ <CreateTask /> } />
        <Route path="/login" element={ <Login /> } />
        <Route path="/logout" element={ <Logout /> } />
      </Routes>
      <Footer />
    </>
  );
}
