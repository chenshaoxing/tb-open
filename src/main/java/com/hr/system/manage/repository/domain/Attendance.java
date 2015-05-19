package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 4/3/15.
 */
@Entity        //考勤
public class Attendance extends BaseDomain {

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    //考勤时间
    @Column
    @Temporal(TemporalType.DATE)
    private Date attendanceDate;

    //调休
    @Column
    private Integer takeOff;

    @Column  //事假
    private Integer causalLeave;

    @Column  //病假
    private Integer sickLeave;

    @Column  //旷工
    private Integer skipWork;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Integer getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(Integer takeOff) {
        this.takeOff = takeOff;
    }

    public Integer getCausalLeave() {
        return causalLeave;
    }

    public void setCausalLeave(Integer causalLeave) {
        this.causalLeave = causalLeave;
    }

    public Integer getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Integer getSkipWork() {
        return skipWork;
    }

    public void setSkipWork(Integer skipWork) {
        this.skipWork = skipWork;
    }
}
