package com.hr.system.manage.web.controller.vo;

/**
 * Created by star on 4/3/15.
 */
public class AttendanceVo extends BaseVo {

    private Long empId;


    private String attendanceDate;

    private Integer takeOff;

    private Integer causalLeave;

    private Integer sickLeave;

    private Integer skipWork;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
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
