import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import TimePicker from "react-time-picker";
import NavBarAdmin from "./AdminNav";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

class MyApp extends Component {
  state = {
    date: new Date(),
    time: "10:00",
  };

  onChange = (date, time) => this.setState({ date, time });
  //onChange = (time) => this.setState({ time });

  render() {
    return (
      <>
        <header>
          <NavBarAdmin />
        </header>
        <Datetime />;
        <div className="col-md-6 mx-auto text-center">
          <div className="header-title">
            <h1 className="wv-heading--title">Editng Schedule for Employee:</h1>
          </div>
        </div>
        <Container className="calender-cmp" fluid="xl">
          <Row noGutters="true">
            <Col md="5">
              <h2>Dates:</h2>
              <Calendar
                className="text-center"
                onChange={this.onChange}
                value={this.state.date}
                allowPartialRange={"true"}
                selectRange={"true"}
              />
            </Col>
            <Col className="time-cmp" md="3">
              <h2>Start Time:</h2>
              <TimePicker
                onChange={this.onChange}
                value={this.state.time}
                className="text-center"
              />
            </Col>
            <Col className="time-cmp" md="3">
              <h2>End Time:</h2>
              <TimePicker
                onChange={this.onChange}
                value={this.state.time}
                className="text-center"
              />
            </Col>
          </Row>
        </Container>
        <div className="text-center ">
          <button type="submit" className=" btn btn-danger">
            Add Schedule
          </button>
        </div>
      </>
    );
  }
}

export default MyApp;
