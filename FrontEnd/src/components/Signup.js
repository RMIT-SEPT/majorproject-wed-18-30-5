import React, { Component } from "react";
import ApiService from "../api/ApiService";
import HomeNav from "./HomeNav";

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
      <>
        <header>
          <HomeNav />
        </header>
        <div className="container">
          <div className="col-md-6 mx-auto text-center">
            <div className="header-title">
              <h1 className="wv-heading--title">Sign Up — it’s free!</h1>
            </div>
          </div>
          <div className="row">
            <div className="col-md-4 mx-auto">
              <div className="myform form ">
                <form action="" method="post" name="signup">
                  <div className="form-group">
                    <input
                      type="text"
                      name="name"
                      className="form-control my-input"
                      id="name"
                      placeholder="Name"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      name="username"
                      className="form-control my-input"
                      id="username"
                      placeholder="Username"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="password"
                      min="0"
                      name="password"
                      id="password"
                      className="form-control my-input"
                      placeholder="Password"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="number"
                      min="0"
                      name="contactnumber"
                      id="contactnumber"
                      className="form-control my-input"
                      placeholder="Contact Number"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      min="0"
                      name="address"
                      id="address"
                      className="form-control my-input"
                      placeholder="Address"
                    />
                  </div>
                  <div className="text-center ">
                    <button type="submit" className=" btn btn-block g-button">
                      Create Your Free Account
                    </button>
                  </div>
                  <div className="col-md-12 ">
                    <div className="login-or">
                      <hr className="hr-or" />
                    </div>
                  </div>

                  <p className="small mt-3">
                    By signing up, you are indicating that you have read and
                    agree to the{" "}
                    <a href="#" className="ps-hero__content__link">
                      Terms of Use
                    </a>{" "}
                    and <a href="#">Privacy Policy</a>.
                  </p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}
export default Signup;
