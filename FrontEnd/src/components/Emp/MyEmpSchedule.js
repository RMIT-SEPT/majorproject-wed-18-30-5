import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import EmployeeNav from "./EmployeeNav";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";
import ApiService from "../../api/ApiService";

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
    border: "2px solid #000",
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

class MyEmpSchedule extends Component {
  constructor() {
    super();
    this.state = {
      time: "10:00",
      showmodal: false,
      open: false,
      setOpen: false,
      scheduleData: [],
      date: "",
      startHour: 8,
      endHour: 18,
    };
  }

  onChange = (date, time) => this.setState({ date, time });

  reloadSchedule = () => {
    ApiService.getSchedule(this, {
      id: Number(5),
    }).then((res) => {
      const scheduleData = Array.from(res.data.body);
      this.setState({ scheduleData }, this.reloadSchedule);
    });
  };

  parseScheduleDatetime = (scheduleDateTime) => {
    return scheduleDateTime.split("@");
  };

  filterSchedule = (scheduleData) => {
    for (let schedule of scheduleData) {
      const [date, startTime] = this.parseScheduleDatetime(
        schedule.startDateTime
      );
      const [endTime] = this.parseScheduleDatetime(schedule.endDateTime);
      if (date === this.state.date) {
        const [startHour, startMinute] = startTime.split(":").map(Number);
        const [endHour, endMinute] = endTime.split(":").map(Number);
        return { startHour, startMinute, endHour, endMinute };
      }
    }
    // get todays schedule
    return null;
  };

  useStyles = makeStyles((theme) => ({
    paper: {
      position: "absolute",
      width: 400,
      backgroundColor: theme.palette.background.paper,
      border: "2px solid #000",
      boxShadow: theme.shadows[5],
      padding: theme.spacing(2, 4, 3),
    },
  }));

  render() {
    return (
      <>
        <header>
          <EmployeeNav />
        </header>
        <div className="col-md-6 mx-auto text-center">
          <div className="header-title">
            <h1 className="wv-heading--title">My Schedule</h1>
          </div>
        </div>
        <div>
          <Calendar
            className="text-center ml-auto mr-auto"
            onChange={(e) =>
              this.onChange({ target: { name: "date", value: e } })
            }
            onClickDay={() => this.setState({ showmodal: true })}
          />
          <DayModal
            open={this.state.showmodal}
            handleClose={() => this.setState({ showmodal: false })}
            aria-labelledby="simple-modal-title"
            aria-describedby="simple-modal-description"
            body={(classes, modalStyle) => (
              <div style={modalStyle} className={classes.paper}>
                <h2 id="simple-modal-title">Text in a modal</h2>
                <p id="simple-modal-description">
                  {this.startHour} AND {this.endHour}
                </p>
              </div>
            )}
          />
        </div>
      </>
    );
  }
}

export default MyEmpSchedule;
