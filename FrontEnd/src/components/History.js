import React, { Component } from "react";
import { Table, Card, CardDeck } from "react-bootstrap";
import NavBar from "./NavBar";
import ApiService from "../api/ApiService";

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
      this.setState({ pastbookings: Array.from(res.data.body) });
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
            {this.state.pastbookings.map((pastbooking) => (
              <Card>
                <Card.Img variant="top" src="holder.js/100px160" />
                <Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    <ul>
                      <li>
                        Customer Name : {pastbooking.customers[0]["name"]}
                      </li>
                      <li>
                        Employee Name : {pastbooking.employees[0]["name"]}
                      </li>
                      <li>Start-Date : {pastbooking.startDateTime}</li>
                      <li>End-Date : {pastbooking.endDateTime}</li>
                    </ul>
                  </Card.Text>
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

export default History;
