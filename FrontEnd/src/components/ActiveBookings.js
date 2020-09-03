import React, { Component } from 'react'
import { Table, Card, CardDeck } from 'react-bootstrap';
import NavBar from "./NavBar";
import ApiService from "../api/ApiService";

export default class ActiveBookings extends Component {
    constructor(props) {
        super(props)
        this.state = {
            bookings: [],
            message: null
        }
        this.reloadBookingList = this.reloadBookingList.bind(this);
    }

    componentDidMount() {
        this.reloadBookingList();
    }

    reloadBookingList() {
        ApiService.fetchBookings()
            .then((res) => {
                this.setState({ bookings: res.data.result })
            });
    }

    render() {
        return (
            <>
                <header>
                    <NavBar />
                </header>
                <div className="active-booking-wrapper">
                    <CardDeck>
                        <Card>
                            <Card.Img variant="top" src="holder.js/100px160" />
                            <Card.Body>
                                <Card.Title>Service Name</Card.Title>
                                <Card.Text>
                                    <ul>
                                        <li>Customer Name : </li>
                                        <li>Employee Name : </li>
                                        <li>Start-Date : </li>
                                        <li>End-Date : </li>
                                    </ul>
                                </Card.Text>
                            </Card.Body>
                            <Card.Footer>
                                <small className="text-muted">Last updated 3 mins ago</small>
                            </Card.Footer>
                        </Card>
                        <Card>
                            <Card.Img variant="top" src="holder.js/100px160" />
                            <Card.Body>
                                <Card.Title>Service Name</Card.Title>
                                <Card.Text>
                                    <ul>
                                        <li>Customer Name : </li>
                                        <li>Employee Name : </li>
                                        <li>Start-Date : </li>
                                        <li>End-Date : </li>
                                    </ul>
                                </Card.Text>
                            </Card.Body>
                            <Card.Footer>
                                <small className="text-muted">Last updated 3 mins ago</small>
                            </Card.Footer>
                        </Card>
                        <Card>
                            <Card.Img variant="top" src="holder.js/100px160" />
                            <Card.Body>
                                <Card.Title>Service Name</Card.Title>
                                <Card.Text>
                                    <ul>
                                        <li>Customer Name : </li>
                                        <li>Employee Name : </li>
                                        <li>Start-Date : </li>
                                        <li>End-Date : </li>
                                    </ul>
                                </Card.Text>
                            </Card.Body>
                            <Card.Footer>
                                <small className="text-muted">Last updated 3 mins ago</small>
                            </Card.Footer>
                        </Card>
                    </CardDeck>
                </div>
            </>
        )
    }
}
