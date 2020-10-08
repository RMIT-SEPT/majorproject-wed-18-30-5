import React, { Component } from "react";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import NavBarAdmin from "./AdminNav";
import ApiService from "../../api/ApiService";

function ActionsCell({ column, i }) {
  return (
    <TableCell>
      {Object.keys(column.actions).map((key) => (
        <button
          key={key}
          className="btn badge badge-success"
          onClick={() => column.actions[key](i)}
          style={{ margin: "5px" }}
        >
          {key}
        </button>
      ))}
    </TableCell>
  );
}

function DataCell({ column, row }) {
  const value = row[column.id];
  return (
    <TableCell key={column.id} align={column.align}>
      {value && column.format ? column.format(value) : value}
    </TableCell>
  );
}

class EmployeeInfo extends Component {
  constructor() {
    super();
    this.state = {
      employee: "",
      employees: [],
    };
    this.columns = [
      {
        id: "actions",
        label: "actions",
        minWidth: 170,
        actions: {
          "add schedule": this.addSchedule,
          "edit info": this.editInfo,
        },
      },
      {
        id: "name",
        label: "Name",
        minWidth: 170,
        align: "right",
        format: (value) => value.toLocaleString("en-US"),
      },
      {
        id: "username",
        label: "UserName",
        minWidth: 170,
        align: "right",
        format: (value) => value.toLocaleString("en-US"),
      },
      {
        id: "address",
        label: "Address",
        minWidth: 170,
        align: "right",
        format: (value) => value.toLocaleString("en-US"),
      },
      {
        id: "contactNumber",
        label: "Contact Number",
        minWidth: 170,
        align: "right",
        format: (value) => value.toLocaleString("en-US"),
      },
    ];
  }

  componentDidMount() {
    this.reloadEmpList();
  }

  addSchedule = (i) => {
    this.props.history.push({
      pathname: "/addschedule",
      search:
        "?" +
        new URLSearchParams({ empId: this.state.employees[i].id }).toString(),
    });
  };

  editInfo = (i) => {
    this.props.history.push({
      pathname: "/empedit",
      search:
        "?" +
        new URLSearchParams({ empId: this.state.employees[i].id }).toString(),
    });
  };

  reloadEmpList = () => {
    ApiService.fetchEmployees(this).then((res) => {
      this.setState({ employees: Array.from(res.data.body) });
    });
  };

  createData = (name, username, address, contactnumber) => {
    return { name, username, address, contactnumber };
  };

  render() {
    const { employees } = this.state;

    return (
      <>
        <header>
          <NavBarAdmin />
        </header>
        <Paper style={{ width: "100%", backgroundColor: "white" }}>
          <TableContainer
          // style={{ height: "75vh" }}
          >
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {this.columns.map((column) => (
                    <TableCell
                      key={column.id}
                      align={column.align}
                      style={{ minWidth: column.minWidth }}
                    >
                      {column.label}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {employees
                  // .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => {
                    return (
                      <TableRow
                        hover
                        role="checkbox"
                        tabIndex={-1}
                        key={row.id}
                      >
                        {this.columns.map((column, i) =>
                          column.id == "actions" ? (
                            <ActionsCell
                              key={column.id}
                              column={column}
                              i={i}
                            />
                          ) : (
                            <DataCell
                              key={column.id}
                              column={column}
                              row={row}
                            />
                          )
                        )}
                      </TableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
          {/* <TablePagination
            // rowsPerPageOptions={[10, 25, 100]}
            component="div"
            count={rows.length}
            // rowsPerPage={rowsPerPage}
            // page={page}
            // onChangePage={handleChangePage}
            // onChangeRowsPerPage={handleChangeRowsPerPage}
          /> */}
        </Paper>
      </>
    );
  }
}

export default EmployeeInfo;
