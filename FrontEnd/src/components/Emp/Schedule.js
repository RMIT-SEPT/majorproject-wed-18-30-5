import React from 'react';
import { ScheduleComponent, Day, Week, WorkWeek, Month, Agenda, Inject } from '@syncfusion/ej2-react-schedule';
import ReactDom from 'react-dom';
import "./schedule.css";
import EmployeeNav from "./EmployeeNav";

class Schedule extends React.Component {
    constructor() {
        super(...arguments);
        this.data= [{
            Id: 2,
            Subject: 'Melbourne',
            StartTime: new Date(20202, 10, 11, 10, 0),
            EndTime: new Date(2020, 10, 11, 12, 0),
        }];
    }
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