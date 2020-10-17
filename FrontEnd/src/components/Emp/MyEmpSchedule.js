import React, { Component } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import EmployeeNav from "./EmployeeNav";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";
import ApiService from "../../api/ApiService";

const formatPad = (num) => String(num).padStart("2", "0");


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
      startHour: "", 
      startMinute: "", 
      endDate: "", 
      endHour: "", 
      endMinute: ""
    };
  }

  onChange = (date, time) => this.setState({ date, time });

  componentDidMount() {
    this.reloadSchedule();
  }

  reloadSchedule = () => {
    ApiService.getSchedule(this, {
      id: 5,
    }).then((res) => {
      const scheduleData = Array.from(res.data.body);
      this.setState({scheduleData});
    });
  };

  formatDate = (date) => `${date.getFullYear()}:${date.getMonth()}:${date.getDate()}`;

  parseScheduleDatetime = (scheduleDateTime) => {
    const [date, startTime] = scheduleDateTime.split("@");
    const [year, month, day] = date.split("-");
    const [hour, minute] = startTime.split(":");
    const datetime = new Date();
    datetime.setUTCFullYear(year);
    datetime.setUTCMonth(month - 1);
    datetime.setUTCDate(day - 1);
    datetime.setUTCHours(hour);
    datetime.setUTCMinutes(minute);
    return datetime;
  };

  splitDateTimes = (datetime) => {
    const dateTZ = [
      datetime.getFullYear(),
      datetime.getMonth() + 1,
      datetime.getDate() + 1,
    ].join("-");
    const timeTZ = [datetime.getHours(), datetime.getMinutes()].join(":");
    return [dateTZ, timeTZ];
  }

  filterSchedule = (scheduleData, selectedDate = this.state.date) => {
    for (let schedule of scheduleData) {
      const startDatetime = this.parseScheduleDatetime(
        schedule.startDateTime
      );
      const endDatetime = this.parseScheduleDatetime(
        schedule.endDateTime
      );
      if (
        startDatetime.getFullYear() === selectedDate.getFullYear() &&
        startDatetime.getMonth() === selectedDate.getMonth() &&
        startDatetime.getDate() === selectedDate.getDate()
      ) {
        const [, startTime] = this.splitDateTimes(
          startDatetime
        );
        const [endDate, endTime] = this.splitDateTimes(
          endDatetime
        );
        const [startHour, startMinute] = startTime.split(":").map(Number);
        const [endHour, endMinute] = endTime.split(":").map(Number);
        return { startHour, startMinute, endDate, endHour, endMinute };
      }
    }
    // get todays schedule
    return { startHour: "", startMinute: "", endDate: "", endHour: "", endMinute: "" };
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
            minDate={new Date()}
            onChange={(e) =>
              this.onChange({ target: { name: "date", value: e } })
            }
            onClickDay={(e) => {
              console.log(e);
              this.setState(({ scheduleData }) => ({
                showmodal: true,
                ...this.filterSchedule(scheduleData,e)
              }))
            }}
          />
          <DayModal
            open={this.state.showmodal}
            handleClose={() => this.setState({ showmodal: false })}
            aria-labelledby="simple-modal-title"
            aria-describedby="simple-modal-description"
            body={(classes, modalStyle) => (
              <div style={modalStyle} className={classes.paper}>
                <h2 id="simple-modal-title">Employee Schedule:</h2>
                <p id="simple-modal-description">
                  {this.state.startHour === "" ?
             ("No Schedules Allocated For This Date.") :
                    (<span>Starting @ {
                      formatPad(this.state.startHour)}:{
                        formatPad(this.state.startMinute)}0 AND Finishing @ {
                        formatPad(this.state.endHour)}:{
                        formatPad(this.state.endMinute)}
                    </span>)
            }
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
