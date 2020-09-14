import React, { Component } from "react";
import { Table, Card, CardDeck } from "react-bootstrap";
import NavBar from "./NavBar";
import ApiService from "../api/ApiService";

export default class ActiveBookings extends Component {
  constructor(props) {
    super(props);
    this.state = {
      bookings: [],
      message: null,
    };
  }

  componentDidMount() {
    this.reloadBookingList();
  }

  reloadBookingList = () => {
    ApiService.fetchBookings(this).then((res) => {
      debugger;
      this.setState({ bookings: Array.from(res.data.body) });
    });
  };

  render() {
    return (
      <>
        <header>
          <NavBar />
        </header>
        <div className="active-booking-wrapper">
          <CardDeck>
            {this.state.bookings.map((booking) => (
              <Card key={booking.id}>
                {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
                <Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    <ul>
                      <li>Customer Name :</li>
                      <li>Employee Name : </li>
                      <li>Start-Date : {booking.startDateTime}</li>
                      <li>End-Date : {booking.endDateTime}</li>
                    </ul>
                  </Card.Text>
                  <span class="badge badge-pill badge-primary">Primary</span>
                </Card.Body>
                <Card.Footer>
                  <small className="text-muted">Last updated 3 mins ago</small>
                </Card.Footer>
              </Card>
            ))}
          </CardDeck>
        </div>
      </>
    );
  }
}
