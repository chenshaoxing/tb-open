package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class EmployeeDetail extends BaseDomain{

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    @Column  //学历
    private String  diploma;
    @Column   //毕业学校
    private String eduSchool;
    @Column
    private String englishLevel;
    @Column
    private String computerLevel;
    @Column
    @Temporal(TemporalType.DATE)
    private Date  graduatedDate;
    @Column   //专业
    private String  specialty;
    @Column
    private Integer peopleNum;
    @Column
    private String familyAddress;

    @Column
    private String urgentPeople;

    @Column
    private String urgentMobile;

    public String getUrgentMobile() {
        return urgentMobile;
    }

    public void setUrgentMobile(String urgentMobile) {
        this.urgentMobile = urgentMobile;
    }

    public String getUrgentPeople() {
        return urgentPeople;
    }

    public void setUrgentPeople(String urgentPeople) {
        this.urgentPeople = urgentPeople;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getEduSchool() {
        return eduSchool;
    }

    public void setEduSchool(String eduSchool) {
        this.eduSchool = eduSchool;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;
    }

    public String getComputerLevel() {
        return computerLevel;
    }

    public void setComputerLevel(String computerLevel) {
        this.computerLevel = computerLevel;
    }

    public Date getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(Date graduatedDate) {
        this.graduatedDate = graduatedDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }
}
