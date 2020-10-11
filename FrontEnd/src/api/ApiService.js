import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080";

const handleLoginRedirect = (component) => {
  return (res) => {
    if (res.request.responseURL === USER_API_BASE_URL + "/login") {
      component.props.history.push("/login");
      return Promise.reject({});
    }
    return Promise.resolve(res);
  };
};

class ApiService {
  fetchUsers(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/user/getEmployees", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchUserById(component, userId) {
    return axios
      .get(USER_API_BASE_URL + "/" + userId, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  deleteUser(component, userId) {
    return axios
      .delete(USER_API_BASE_URL + "/" + userId, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  addUser(component, user) {
    return axios
      .post(USER_API_BASE_URL + "/api/user/createCustomer", user, {
        headers: {
          // Overwrite Axios's automatically set Content-Type
          "Content-Type": "application/json",
        },
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  editUser(component, user) {
    return axios
      .put(USER_API_BASE_URL + "/" + user.id, user, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchBookings(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/booking/getUpcomingBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchPastBookings(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/booking/getCompletedBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchServices(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/service/getServices", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  /**
   * service: { service_id: <Number> }
   */
  fetchEmployeeByService(component, service) {
    return axios
      .post(USER_API_BASE_URL + "/api/user/getEmployeesByService", service, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  createBooking(component, booking) {
    return axios
      .post(USER_API_BASE_URL + "/api/booking/createBooking", booking, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchEmployees(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/user/getEmployees", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchAdminBookings(component) {
    return axios
      .get(USER_API_BASE_URL + "/api/booking/getAllBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  approveBookingStatus(component, id) {
    return axios
      .patch(USER_API_BASE_URL + "/api/booking/approveBooking", id, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }
  cancelBooking(component, id) {
    return axios
      .patch(USER_API_BASE_URL + "/api/booking/cancelBooking", id, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  getBookedTimeslots(component, employee) {
    return axios
      .post(USER_API_BASE_URL + "/api/booking/getBookedTimeslots", employee, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }
}

export default new ApiService();
