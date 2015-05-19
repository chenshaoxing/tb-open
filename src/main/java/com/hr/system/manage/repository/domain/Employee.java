package com.hr.system.manage.repository.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by star on 3/25/15.
 */
@Entity
public class Employee extends BaseDomain{
    public  enum Sex{
        MAN,WOMAN
    }
    @Column(name = "name")
    private String name;


    @Column
    @Enumerated(EnumType.STRING)
    private  Sex sex;

    @Column
    private Integer age;

    @Column
    private String  people;

    @Column(name = "is_marry")
    private boolean isMarry;

    @Column
    private String jiGUan;

    @Column         //政治面貌
    private String politicsFace;

    @Column     //户口所在地
    private String  residenceAddress;

    @Column
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column
    private String telephone;

    @Column
    private String email;

    @Column(name = "id_card")
    private String idCard;


    @Column
    private String photo;

    @Column //职位
    private String position;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="dep_id")
    private Department department;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getJiGUan() {
        return jiGUan;
    }

    public void setJiGUan(String jiGUan) {
        this.jiGUan = jiGUan;
    }

    public String getPoliticsFace() {
        return politicsFace;
    }

    public void setPoliticsFace(String politicsFace) {
        this.politicsFace = politicsFace;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }



    public boolean isMarry() {
        return isMarry;
    }

    public void setMarry(boolean isMarry) {
        this.isMarry = isMarry;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getPosition() {
        return position;
    } public void setPosition(String position) {
        this.position = position;
    }
}
