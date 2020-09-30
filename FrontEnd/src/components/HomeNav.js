import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";

class HomeNavBar extends Component {
  render() {
    return (
      <>
        <Navbar
          className="color-nav"
          collapseOnSelect
          expand="lg"
          variant="dark"
        >
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto ml-auto">
              <Nav.Link href="/login"> Login</Nav.Link>
              <Nav.Link href="/aboutus">About Us</Nav.Link>
              <Nav.Link href="/contactus">Contact Us</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </>
    );
  }
}

export default HomeNavBar;
