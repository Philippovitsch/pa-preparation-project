import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import Tasks from "./components/Tasks";
import Footer from "./components/Footer";
import Header from "./components/Header";
import CreateTask from "./components/CreateTask";
import Login from "./components/Login";
import Logout from "./components/Logout";
import SignUp from "./components/SignUp";
import Navbar from "./components/Navbar";

export default function App() {
  const [user, setUser] = useState(localStorage.getItem("user"));

  return (
    <>
      <Header user={ user } />
      <Navbar />
      <Routes>
        <Route path="/" exact element={ <Tasks /> } />
        <Route path="/new-task" element={ <CreateTask /> } />
        <Route path="/login" element={ <Login setUser={ setUser } /> } />
        <Route path="/logout" element={ <Logout setUser={ setUser } /> } />
        <Route path="/sign-up" element={ <SignUp /> } />
      </Routes>
      <Footer />
    </>
  );
}
