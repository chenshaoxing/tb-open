package com.hr.system.manage.web.controller.vo;

import com.hr.system.manage.repository.domain.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/30/15.
 */
public class SalaryVo extends BaseVo {
    private Integer month;

    private String salaryDate;

    private String  bankAccount;

    private Float basicSalary;

    private Float performanceRelatedPay;

    private Float tax;

    private Float pension;

    private Float unemploymentInsurance;

    private Float medicalInsurance;

    private Float publicReserveFund;

    private Float  attendanceDeduct;

    private Float foodSubsidies;

    private Long empId;

    private String salaryType;

    private Integer year;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Float getPerformanceRelatedPay() {
        return performanceRelatedPay;
    }

    public void setPerformanceRelatedPay(Float performanceRelatedPay) {
        this.performanceRelatedPay = performanceRelatedPay;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getPension() {
        return pension;
    }

    public void setPension(Float pension) {
        this.pension = pension;
    }

    public Float getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(Float unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public Float getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Float medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Float getPublicReserveFund() {
        return publicReserveFund;
    }

    public void setPublicReserveFund(Float publicReserveFund) {
        this.publicReserveFund = publicReserveFund;
    }

    public Float getAttendanceDeduct() {
        return attendanceDeduct;
    }

    public void setAttendanceDeduct(Float attendanceDeduct) {
        this.attendanceDeduct = attendanceDeduct;
    }

    public Float getFoodSubsidies() {
        return foodSubsidies;
    }

    public void setFoodSubsidies(Float foodSubsidies) {
        this.foodSubsidies = foodSubsidies;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(Float realSalary) {
        this.realSalary = realSalary;
    }

    private Float realSalary;
}
