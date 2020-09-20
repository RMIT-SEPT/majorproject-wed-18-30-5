import React, { Component } from "react";
import { Card, ListGroup } from "react-bootstrap";

class BookingCard extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { customers, employees } = this.props.booking;
    return (
      <Card>
        {/* <pre>{JSON.stringify(booking, null, "    ")}</pre> */}
        {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
        <Card.Body>
          <Card.Title>Service Name</Card.Title>
          {/* <Card.Title>Customers</Card.Title> */}
          <ListGroup variant="flush">
            <ListGroup.Item>
              Customer :{" "}
              {customers &&
                customers.map(({ name }) => (
                  <li key={name}>Name: {name}</li>
                ))}{" "}
            </ListGroup.Item>
            <ListGroup.Item>
              Employee :{" "}
              {employees &&
                employees.map(({ name }) => <li key={name}>Name: {name} </li>)}
            </ListGroup.Item>
            <ListGroup.Item>
              Start-Date : {this.props.booking.startDateTime}{" "}
            </ListGroup.Item>
            <ListGroup.Item>
              End-Date : {this.props.booking.endDateTime}{" "}
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
            {this.props.booking.approved ? (
              <span className="badge badge-success">Active</span>
            ) : (
              <span className="badge badge-secondary">Pending</span>
            )}
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
