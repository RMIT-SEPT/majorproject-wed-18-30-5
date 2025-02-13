import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import TimePicker from "react-time-picker";
import NavBarAdmin from "./AdminNav";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ApiService from "../../api/ApiService";
import { formatDate as formatFullDate } from "../../utils";
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  paper: {
    position: "absolute",
    width: 400,
    backgroundColor: theme.palette.background.paper,
    border: "1px solid #000",
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

function DayModal({ open, handleClose, body }) {
  const classes = useStyles();
  const [modalStyle] = React.useState(getModalStyle);

  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        {body(classes, modalStyle)}
      </Modal>
    </div>
  );
}

class EmpSchedule extends Component {
  constructor() {
    super();
    this.state = {
      date: null,
      startTime: null,
      endTime: null,
      schedule: "",
      message: null,
    };
  }

  onChange = (e) => {
    const { name, value } = e.target;

    this.setState({ [name]: value });
  };

  createSchedule = (data) => {
    ApiService.createSchedule(this, data)
      .then((res) => {
        this.setState({ showmodal: true });
      })
      .catch((e) => {
        alert(e.response.data.errors);
      });
  };

  getEmpId = () => {
    const params = new URL(document.location).searchParams;
    return params.get("empId");
  };

  onSubmit = (e) => {
    e.preventDefault();
    const { date, startTime, endTime } = this.state;
    const start_time = startTime.split(":").map(Number);
    const end_time = endTime.split(":").map(Number);
    const startDateTime = new Date(date);
    const endDateTime = new Date(date);
    startDateTime.setHours(start_time[0]);
    startDateTime.setMinutes(start_time[1]);
    endDateTime.setHours(end_time[0]);
    endDateTime.setMinutes(end_time[1]);
    const data = {
      startDateTime: formatFullDate(startDateTime),
      endDateTime: formatFullDate(endDateTime),
      user_ids: [this.getEmpId()],
    };
    this.createSchedule(data);
  };

  render() {
    return (
      <>
        <header>
          <NavBarAdmin />
        </header>
        <Datetime />
        <div className="col-md-6 mx-auto text-center">
          <div className="header-title">
            <h1 className="wv-heading--title">
              Editing Schedule for Employee ID: {this.getEmpId()}
            </h1>
          </div>
        </div>
        <Container className="calender-cmp" fluid="xl">
          <Row noGutters="true">
            <Col md="5">
              <h2>Dates:</h2>
              <Calendar
                className="text-center"
                onChange={(e) =>
                  this.onChange({ target: { name: "date", value: e } })
                }
                value={this.state.date}
              />
            </Col>
            <Col className="time-cmp" md="3">
              <h2>Start Time:</h2>
              <TimePicker
                onChange={(e) =>
                  this.onChange({
                    target: { name: "startTime", value: e },
                  })
                }
                value={this.state.startTime}
                className="text-center"
              />
            </Col>
            <Col className="time-cmp" md="3">
              <h2>End Time:</h2>
              <TimePicker
                onChange={(e) =>
                  this.onChange({
                    target: { name: "endTime", value: e },
                  })
                }
                value={this.state.endTime}
                className="text-center"
              />
            </Col>
          </Row>
        </Container>
        <div className="text-center ">
          <button
            type="submit"
            className=" btn btn-danger"
            onClick={this.onSubmit}
          >
            Add Schedule
          </button>
        </div>
        <DayModal
          open={this.state.showmodal}
          handleClose={() => this.setState({ showmodal: false })}
          aria-labelledby="simple-modal-title"
          aria-describedby="simple-modal-description"
          body={(classes, modalStyle) => (
            <div style={modalStyle} className={classes.paper}>
              <h2 id="simple-modal-title">ALERT!</h2>
              <p id="simple-modal-description">
                Created Schedule Successfully!
              </p>
            </div>
          )}
        />
      </>
    );
  }
}

export default EmpSchedule;
