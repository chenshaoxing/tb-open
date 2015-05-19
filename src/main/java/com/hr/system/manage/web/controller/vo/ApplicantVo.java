package com.hr.system.manage.web.controller.vo;

import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.repository.domain.RecruitNeed;

import javax.persistence.*;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 14:14
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class ApplicantVo extends BaseVo {
    private String applicantName;

    private Long recruitNeedId;

    private String sex;

    private Integer age;

    private String diploma;

    private  String tel;

    private String applyStatus;

    private String applyResult;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getRecruitNeedId() {
        return recruitNeedId;
    }

    public void setRecruitNeedId(Long recruitNeedId) {
        this.recruitNeedId = recruitNeedId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(String applyResult) {
        this.applyResult = applyResult;
    }
}
