import React, { Component } from "react";
import ApiService from "../api/ApiService";

class Home extends Component {

  constructor(props) {
    super(props)
    this.state = {
      users: [],
      message: null
    }

  }

  componentDidMount() {
    this.reloadUserList();
  }

  reloadUserList = () => {
    ApiService.fetchUsers()
      .then((res) => {
        this.setState({ users: res.data.body })
      });
  }

  deleteUser = (userId) => {
    ApiService.deleteUser(userId)
      .then(res => {
        this.setState({ message: 'User deleted successfully.' });
        this.setState({ users: this.state.users.filter(user => user.id !== userId) });
      })

  }

  editUser = (id) => {
    window.localStorage.setItem("userId", id);
    this.props.history.push('/edit-user');
  }

  addUser = () => {
    window.localStorage.removeItem("userId");
    this.props.history.push('/add-user');
  }
  render() {
    return (
      <div>
        <h2 className="text-center">User Details</h2>
        <button className="btn btn-danger" onClick={() => this.addUser()}> Add User</button>
        <table className="table table-striped">
          <thead>
            <tr>
              <th className="hidden">Id</th>
              <th>Name</th>
              <th>Username</th>
              <th>Password</th>
              <th>ContactNumber</th>
            </tr>
          </thead>
          <tbody>
            {
              this.state.users.map(
                user =>
                  <tr key={user.username}>
                    <td>{user.name}</td>

                    <td>{user.password}</td>
                    <td>{user.contactNumber}</td>
                    <td>
                      <button className="btn btn-success" onClick={() => this.deleteUser(user.id)}> Delete</button>
                      <button className="btn btn-success" onClick={() => this.editUser(user.id)}> Edit</button>
                    </td>
                  </tr>
              )
            }
          </tbody>
        </table>

      </div>
    );
  }

}

export default Home;