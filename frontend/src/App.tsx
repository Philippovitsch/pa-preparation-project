import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import Tasks from "./components/Tasks";
import Footer from "./components/Footer";
import Navbar from "./components/Navbar";
import CreateTask from "./components/CreateTask";
import Login from "./components/Login";
import Logout from "./components/Logout";
import SignUp from "./components/SignUp";
import User from "./components/User";

export default function App() {
  const [user, setUser] = useState(localStorage.getItem("user"));
  return (
    <>
      <Navbar user={ user } />
      <Routes>
        <Route path="/" element={ <Tasks /> } />
        <Route path="/new-task" element={ <CreateTask /> } />
        <Route path="/login" element={ <Login setUser={ setUser } /> } />
        <Route path="/logout" element={ <Logout setUser={ setUser } /> } />
        <Route path="/sign-up" element={ <SignUp /> } />
        <Route path="/user" element={ <User /> } />
      </Routes>
      <Footer />
    </>
  );
}
