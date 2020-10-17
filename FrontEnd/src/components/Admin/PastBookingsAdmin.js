import React, { Component } from "react";
import AdminNav from "./AdminNav";
import { CardColumns } from "react-bootstrap";
import ApiService from "../../api/ApiService";
import BookingCard from "../BookingCard";

class PastBookingsAdmin extends Component {
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
          <AdminNav />
        </header>

        <div className="active-booking-wrapper">
          <CardColumns>
            {this.state.pastbookings.map((pastbookings) => (
              <BookingCard
                key={pastbookings.id}
                booking={pastbookings}
              ></BookingCard>
            ))}
          </CardColumns>
        </div>
      </>
    );
  }
}

export default PastBookingsAdmin;
