package com.hr.system.manage.service;

import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.repository.domain.EmployeeDetail;

/**
 * Created by star on 3/28/15.
 */
public interface EmployeeDetailService {
    public EmployeeDetail save(EmployeeDetail employeeDetail);

    public void delete(EmployeeDetail employeeDetail);

    public EmployeeDetail findByEmpId(long empId);


}
