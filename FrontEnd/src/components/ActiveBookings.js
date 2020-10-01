import React, { Component } from "react";
import { Table, Card, CardDeck } from "react-bootstrap";
import NavBar from "./NavBar";
import BookingCard from "./BookingCard";
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
      this.setState({ bookings: Array.from(res.data.body.bookings) });
    });
  };

  cancelBooking = (id) => {
    ApiService.cancelBooking(this, { id }).then((res) => {
      this.setState({ message: res.data });
      this.reloadBookingList();
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
              <BookingCard
                key={booking.bookingID}
                booking={booking}
                cancelButton={
                  <button
                    type="button"
                    className="btn btn-danger"
                    onClick={() => this.cancelBooking(booking.bookingID)}
                    style={{ float: "right", margin: "5px" }}
                  >
                    Cancel Booking
                  </button>
                }
              ></BookingCard>
            ))}
          </CardDeck>
        </div>
      </>
    );
  }
}
