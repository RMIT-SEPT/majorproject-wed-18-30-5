import React, { Component } from "react";
import { CardColumns } from "react-bootstrap";
import EmployeeNav from "./EmployeeNav";
import BookingCard from "../BookingCard";
import ApiService from "../../api/ApiService";

export default class EmpBookings extends Component {
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

  render() {
    return (
      <>
        <header>
          <EmployeeNav />
        </header>
        <div className="active-booking-wrapper">
          <CardColumns>
            {this.state.bookings.map((booking) => (
              <BookingCard key={booking.id} booking={booking}></BookingCard>
            ))}
          </CardColumns>
        </div>
      </>
    );
  }
}
