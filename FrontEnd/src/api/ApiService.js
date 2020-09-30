import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080/user";

const handleLoginRedirect = (component) => {
  return (res) => {
    if (res.request.responseURL === "http://localhost:8080/login") {
      component.props.history.push("/login");
      return Promise.reject({});
    }
    return Promise.resolve(res);
  };
};

class ApiService {
  fetchUsers(component) {
    return axios
      .get("http://localhost:8080/api/user/getEmployees", {
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
      .post("http://localhost:8080/api/user/createCustomer", user, {
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
      .get("http://localhost:8080/api/booking/getUpcomingBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchPastBookings(component) {
    return axios
      .get("http://localhost:8080/api/booking/getCompletedBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchServices(component) {
    return axios
      .get(" http://localhost:8080/api/service/getServices", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  /**
   * service: { service_id: <Number> }
   */
  fetchEmployeeByService(component, service) {
    return axios
      .post(" http://localhost:8080/api/user/getEmployeesByService", service, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  createBooking(component, booking) {
    return axios
      .post("http://localhost:8080/api/booking/createBooking", booking, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchEmployees(component) {
    return axios
      .get(" http://localhost:8080/api/user/getEmployees", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  fetchAdminBookings(component) {
    return axios
      .get("http://localhost:8080/api/booking/getAdminBookings", {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }

  approveBookingStatus(component, id) {
    return axios
      .patch("http://localhost:8080/api/booking/approveBooking", id, {
        withCredentials: true,
      })
      .then(handleLoginRedirect(component));
  }
}

export default new ApiService();
