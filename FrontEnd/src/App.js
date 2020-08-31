import React from "react";
import "./App.css";
import Login from "./components/Login";
import Signup from "./components/Signup";
import DashboardUser from "./components/DashboardUser";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Home from "./components/Home";
import Booking from "./components/Booking";
import History from "./components/History";
import EditUser from "./components/Users/EditUser";

function App() {
  return (
    <Router>
      <Route exact path="/" component={Login} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/booking" component={Booking} />
      <Route exact path="/history" component={History} />
      <Route exact path="/edituser" component={EditUser} />
      <Route exact path="/signup" component={Signup} />
    </Router>
  );
}

export default App;
