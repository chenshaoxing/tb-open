package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class ContractInfo extends BaseDomain {
    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="contract_emp_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    public enum ContractType{
        LABOR,APPLICANT
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public enum ContractStatus{
        TIMEOUT,NORMAL
    }

    @Column
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;
    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column
    @Temporal(TemporalType.DATE)
    private Date signDate;
}
