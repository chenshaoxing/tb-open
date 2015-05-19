package com.taobao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: csx
 * Date: 10/18/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseDomain implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "CreatedTime", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime = new Date();
    @Column(name = "CreatedBy", length = 255, nullable = true)
    private String createdBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "UpdatedTime", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime = new Date();
    @Column(name = "UpdatedBy", length = 255, nullable = true)
    private String updatedBy;
}
