import React, { Component } from "react";
import { Card, ListGroup } from "react-bootstrap";

function BookingCard({ cancelButton, booking, render }) {
  return (
    <Card>
      {/* <pre>{JSON.stringify(booking, null, "    ")}</pre> */}
      {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
      <Card.Body>
        <Card.Title>{booking.service} </Card.Title>
        {/* <Card.Title>Customers</Card.Title> */}
        <ListGroup variant="flush">
          <ListGroup.Item>
            <b>Customer : </b>
            {booking.customerName}{" "}
          </ListGroup.Item>
          <ListGroup.Item>
            <b>Employee : </b>
            {booking.employeeName}{" "}
          </ListGroup.Item>
          <ListGroup.Item>
            {" "}
            <b>Date : </b> {booking.date}{" "}
          </ListGroup.Item>
          <ListGroup.Item>
            <b>Time From : </b> {booking.startTime} <b> To : </b>{" "}
            {booking.endTime}{" "}
          </ListGroup.Item>
        </ListGroup>
        {cancelButton !== undefined && cancelButton}
        {/* <ul>
            {customers &&
              customers.map(({ name }) => <li key={name}>Name: {name}</li>)}
          </ul>
          <Card.Title>Employees</Card.Title>

          <ul>
            {employees &&
              employees.map(({ name }) => <li key={name}>Name: {name} </li>)}
          </ul> */}
        {/* <Card.Text>Start-Date : {booking.startDateTime}</Card.Text> */}
        {/* <Card.Text>End-Date : {booking.endDateTime}</Card.Text> */}
      </Card.Body>
      {render && render()}
      <Card.Footer>
        <span className="mr-2">
          {(() => {
            switch (booking.status) {
              case "PENDING":
                return <span className="badge badge-secondary">Pending</span>;
              case "APPROVED":
                return <span className="badge badge-success">Active</span>;
              case "CANCELLED":
                return <span className="badge badge-danger">Cancelled</span>;
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

export default BookingCard;
