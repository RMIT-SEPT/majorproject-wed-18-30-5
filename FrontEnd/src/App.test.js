import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { render, waitForElement } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import NavBar from "./components/NavBar";
import ActiveBookings from "./components/ActiveBookings";
import Booking from "./components/Booking";
import AdminNav from "./components/Admin/AdminNav";
import Signup from "./components/Signup";
import Login from "./components/Login";
import DashboardAdmin from "./components/Admin/DashboardAdmin";
import DashboardUser from "./components/Users/DashboardUser";

/*
 *  Testing If the Components Render Properly.
 */
it("App renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<App />, div);
});

it("Navbar renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<NavBar />, div);
});

it("Booking renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(
    <BrowserRouter>
      <Booking />
    </BrowserRouter>,
    div
  );
});

it("ActiveBookings renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<ActiveBookings />, div);
});

// it("AdminNav renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(
//     <BrowserRouter>
//       <AdminNav />
//     </BrowserRouter>,
//     div
//   );
// });

// it("Signup renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(
//     <BrowserRouter>
//       <Signup />
//     </BrowserRouter>,
//     div
//   );
// });

// it("Login renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(
//     <BrowserRouter>
//       <Login />
//     </BrowserRouter>,
//     div
//   );
// });

// it("DashboardAdmin renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(
//     <BrowserRouter>
//       <DashboardAdmin />
//     </BrowserRouter>,
//     div
//   );
// });

// it("DashboardUser renders without crashing", () => {
//   const div = document.createElement("div");
//   ReactDOM.render(
//     <BrowserRouter>
//       <DashboardUser />
//     </BrowserRouter>,
//     div
//   );
// });
