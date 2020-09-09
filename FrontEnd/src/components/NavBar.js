import React, { Component } from "react";
import { Navbar, NavDropdown, Nav } from "react-bootstrap";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Booking from "./Booking";
import Home from "./Home";
import History from "./History";

class NavBar extends Component {
  render() {
    return (
      <>
        <Navbar className="color-nav" collapseOnSelect expand="lg" variant="dark" >
          <Navbar.Brand>AGME</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="/home"> Home</Nav.Link>
              <Nav.Link href="/activebookings">Current Bookings</Nav.Link>
              <Nav.Link href="/history">History</Nav.Link>
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

export default NavBar;
