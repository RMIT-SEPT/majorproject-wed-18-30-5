import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import NavBarAdmin from "./AdminNav";

class MyApp extends Component {
  state = {
    date: new Date(),
  };

  onChange = (date) => this.setState({ date });

  render() {
    return (
      <>
        <header>
          <NavBarAdmin />
        </header>

        <div className="col-md-6 mx-auto text-center">
          <div className="header-title">
            <h1 className="wv-heading--title">Editng Schedule for Employee:</h1>
          </div>
        </div>

        <div className="calender-div">
          <Calendar
            className="calender-emp text-center ml-auto mr-auto"
            onChange={this.onChange}
            value={this.state.date}
            allowPartialRange={"true"}
            selectRange={"true"}
          />
        </div>
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
