import React, { Component } from "react";
import ApiService from "../api/ApiService";

class Signup extends Component {
  constructor() {
    super();

    this.state = {
      name: "",
      username: "",
      password: "",
      contactNumber: "",
      address: "",
      message: "",
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }
  onSubmit(e) {
    e.preventDefault();
    const newPerson = {
      name: this.state.name,
      username: this.state.username,
      password: this.state.password,
      contactNumber: this.state.contactNumber,
      address: this.state.address,
    };

    console.log(newPerson);
  }

  addUser = (e) => {
    e.preventDefault();
    let user = {
      name: this.state.name,
      username: this.state.username,
      password: this.state.password,
      contactNumber: this.state.contactNumber,
    };
    ApiService.addUser(this, user)
      .then((res) => {
        this.setState({ message: "User added successfully." });
        this.props.history.push("/login");
      })
      .catch(console.log);
  };

  render() {
    return (
      <div className="Person">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">SignUp</h5>
              <hr />
              <div>{this.state.message}</div>
              <form onSubmit={this.onSubmit}>
                <h6>Full Name</h6>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg "
                    placeholder="Full Name"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Username</h6>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="UserName"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Password</h6>
                <div className="form-group">
                  <input
                    type="password"
                    className="form-control form-control-lg"
                    placeholder="Password"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Contact Number</h6>
                <div className="form-group">
                  <input
                    type="number"
                    className="form-control form-control-lg"
                    name="contactNumber"
                    placeholder="Contact Number"
                    value={this.state.contactNumber}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Address</h6>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    name="Address"
                    placeholder="Address"
                    value={this.state.Address}
                    onChange={this.onChange}
                  />
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                  onClick={this.addUser}
                />
                <a href="/login">Back</a>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Signup;
