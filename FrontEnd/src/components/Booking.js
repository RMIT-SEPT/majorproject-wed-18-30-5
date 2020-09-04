import React, { Component } from "react";

class Booking extends Component {
  constructor() {
    super();
    this.state = {
      date: "",
      time: "",

    };
    this.onChange = this.onChange.bind(this);
    // this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onsubmit(e) {
    e.preventDefault();
    const booking = {
      date: this.state.date,
      time: this.state.time
    }
    console.log(booking);
  }



  render() {
    return (
      <>
        <div>
          <form className="booking-form" onSubmit={this.onsubmit}>
            <div className="row">
              <div className="col-md-6 offset-md-3">
                <h3>Create A Booking</h3><br />
                <div className="form-row">
                  <div className="form-group col-md-3">
                    <label>Service :</label>
                    <select className="form-control" name="city" onChange={this.handleInputChange}>
                      <option value="1">Hairdresser</option>
                      <option value="2">Gardner</option>
                      <option value="3">Plumber</option>
                    </select>
                  </div>
                  <div className="form-group col-md-3">
                    <label>Freelancers :</label>
                    <select className="form-control" name="city" onChange={this.handleInputChange}>
                      <option value="1">city 1</option>
                      <option value="2">city 2</option>
                      <option value="3">city 3</option>
                    </select>
                  </div>
                  <div className="form-group col-md-3">
                    <label>Date :</label>
                    <input type="date" className="form-control"
                      name="date"
                      value={this.state.date}
                      onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group col-md-3">
                    <label>Time :</label>
                    <input type="time" className="form-control"
                      name="time"
                      value={this.state.time}
                      onChange={this.onChange}
                    />
                  </div>
                </div>
                <div className="form-row">
                  <div className="col-md-12 text-center">
                    <button type="submit" className="btn btn-primary" onClick={() => this.submit()}>Submit</button>
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
