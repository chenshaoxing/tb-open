package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class Salary extends BaseDomain {
    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="emp_id")
    private Employee employee;

    public enum SalaryType{
        OFFICIAL,TEMP
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column

    private boolean status;

    @Column
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @Column
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Column
    private Integer month;

    @Column
    @Temporal(TemporalType.DATE)
    private Date salaryDate;

    @Column
    private String  bankAccount;

    @Column
    private Float basicSalary;

    @Column        //绩效
    private Float performanceRelatedPay;

    @Column       //税
    private Float tax;

    @Column //养老保险
    private Float pension;

    @Column //失业保险
    private Float unemploymentInsurance;

    @Column//医疗保险
    private Float medicalInsurance;

    @Column //公积金
    private Float publicReserveFund;

    @Column //考勤扣款
    private Float  attendanceDeduct;

    @Column //伙食补贴
    private Float foodSubsidies;

    public Float getRealSalary() {
        realSalary = basicSalary+performanceRelatedPay+foodSubsidies-tax-pension-unemploymentInsurance-medicalInsurance-publicReserveFund-attendanceDeduct;
        return realSalary;
    }

    public void setRealSalary(Float realSalary) {
        this.realSalary = realSalary;
    }

    public Float getFoodSubsidies() {
        return foodSubsidies;
    }

    public void setFoodSubsidies(Float foodSubsidies) {
        this.foodSubsidies = foodSubsidies;
    }

    public Float getAttendanceDeduct() {
        return attendanceDeduct;
    }

    public void setAttendanceDeduct(Float attendanceDeduct) {
        this.attendanceDeduct = attendanceDeduct;
    }

    public Float getPublicReserveFund() {
        return publicReserveFund;
    }

    public void setPublicReserveFund(Float publicReserveFund) {
        this.publicReserveFund = publicReserveFund;
    }

    public Float getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Float medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Float getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(Float unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public Float getPension() {
        return pension;
    }

    public void setPension(Float pension) {
        this.pension = pension;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getPerformanceRelatedPay() {
        return performanceRelatedPay;
    }

    public void setPerformanceRelatedPay(Float performanceRelatedPay) {
        this.performanceRelatedPay = performanceRelatedPay;
    }

    public Float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column //实际工资
    private Float realSalary;

}
