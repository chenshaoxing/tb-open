package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class DepartmentChange extends BaseDomain {
    @Column
    @Temporal(TemporalType.DATE)
    private Date changeDate;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="before_dep_id")
    private Department beforeDepartment;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="after_dep_id")
    private Department afterDepartment;

    @Column
    private String beforePosition;
    @Column
    private String afterPosition;
    @Column
    private String cause;

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Department getBeforeDepartment() {
        return beforeDepartment;
    }

    public void setBeforeDepartment(Department beforeDepartment) {
        this.beforeDepartment = beforeDepartment;
    }

    public Department getAfterDepartment() {
        return afterDepartment;
    }

    public void setAfterDepartment(Department afterDepartment) {
        this.afterDepartment = afterDepartment;
    }

    public String getBeforePosition() {
        return beforePosition;
    }

    public void setBeforePosition(String beforePosition) {
        this.beforePosition = beforePosition;
    }

    public String getAfterPosition() {
        return afterPosition;
    }

    public void setAfterPosition(String afterPosition) {
        this.afterPosition = afterPosition;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
