import React from "react";
import "./App.css";
import Login from "./components/Login";
import Signup from "./components/Signup";
import DashboardUser from "./components/DashboardUser";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Redirect } from "react-router-dom";
import Home from "./components/Home";
import Booking from "./components/Booking";
import History from "./components/History";
import EditUser from "./components/Users/EditUser";
import ActiveBooking from "./components/ActiveBookings";
import DashboardAdmin from "./components/DashboardAdmin"
import Employee from "./components/Employee";
import PastBookings from "./components/PastBookings";
import DashEmployee from "./components/DashEmployee"

function App() {
  return (
    <Router>
      <Route exact path="/" component={() => <Redirect to="/login" />} />
      <Route exact path="/login" component={Login} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/booking" component={Booking} />
      <Route exact path="/history" component={History} />
      <Route exact path="/edituser" component={EditUser} />
      <Route exact path="/signup" component={Signup} />
      <Route exact path="/activebookings" component={ActiveBooking} />
      <Route exact path="/dashboardadmin" component={DashboardAdmin} />
      <Route exact path="/employee" component={Employee} />
      <Route exact path="/pastbookings" component={PastBookings} />
      <Route exact path="/dashemployee" component={DashEmployee} />
    </Router>
  );
}

export default App;
