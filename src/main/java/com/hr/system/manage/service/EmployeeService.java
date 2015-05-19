package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Employee;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 3/25/15.
 */
public interface EmployeeService {
    public Employee save(Employee employee);

    public void remove(Employee employee);

    public PageInfo<Employee> query(int currentPage,int pageSize,Employee employee);

    public Employee findById(long id);

    public List<Employee> findByName(String name);

    public List<Employee> findAll();

    public List<Employee> findByDepId(Long depId);

    public long countByParams(Map<String,String> params);
}
