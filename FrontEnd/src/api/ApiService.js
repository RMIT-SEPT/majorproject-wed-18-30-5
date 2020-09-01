import axios from 'axios';

const USER_API_BASE_URL = 'http://localhost:8080/user';

class ApiService {

    fetchUsers() {
        return axios.get('http://localhost:8080/api/user/getEmployees');
    }

    fetchUserById(userId) {
        return axios.get(USER_API_BASE_URL + '/' + userId);
    }

    deleteUser(userId) {
        return axios.delete(USER_API_BASE_URL + '/' + userId);
    }

    addUser(user) {
        return axios.post('http://localhost:8080/api/user/createCustomer', user, {
            headers: {
                // Overwrite Axios's automatically set Content-Type
                'Content-Type': 'application/json'
            }
        });
    }


    editUser(user) {
        return axios.put(USER_API_BASE_URL + '/' + user.id, user);
    }

}

export default new ApiService();