import React, { Component } from "react";
import AdminNav from "./AdminNav";
import { Table, Card, CardDeck } from "react-bootstrap";
import ApiService from "../api/ApiService";


class PastBookings extends Component {
    constructor(props) {
        super(props);
        this.state = {
          pastbookings: [],
          message: null,
        };
      }
    
      
    
  render() {
    return (
      <>
        <header>
          <AdminNav />
        </header>
        <div className="pending-booking-wrapper">
        <p>Past 7 Days Bookings</p>
        <form>
          <CardDeck>
            
              <Card>
                {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
                <Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    
                    <ul>
                      <li><lable>Customer Name : </lable></li>
                      <li><lable>Employee Name : </lable></li>
                      <li><lable>Start-Date : </lable></li>
                      <li><lable>End-Date : </lable></li>
                    </ul>
                    
                  </Card.Text>
                  
                </Card.Body>
                <Card.Footer>
                  <small className="text-muted">Last updated 3 mins ago</small>
                </Card.Footer>
              </Card>
            
          </CardDeck>
          
        </form>
        </div>
      </>
    );
  }
}

export default PastBookings;