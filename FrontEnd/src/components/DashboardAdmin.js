import React, { Component } from "react";
import AdminNav from "./AdminNav";
import { Table, Card, CardDeck } from "react-bootstrap";
import ApiService from "../api/ApiService";


class DashboardAdmin extends Component {
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
                  
                <button className="ml-auto submit approve-button" onClick={(e) => this.handleButtonClick(e)}>Approve</button>
                <button className="ml-auto submit reject-button" onClick={(e) => this.handleButtonClick(e)}>Disapprove</button>
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

export default DashboardAdmin;