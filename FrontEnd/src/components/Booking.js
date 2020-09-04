import React, { Component } from "react";
import ApiService from "../api/ApiService";

class Booking extends Component {
  constructor() {
    super();
    this.state = {
      service: "",
      employee: "",
      schemas: [],
      employees: [],
      date: "",
      time: "",
    };
    // this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    this.reloadServiceList();
    // this.reloadEmployeeList();
  }

  reloadServiceList = () => {
    ApiService.fetchServices(this).then((res) => {
      const schemas = Array.from(res.data.body);
      this.setState({ schemas }, this.reloadEmployeeList);
    });
  };

  reloadEmployeeList = () => {
    if (this.state.service) {
      ApiService.fetchEmployeeByService(this, { service_id: Number(this.state.service) }).then((res) => {
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
  }

  onsubmit = (e) => {
    e.preventDefault();
    const booking = {
      date: this.state.date,
      start_time: this.state.start_time,
      end_time: this.state.end_time
    };
    console.log(booking);
  }

  render() {
    const { schemas, employees } = this.state;
    return (
      <>
        <div>
          <form className="booking-form" onSubmit={this.onsubmit}>
            <div className="row">
              <div className="col-md-6 offset-md-3">
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
                      {this.state.service === "" && <option value="">Select A Service</option>}
                      {schemas &&
                        schemas.length > 0 &&
                        schemas.map((schema) => (
                          <option key={schema.id} value={schema.id}>{schema.name}</option>
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
                      ) : (employees &&
                        employees.length > 0 ? (
                          employees.map((employee) => (
                            <option key={employee.id} value={employee.id}>{employee.name}</option>
                          ))) : (
                          <option disabled>No Employees</option>
                        ))}

                    </select>
                  </div>
                  <div className="form-group col-md-3" >
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
                      onClick={() => this.submit()}
                    >
                      Submit
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

export default Booking;
