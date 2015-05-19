package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Salary;
import com.hr.system.manage.service.impl.SalaryServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 3/30/15.
 */
public interface SalaryService {
    public Salary save(Salary salary);

    public PageInfo<Salary> findByParams(Map<String,Object> params,int currentPage,int pageSize);

    public List<Salary> findByEmpIdAndDate(Long empId,Integer year,Integer month);

    public void delete(Salary salary);

    public List<Salary> findByEmpId(Long empId);

    public Long countSalaryInterval(Map<SalaryServiceImpl.SalaryFilterType,Float> params);
}
