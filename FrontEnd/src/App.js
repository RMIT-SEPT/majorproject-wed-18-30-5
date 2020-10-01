import React from "react";
import "./App.css";
import Login from "./components/Login";
import Signup from "./components/Signup";
// import DashboardUser from "./components/DashboardUser";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  BrowserRouter as Router,
  Route,
  Redirect,
  useLocation,
  Switch,
} from "react-router-dom";
import Home from "./components/Home";
import Booking from "./components/Booking";
import History from "./components/History";
import EditUser from "./components/Users/EditUser";
import ActiveBooking from "./components/ActiveBookings";
import DashboardAdmin from "./components/Admin/DashboardAdmin";
import Schedule from './components/Emp/Schedule';
  // import Upcoming from './components/Emp/upcoming/Upcoming';
// import Employee from "./components/Employee";
// import DashEmployee from "./components/DashEmployee";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={() => <Redirect to="/login" />} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/home" component={Home} />
        <Route exact path="/booking" component={Booking} />
        <Route exact path="/history" component={History} />
        <Route exact path="/edituser" component={EditUser} />
        <Route exact path="/signup" component={Signup} />
        <Route exact path="/activebookings" component={ActiveBooking} />
        <Route exact path="/dashboardadmin" component={DashboardAdmin} />
        <Route exact path="/schedule" component={Schedule} />
        {/* <Route exact path="/upcoming" component={Upcoming} /> */}
        {/* <Route exact path="/employee" component={Employee} /> */}
        {/* <Route exact path="/dashemployee" component={DashEmployee} /> */}
        <Route exact path="*" component={NoMatch} />
      </Switch>
    </Router>
  );
}

function NoMatch() {
  let location = useLocation();

  return (
    <div>
      <h3>
        No match for <code>{location.pathname}</code>
      </h3>
    </div>
  );
}

export default App;
