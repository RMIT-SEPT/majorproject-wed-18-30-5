import React, { Component } from "react";
import ApiService from "../api/ApiService";
import { withRouter } from "react-router-dom";
import "../App.css";

class Booking extends Component {
  constructor() {
    super();
    this.state = {
      service: "",
      employee: "",
      schemas: [],
      employees: [],
      startdate: "",
      starttime: "",
      endtime: "",
      date: "",
      message: "",
    };
  }

  componentDidMount() {
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
        this.setState({ employees: Array.from(res.data.body) });
      });
    }
  };

  onChange = (e) => {
    const { name, value } = e.target;
    this.setState({ [name]: value }, () => {
      if (name === "service") {
        this.reloadEmployeeList();
      }
    });
  };

  /**
   * @param {Date} date object to format
   * @return {string} with format like this '2020-09-07T17:00+10:00'
   */
  formatDate = (date) => {
    const formatPad = (num) => String(num).padStart("2", "0");
    const year = date.getUTCFullYear();
    const month = formatPad(date.getUTCMonth());
    const day = formatPad(date.getUTCDate());
    const hour = formatPad(date.getUTCHours());
    const minute = formatPad(date.getUTCMinutes());
    return `${year}-${month}-${day}T${hour}:${minute}+00:00`;
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
      user_ids: [Number(5)],
    };
    ApiService.createBooking(this, booking).then((res) => {
      console.log(booking);
      this.setState({ message: "Booking Created!" });
      this.props.history.push("/activebookings");
    });
    console.log(this.state.message);
  };

  render() {
    const { schemas, employees } = this.state;
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
                        <option value="">Select A Service</option>
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
                  <div className="form-group col-md-2">
                    <label>Employees :</label>
                    <select
                      name="employee"
                      value={this.state.employee}
                      onChange={this.onChange}
                      className="form-control"
                    >
                      {this.state.service === "" ? (
                        <option value=""></option>
                      ) : employees && employees.length > 0 ? (
                        employees.map((employee) => (
                          <option key={employee.id} value={employee.id}>
                            {employee.name}
                          </option>
                        ))
                      ) : (
                        <option disabled>No Employees</option>
                      )}
                    </select>
                  </div>
                  <div className="form-group col-md-3">
                    <label>Date :</label>
                    <input
                      type="date"
                      className="form-control"
                      name="date"
                      value={this.state.date}
                      onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group col-md-2">
                    <label>Start Time :</label>
                    <input
                      type="time"
                      className="form-control"
                      name="starttime"
                      value={this.state.starttime}
                      onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group col-md-2">
                    <label>End Time :</label>
                    <input
                      type="time"
                      className="form-control"
                      name="endtime"
                      value={this.state.endtime}
                      onChange={this.onChange}
                    />
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
