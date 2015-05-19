package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.DepartmentChange;
import com.hr.system.manage.repository.domain.Employee;

import java.util.List;

/**
 * Created by Administrator on 2015-03-31.
 */
public interface DepartmentChangeService {
    public DepartmentChange save(DepartmentChange change);

    public void delete(DepartmentChange change);

    public PageInfo<DepartmentChange> findByEmp(Long empId,String empName,int currentPage,int pageSize);

    public DepartmentChange findById(Long id);

    public List<DepartmentChange> findByEmpId(Long empId);

    public List<DepartmentChange> findByDepId(Long depId);

}
