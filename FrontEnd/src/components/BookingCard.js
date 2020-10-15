import React from "react";
import { Card, ListGroup } from "react-bootstrap";

const parseBookingDateTimes = ({ date, startTime, endTime }) => {
  const [year, month, day] = date.split("-");
  const [hour, minute_AMPM] = startTime.split(":");
  const [minute] = minute_AMPM.split(" ");
  const datetime = new Date();
  datetime.setUTCFullYear(year);
  datetime.setUTCMonth(month - 1);
  datetime.setUTCDate(day - 1);
  datetime.setUTCHours(hour);
  datetime.setUTCMinutes(minute);
  const dateTZ = [
    datetime.getFullYear(),
    datetime.getMonth() + 1,
    datetime.getDate() + 1,
  ].join("-");
  const startTimeTZ = [datetime.getHours(), datetime.getMinutes()].join(":");
  const [hourEnd, minute_AMPMEnd] = endTime.split(":");
  const [minuteEnd] = minute_AMPMEnd.split(" ");
  datetime.setUTCHours(hourEnd);
  datetime.setUTCMinutes(minuteEnd);
  const endTimeTZ = [datetime.getHours(), datetime.getMinutes()].join(":");
  return [dateTZ, startTimeTZ, endTimeTZ];
};

function BookingCard({ cancelButton, booking, render }) {
  const [date, startTime, endTime] = parseBookingDateTimes(booking);
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
            <b>Date : </b> {date}{" "}
          </ListGroup.Item>
          <ListGroup.Item>
            <b>Time From : </b> {startTime} <b> To : </b> {endTime}
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
