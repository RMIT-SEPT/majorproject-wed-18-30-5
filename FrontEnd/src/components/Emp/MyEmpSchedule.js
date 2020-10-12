import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import EmployeeNav from "./EmployeeNav";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";

class MyEmpSchedule extends Component {
  state = {
    date: new Date(),
    time: "10:00",
  };

  onChange = (date, time) => this.setState({ date, time });

  render() {
    return (
      <>
        <header>
          <EmployeeNav />
        </header>
        <Datetime />;
        <div className="col-md-6 mx-auto text-center">
          <div className="header-title">
            <h1 className="wv-heading--title">My Schedule</h1>
          </div>
        </div>
        <div>
          <Calendar
            className="text-center ml-auto mr-auto"
            onChange={this.onChange}
            value={this.state.date}
          />
        </div>
      </>
    );
  }
}

export default MyEmpSchedule;
