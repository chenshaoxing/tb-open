package com.hr.system.manage.web.controller.vo;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 11:07
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class RecruitNeedVo extends BaseVo {
    private Long empId;       //招聘人
    private Long depId;       //需求部门

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    private String recruitCondition ;  //招聘条件

    private String position;

    private Float maxSalary;

    private Float minSalary;

    private String recruitDate;

    private String recruitStatus;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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

    public String getRecruitDate() {
        return recruitDate;
    }

    public void setRecruitDate(String recruitDate) {
        this.recruitDate = recruitDate;
    }

    public String getRecruitStatus() {
        return recruitStatus;
    }

    public void setRecruitStatus(String recruitStatus) {
        this.recruitStatus = recruitStatus;
    }
}
