import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { Server } from "miragejs";
import { render, waitForElement } from "@testing-library/react";
import NavBar from "./components/NavBar";
import ActiveBookings from "./components/ActiveBookings";
import Booking from "./components/Booking";

// /**
//  * @param {Server} server
//  */
// let server;

// beforeEach(() => {
//   server = new Server();
//   server.namespace = "http://localhost:8080";
// });

// afterEach(() => {
//   server.shutdown();
// });

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
  ReactDOM.render(<Booking />, div); 
});

// it("Bookings renders 'Employee' text", () => {
//   const { getByText } = render(<Booking />);
//   const linkElement = getByText(/Employee/);
//   expect(linkElement).toBeInTheDocument();
// });

// it("shows the users from our server", async () => {
//   server.get("/users", () => [
//     { id: 1, name: "Luke" },
//     { id: 2, name: "Leia" },
//   ]);

//   const { getByTestId } = render(<App />);
//   await waitForElement(() => getByTestId("user-1"));
//   await waitForElement(() => getByTestId("user-2"));

//   expect(getByTestId("user-1")).toHaveTextContent("Luke");
//   expect(getByTestId("user-2")).toHaveTextContent("Leia");
// });
