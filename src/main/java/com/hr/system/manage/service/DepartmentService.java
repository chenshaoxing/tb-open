package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Department;

import java.util.List;
import java.util.Map;

/**
 * Created by shaz on 2015/3/3.
 */
public interface DepartmentService {
    public Department findByDepId(long depId);
    public List<Department> findAll();
    public PageInfo<Department> findByParams(Department department,int currentPage,int pageSize);
    public Department save(Department department);
    public void delete(Department department);
    public List<Department> findByName(String name);
    public List<Department> findByEmpId(Long empId);



}
