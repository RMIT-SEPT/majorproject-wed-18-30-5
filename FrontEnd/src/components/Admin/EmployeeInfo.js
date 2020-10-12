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

function ActionsCell({ column, index, key }) {
  return (
    <TableCell key={key}>
      {column.actions.map(({ fn, label }) => (
        <a
          key={label}
          className="btn badge-danger"
          href={fn(index)}
          style={{ margin: "5px" }}
        >
          {label}
        </a>
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
        label: "",
        minWidth: 170,
        actions: [
          {
            fn: this.addSchedule,
            label: "Add Schedule",
          },
          {
            fn: this.editInfo,
            label: "Edit Info",
          },
        ],
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
    return (
      "/empschedule?" +
      new URLSearchParams({ empId: this.state.employees[i].id }).toString()
    );
  };

  editInfo = (i) => {
    return (
      "/editemp?" +
      new URLSearchParams({ empId: this.state.employees[i].id }).toString()
    );
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
                  .map((row, i) => {
                    return (
                      <TableRow
                        hover
                        role="checkbox"
                        tabIndex={-1}
                        key={row.id}
                      >
                        {this.columns.map((column) =>
                          column.id == "actions" ? (
                            <ActionsCell
                              key={column.id}
                              column={column}
                              index={i}
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
