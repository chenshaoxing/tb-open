package com.hr.system.manage.repository.domain;

import javax.persistence.*;

/**
 * Created by star on 3/28/15.
 */
@Entity
public class Applicant extends BaseDomain{
    @Column
    private String applicantName;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="recruit_need_id")
    private RecruitNeed recruitNeed;

    @Column
    @Enumerated(EnumType.STRING)
    private Employee.Sex sex;

    @Column
    private Integer age;

    @Column                //学历
    private String diploma;

    @Column
    private  String tel;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicantStatus applyStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicantResult applyResult;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public RecruitNeed getRecruitNeed() {
        return recruitNeed;
    }

    public void setRecruitNeed(RecruitNeed recruitNeed) {
        this.recruitNeed = recruitNeed;
    }

    public Employee.Sex getSex() {
        return sex;
    }

    public void setSex(Employee.Sex sex) {
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


    public ApplicantResult getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(ApplicantResult applyResult) {
        this.applyResult = applyResult;
    }

    public enum ApplicantResult{
        ING,PASS,NOT_PASS
    }

    public enum ApplicantStatus{
        CHU_SHI,FU_SHI,END
    }

    public ApplicantStatus getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(ApplicantStatus applyStatus) {
        this.applyStatus = applyStatus;
    }


}
