import React, { Component } from "react";
import ApiService from "../api/ApiService";
import { withRouter } from "react-router-dom";

const formatPad = (num) => String(num).padStart("2", "0");

class Booking extends Component {
  constructor() {
    super();
    this.state = {
      service: "",
      employee: "",
      date: "",
      time: "",
      schemas: [],
      employees: [],
      starttime: "",
      endtime: "",
      message: "",
      maxDate: "",
      minDate: "",
      availableTimes: [],
      startHour: 8,
      endHour: 18,
    };
  }
  calculateDateRange = () => {
    const formatDate = (d) =>
      `${d.getFullYear()}-${formatPad(d.getMonth() + 1)}-${formatPad(
        d.getDate()
      )}`;
    const now = new Date();
    now.setDate(now.getDate() + 1);
    const in7 = new Date(now);
    in7.setDate(now.getDate() + 6);
    this.setState({ maxDate: formatDate(in7), minDate: formatDate(now) });
  };

  componentDidMount() {
    this.calculateDateRange();
    this.reloadServiceList();
  }

  reloadServiceList = () => {
    ApiService.fetchServices(this).then((res) => {
      const schemas = Array.from(res.data.body);
      this.setState({ schemas }, this.reloadEmployeeList);
    });
  };

  reloadEmployeeList = () => {
    if (this.state.service) {
      ApiService.fetchEmployeeByService(this, {
        service_id: Number(this.state.service),
      }).then((res) => {
        this.setState({
          employees: Array.from(res.data.body),
          employee: "",
          date: "",
          time: "",
        });
      });
    }
  };

  getBookedTimeslots = () => {
    if (this.state.employee) {
      const employee = {
        userID: this.state.employee,
        date: this.state.date,
      };
      ApiService.getBookedT &&
        this.state.dateslots(this, employee).then((res) => {
          const timeslots = Array.from(res.data.body.bookedTimes);
          this.generateAvailableTimes(timeslots);
        });
        });
    }
  };

  onChange = (e) => {
    const { name, value } = e.target;
    if (name === "employee" && !this.state.service) return;
    if (name === "date" && !this.state.employee) return;
    if (name === "time" && !this.state.date) return;
    this.setState({ [name]: value }, () => {
      if (name === "service") {
        this.reloadEmployeeList();
      }
      if (name === "date") {
        this.getBookedTimeslots();
      }
      if (name === "time") {
        this.setTimeslot(value);
      }
    });
  };

  /**
   * @param {Date} date object to format
   * @return {string} with format like this '2020-09-07T17:00+10:00'
   */
  formatDate = (date) => {
    const year = date.getUTCFullYear();
    const month = formatPad(date.getUTCMonth());
    const day = formatPad(date.getUTCDate());
    const hour = formatPad(date.getUTCHours());
    const minute = formatPad(date.getUTCMinutes());
    return `${year}-${month}-${day}T${hour}:${minute}+00:00`;
  };

  generateAvailableTimes = (excludedTimeslots) => {
    const formatTime = (time) => `${time.getHours()}:${time.getMinutes()}`;
    const availableTimes = [];
    const service = this.state.schemas[this.state.service];
    for (
      let time = new Date(1111, 11, 11, this.state.startHour);
      time.getHours() < this.state.endHour;

    ) {
      const start = formatTime(time);
      time.setMinutes(time.getMinutes() + service.length);
      const end = formatTime(time);
      availableTimes.push({ start, end });
    }
    this.setState({ availableTimes });
  };

  setTimeslot = (index) => {
    debugger;
    const timeslot = this.state.availableTimes[index];
    this.setState({ starttime: timeslot.start, endtime: timeslot.end });
  };

  onsubmit = (e) => {
    e.preventDefault();
    const date = this.state.date.split("-").map(Number);
    const start_time = this.state.starttime.split(":").map(Number);
    const end_time = this.state.endtime.split(":").map(Number);
    const start_date = new Date(...date, ...start_time);
    const end_date = new Date(...date, ...end_time);
    const booking = {
      startDateTime: this.formatDate(start_date),
      endDateTime: this.formatDate(end_date),
      user_ids: [Number(this.state.employee)],
    };
    debugger;
    ApiService.createBooking(this, booking).then((res) => {
      console.log(booking);
      this.setState({ message: "Booking Created!" });
      this.props.history.push("/activebookings");
    });
    console.log(this.state.message);
  };

  render() {
    const { schemas, employees, availableTimes } = this.state;

    return (
      <>
        <div>
          <form
            className="booking-form"
            onSubmit={this.onsubmit}
            style={{ overflow: "hidden" }}
          >
            <div className="row booking-form-row">
              <div className="col-md-7 ml-auto mr-auto margin-offset">
                <h3>Create A Booking</h3>
                <br />
                <div className="form-row">
                  <div className="form-group col-md-3">
                    <label>Service :</label>
                    <select
                      name="service"
                      value={this.state.service}
                      onChange={this.onChange}
                      className="form-control"
                    >
                      {this.state.service === "" && (
                        <option value="">select service</option>
                      )}
                      {schemas &&
                        schemas.length > 0 &&
                        schemas.map((schema) => (
                          <option key={schema.id} value={schema.id}>
                            {schema.name}
                          </option>
                        ))}
                    </select>
                  </div>
                  <div className="form-group col-md-3">
                    <label>Employees :</label>
                    <select
                      name="employee"
                      value={this.state.employee}
                      onChange={this.onChange}
                      className="form-control"
                    >
                      {this.state.service === "" ? (
                        <option value=""></option>
                      ) : (
                        this.state.employee === "" && (
                          <option value="">select employee</option>
                        )
                      )}
                      {employees && employees.length > 0 ? (
                        employees.map((employee) => (
                          <option key={employee.id} value={employee.id}>
                            {employee.name}
                          </option>
                        ))
                      ) : (
                        <option disabled>no employees</option>
                      )}
                    </select>
                  </div>
                  <div className="form-group col-md-3">
                    <label>Date :</label>
                    <input
                      type="date"
                      className="form-control"
                      name="date"
                      max={this.state.maxDate}
                      min={this.state.minDate}
                      value={this.state.date}
                      onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group col-md-3">
                    <label>Time :</label>
                    <select
                      name="time"
                      value={this.state.time}
                      onChange={this.onChange}
                      className="form-control"
                    >
                      {this.state.date === "" ? (
                        <option value=""></option>
                      ) : (
                        this.state.time === "" && (
                          <option value="">select time</option>
                        )
                      )}
                      {availableTimes && availableTimes.length > 0 ? (
                        availableTimes.map((time, i) => (
                          <option key={time.start} value={i}>
                            {time.start} to {time.end}
                          </option>
                        ))
                      ) : (
                        <option disabled>
                          no time slots on {this.state.date}
                        </option>
                      )}
                    </select>
                  </div>
                </div>
                <div className="form-row">
                  <div className="col-md-12 text-center">
                    <button
                      type="submit"
                      className="btn btn-primary"
                      onClick={this.onsubmit}
                    >
                      Create Booking
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </>
    );
  }
}

export default withRouter(Booking);
