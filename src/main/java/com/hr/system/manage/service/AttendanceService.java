package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Attendance;

import java.util.Date;
import java.util.List;

/**
 * Created by star on 4/3/15.
 */
public interface AttendanceService {
    public Attendance save(Attendance attendance);

    public Attendance findById(Long id);

    public List<Attendance> findByEmpId(Long empId);

    public PageInfo<Attendance> findByParams(Long empId,Date attendanceDate,int currentPage,int pageSize);

    public Attendance findByParams(Long empId,Date attendanceDate);

    public void delete(Attendance attendance);

    public List<Attendance> findAll();

}
