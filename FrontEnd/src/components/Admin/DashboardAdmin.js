import React, { Component } from "react";
import AdminNav from "./AdminNav";
import { Table, Card, CardDeck } from "react-bootstrap";
import ApiService from "../../api/ApiService";
import BookingCard from "../BookingCard";

class DashboardAdmin extends Component {
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

  refreshPage() {
    window.location.reload(false);
  }

  reloadBookingList = () => {
    ApiService.fetchAdminBookings(this).then((res) => {
      this.setState({ bookings: Array.from(res.data.body) });
    });
  };

  approveBooking = (id) => {
    ApiService.approveBookingStatus(this, { id }).then((res) => {
      this.setState({ message: res.data });
      this.reloadBookingList();
    });
  };

  render() {
    return (
      <>
        <header>
          <AdminNav />
        </header>
        <div className="active-booking-wrapper">
          <CardDeck>
            {this.state.bookings.map((booking) => (
              <BookingCard
                key={booking.id}
                booking={booking}
                render={() => (
                  <div>
                    <button
                      type="button"
                      className="btn btn-danger"
                      onClick={() => alert("Declining bookings unsupported")}
                      style={{ float: "right", margin: "5px" }}
                    >
                      Reject
                    </button>
                    <button
                      type="button"
                      className="btn btn-success"
                      onClick={() => this.approveBooking(booking.id)}
                      style={{ float: "right", margin: "5px" }}
                    >
                      Approve
                    </button>
                  </div>
                )}
              />
            ))}
          </CardDeck>
        </div>
      </>
    );
  }
}

export default DashboardAdmin;
