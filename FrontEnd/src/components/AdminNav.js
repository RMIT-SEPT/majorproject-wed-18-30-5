import React, { Component } from "react";
import { Navbar, NavDropdown, Nav } from "react-bootstrap";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import PastBookings from "./PastBookings";
import DashboardAdmin from "./DashboardAdmin";
import Employee from "./Employee";

class AdminNav extends Component {
  render() {
    return (
      <>
        <Navbar className="color-nav" collapseOnSelect expand="lg" variant="dark" >
          <Navbar.Brand>AGME</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="/PastBookings"> Past Bookings</Nav.Link>
              <Nav.Link href="/DashboardAdmin">Home</Nav.Link>
              <Nav.Link href="/Employee">Employee</Nav.Link>
            </Nav>
            <Nav>
              <NavDropdown title="Profile" id="collasible-nav-dropdown">
                <NavDropdown.Item href="/edituser">
                  Edit Profile
                </NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">
                  Another action
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#action/3.4">
                  Separated link
                </NavDropdown.Item>
              </NavDropdown>
              <Nav.Link eventKey={2} href="#memes">
                Log Out
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </>
    );
  }
}

export default AdminNav;
