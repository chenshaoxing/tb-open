package com.hr.system.manage.web.controller.vo;

import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 10:16
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class DepartmentChangeVo extends BaseVo {
    private String changeDate;
    private Long empId;
    private Long beforeDepId;
    private Long afterDepId;
    private String beforePosition;
    private String afterPosition;

    public Long getAfterDepId() {
        return afterDepId;
    }

    public void setAfterDepId(Long afterDepId) {
        this.afterDepId = afterDepId;
    }

    private String cause;

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getBeforeDepId() {
        return beforeDepId;
    }

    public void setBeforeDepId(Long beforeDepId) {
        this.beforeDepId = beforeDepId;
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
