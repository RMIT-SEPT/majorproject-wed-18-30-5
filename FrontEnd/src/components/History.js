import React, { Component } from "react";
import { Table, Card, CardDeck } from 'react-bootstrap';
import NavBar from './NavBar';

class History extends Component {
  render() {

    return (
      <>
        <header>
          <NavBar />
        </header>
        <div className="active-booking-wrapper">
          <CardDeck>
            {
              this.state.bookings.map(
                booking => (
                  <Card>
                    <Card.Img variant="top" src="holder.js/100px160" />
                    <Card.Body>
                      <Card.Title>Service Name</Card.Title>
                      <Card.Text>
                        <ul>
                          <li>Customer Name : {booking.startDateTime}</li>
                          <li>Employee Name : </li>
                          <li>Start-Date : </li>
                          <li>End-Date : </li>
                        </ul>
                      </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                      <small className="text-muted">Last updated 3 mins ago</small>
                    </Card.Footer>
                  </Card>
                )
              )}
          </CardDeck>
        </div>
      </>
    )
  }
}

export default History;
