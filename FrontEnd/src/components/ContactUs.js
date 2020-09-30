import React, { Component } from "react";
import ContactForm from "./ContactForm";
import HomeNav from "./HomeNav";
import Map from "./Map";

export default class ContactUs extends Component {
  render() {
    return (
      <>
        <header>
          <HomeNav />
        </header>
        <Map />
        <ContactForm className="ml-auto mr-auto contact-form-wr" />
      </>
    );
  }
}
