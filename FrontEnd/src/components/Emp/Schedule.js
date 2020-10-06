import React from 'react';
import { ScheduleComponent, Day, Week, WorkWeek, Month, Agenda, Inject } from '@syncfusion/ej2-react-schedule';
import ReactDom from 'react-dom';
import "./schedule.css";
import EmployeeNav from "./EmployeeNav";


class Schedule extends React.Component {
    constructor() {
        super(...arguments);
        this.state = {
            pastbookings: [],
            message: null,
          };
    }
    // componentDidMount() {
    //     this.reloadBookingList();
    // };
    render() {
        return (
        <>
        <header> 
            <EmployeeNav />
        </header>
        <ScheduleComponent> 
            <Inject services={[Day, Week, WorkWeek, Month, Agenda]} />
        </ScheduleComponent>
        </>
        )
    }

}
export default Schedule;