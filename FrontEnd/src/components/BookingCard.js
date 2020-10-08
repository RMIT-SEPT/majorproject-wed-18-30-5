import React from "react";
import { Card, ListGroup } from "react-bootstrap";

function BookingCard({ cancelButton, booking, render }) {
  return (
    <Card>
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
      </Card.Body>
      <Card.Footer>
        <span>
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
        {cancelButton !== undefined &&
          booking.status != "CANCELLED" &&
          cancelButton}
      </Card.Footer>
      {render && render()}
    </Card>
  );
}

export default BookingCard;
