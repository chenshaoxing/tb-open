package com.hr.system.manage.web.controller.vo;

import java.util.Date;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 16:21
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class ContractInfoVo extends BaseVo {
    private Long empId;
    private String contractType;
    private String contractStatus;
    private String startDate;
    private String endDate;
    private String signDate;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

}
