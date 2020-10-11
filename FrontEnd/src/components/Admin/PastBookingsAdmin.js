// import React, { Component } from "react";
// import AdminNav from "./AdminNav";
// import { CardColumns } from "react-bootstrap";
// import ApiService from "../../api/ApiService";
// import BookingCard from "../BookingCard";

// class DashboardAdmin extends Component {
//   constructor(props) {
//     super(props);
//     this.state = {
//       pastbookings: [],
//       message: null,
//     };
//   }

//   componentDidMount() {
//     this.reloadBookingList();
//   }

//   refreshPage() {
//     window.location.reload(false);
//   }

//   reloadBookingList = () => {
//     ApiService.fetchAdminBookings(this).then((res) => {
//       this.setState({ pastbookings: Array.from(res.data.body.bookings) });
//       debugger;
//     });
//   };

//   render() {
//     return (
//       <>
//         <header>
//           <AdminNav />
//         </header>
//         <div className="active-booking-wrapper">
//           <CardColumns>
//             {this.state.bookings.map((booking) => (
//               <BookingCard key={pastbooking.bookingID} booking={booking} />
//             ))}
//           </CardColumns>
//         </div>
//       </>
//     );
//   }
// }

// export default DashboardAdmin;
