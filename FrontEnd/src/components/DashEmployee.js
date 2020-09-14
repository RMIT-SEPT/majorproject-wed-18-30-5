import React, { Component } from "react";
import EmployeeNav from "./EmployeeNav";
import { Table, Card, CardDeck } from "react-bootstrap";
import ApiService from "../api/ApiService";



class DashEmployee extends Component {
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
          <EmployeeNav />
        </header>
        <div className="pending-booking-wrapper">
            <p>My schedule</p>
        <form>
        
          <CardDeck>
        
              <Card>
                {/* <Card.Img variant="top" src="holder.js/100px160" /> */}
                <Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    <ul>
                      <li><lable>Customer Name : </lable></li>
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

export default DashEmployee;