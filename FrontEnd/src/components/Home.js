import React, { Component } from "react";
import ApiService from "../api/ApiService";
import NavBar from "./NavBar";
import Booking from "./Booking";
import background from "../images/background.jpg";
import "../App.css";

class Home extends Component {
  render() {
    return (
      <>
        <header>
          <NavBar />
        </header>

        <div className="order-1 order-lg-2 ">
          <img src={background} className="img-fluid home-bq" />
        </div>
        <div className="booking-wrapper">
          <Booking />
        </div>
      </>
    );
  }
}

export default Home;
