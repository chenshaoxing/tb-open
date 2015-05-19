package com.hr.system.manage.repository.domain;

import javax.persistence.*;

/**
 * Created by shaz on 2015/3/3.
 */
@Entity
public class Department extends BaseDomain{
    @Column
    private String name;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="leader_emp_id",nullable = true)
    private Employee leader;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }



}
