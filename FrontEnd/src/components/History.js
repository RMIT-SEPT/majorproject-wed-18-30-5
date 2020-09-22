import React, { Component } from "react";
import { Table, Card, CardDeck } from "react-bootstrap";
import NavBar from "./NavBar";
import ApiService from "../api/ApiService";
import BookingCard from "./BookingCard";

class History extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pastbookings: [],
      message: null,
    };
  }

  componentDidMount() {
    this.reloadBookingList();
  }

  reloadBookingList = () => {
    ApiService.fetchPastBookings(this).then((res) => {
      this.setState({ pastbookings: Array.from(res.data.body.bookings) });
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
            {this.state.pastbookings.map((pastbookings) => (
              <BookingCard
                key={pastbookings.id}
                booking={pastbookings}
              ></BookingCard>
            ))}
          </CardDeck>
        </div>
      </>
    );
  }
}

export default History;
