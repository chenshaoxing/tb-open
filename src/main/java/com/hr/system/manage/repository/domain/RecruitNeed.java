package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class RecruitNeed extends BaseDomain{
    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="recruit_emp_id")
    private Employee recruitEmp;       //招聘人

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="dep_id")
    private Department department;       //需求部门

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Column
    private String recruitCondition ;  //招聘条件

    @Column
    private String position;

    public Employee getRecruitEmp() {
        return recruitEmp;
    }

    public void setRecruitEmp(Employee recruitEmp) {
        this.recruitEmp = recruitEmp;
    }

    public String getRecruitCondition() {
        return recruitCondition;
    }

    public void setRecruitCondition(String recruitCondition) {
        this.recruitCondition = recruitCondition;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Float getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Float maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Float getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Float minSalary) {
        this.minSalary = minSalary;
    }

    public Date getRecruitDate() {
        return recruitDate;
    }

    public void setRecruitDate(Date recruitDate) {
        this.recruitDate = recruitDate;
    }

    @Column

    private Float maxSalary;
    @Column
    private Float minSalary;

    public RecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    public void setRecruitStatus(RecruitStatus recruitStatus) {
        this.recruitStatus = recruitStatus;
    }

    @Column
    @Temporal(TemporalType.DATE)
    private Date recruitDate;

    public enum  RecruitStatus{
        END,RUNNING

    }

    @Column
    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

}
