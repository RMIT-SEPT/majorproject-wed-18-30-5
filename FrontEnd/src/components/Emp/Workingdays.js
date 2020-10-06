import React, { Component } from 'react';
import EmployeeNav from './EmployeeNav';
import ApiService from "../../api/ApiService";
import ReactTable from 'react-table-6';
import 'react-table-6/react-table.css'

class Workingdays extends Component {
    constructor(props) {
        super(props);
        this.state = {
          bookings: [],
          message: null,
        };
      }
    componentDidMount() {
        this.reloadBookingList();
    }
    
    reloadBookingList = () => {
        ApiService.fetchBookings(this).then((res) => {
        debugger;
        this.setState({ bookings: Array.from(res.data.body.bookings) });
        });
    };
    
    render() {
        // const data = this.state.props.workingTime;
        const columns = [
           {
               Header: "Monday",
               accessor: "Monday"
           },
           {
            Header: "Tuesday",
            accessor: "Tuesday"
            },
            {
                Header: "Wednesday",
                accessor: "Wednesday"
            },
            {
                Header: "Thursday",
                accessor: "Thursday"
            },
            {
                Header: "Friday",
                accessor: "Friday"
            },
            {
                Header: "Saturday",
                accessor: "Saturday"
            },
            {
                Header: "Sunday",
                accessor: "Sunday"
            },


        ];
    
    return(
        
        <>
        <header>
            <EmployeeNav />
        </header>
        <body>
            <ReactTable
                columns={columns}
                
            >
            {this.state.WorkingTime}
            </ReactTable>
        </body>
        </>
    )
}

}

export default Workingdays;