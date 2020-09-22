import React, { Component } from "react";
import { Card, ListGroup } from "react-bootstrap";

class BookingCard extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Card>
        {/* <pre>{JSON.stringify(booking, null, "    ")}</pre> */}
        {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
        <Card.Body>
          <Card.Title>{this.props.booking.service} </Card.Title>
          {/* <Card.Title>Customers</Card.Title> */}
          <ListGroup variant="flush">
            <ListGroup.Item>
              <b>Customer : </b>
              {this.props.booking.customerName}{" "}
            </ListGroup.Item>
            <ListGroup.Item>
              <b>Employee : </b>
              {this.props.booking.employeeName}{" "}
            </ListGroup.Item>
            <ListGroup.Item>
              {" "}
              <b>Date : </b> {this.props.booking.date}{" "}
            </ListGroup.Item>
            <ListGroup.Item>
              <b>Time From : </b> {this.props.booking.startTime} <b> To : </b>{" "}
              {this.props.booking.endTime}{" "}
            </ListGroup.Item>
          </ListGroup>
          {/* <ul>
            {customers &&
              customers.map(({ name }) => <li key={name}>Name: {name}</li>)}
          </ul>
          <Card.Title>Employees</Card.Title>

          <ul>
            {employees &&
              employees.map(({ name }) => <li key={name}>Name: {name} </li>)}
          </ul> */}
          {/* <Card.Text>Start-Date : {this.props.booking.startDateTime}</Card.Text> */}
          {/* <Card.Text>End-Date : {this.props.booking.endDateTime}</Card.Text> */}
        </Card.Body>
        {this.props.render && this.props.render()}
        <Card.Footer>
          <span className="mr-2">
            {(() => {
              switch (this.props.booking.status) {
                case "PENDING":
                  return <span className="badge badge-secondary">Pending</span>;
                case "APPROVED":
                  return <span className="badge badge-success">Active</span>;
                case "CANCELLED":
                  return <span className="badge badge-warning">Cancelled</span>;
                default:
                  return <span className="badge badge-warning">Cancelled</span>;
              }
            })()}
          </span>

          <small className="text-muted float-right">
            Last updated 3 mins ago
          </small>
        </Card.Footer>
      </Card>
    );
  }
}

export default BookingCard;
