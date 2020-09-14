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
                {/* <pre>{JSON.stringify(booking, null, "    ")}</pre> */}
                {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
                <Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    <Card.Title>Customers</Card.Title>
                    <ul>
                      {booking.customers.map(({ name }) => (
                        <li>Name: {name}</li>
                      ))}
                    </ul>
                    <Card.Title>Employees</Card.Title>

                    <ul>
                      {booking.employees.map(({ name }) => (
                        <li>Name: {name} </li>
                      ))}
                    </ul>
                    <Card.Text>Start-Date : {booking.startDateTime}</Card.Text>
                    <Card.Text>End-Date : {booking.endDateTime}</Card.Text>
                  </Card.Text>
                </Card.Body>
                <Card.Footer>
                  <span className="mr-2">
                    {booking.approved ? (
                      <span class="badge badge-success">Active</span>
                    ) : (
                      <span class="badge badge-secondary">Pending</span>
                    )}
                  </span>

                  <small className="text-muted float-right">
                    Last updated 3 mins ago
                  </small>
                </Card.Footer>
              </Card>
            ))}
          </CardDeck>
        </div>
      </>
    );
  }
}
