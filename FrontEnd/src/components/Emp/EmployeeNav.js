import React, { Component } from "react";
import { Navbar, NavDropdown, Nav } from "react-bootstrap";

class AdminNav extends Component {
  render() {
    return (
      <>
        <Navbar
          className="color-nav"
          collapseOnSelect
          expand="lg"
          variant="dark"
        >
          <Navbar.Brand>AGME</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="/dashemployee"> Home </Nav.Link>
              <Nav.Link href="/empschedule">My Schedule </Nav.Link>
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
              <Nav.Link eventKey={2} href="/login">
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
