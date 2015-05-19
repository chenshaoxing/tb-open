package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.domain.EmployeeDetail;
import com.hr.system.manage.service.EmployeeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by star on 3/28/15.
 */
@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {
    @Resource
    private IBasePersistence iBasePersistence;


    @Override
    public EmployeeDetail save(EmployeeDetail employeeDetail) {
        return iBasePersistence.save(employeeDetail);
    }

    @Override
    public void delete(EmployeeDetail employeeDetail) {
        iBasePersistence.removeDetachedEntity(employeeDetail);
    }

    @Override
    public EmployeeDetail findByEmpId(long empId) {
        return iBasePersistence.getEntitiesByField(EmployeeDetail.class,"employee.id",empId).get(0);
    }
}
