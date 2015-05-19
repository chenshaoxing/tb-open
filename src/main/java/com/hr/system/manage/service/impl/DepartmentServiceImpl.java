package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.service.DepartmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shaz on 2015/3/3.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private IBasePersistence basePersistence;
    @Override
    public Department findByDepId(long depId) {
        Department department = new Department();
        department.setId(depId);
        return basePersistence.getEntityById(Department.class,department);
    }

    @Override
    public List<Department> findAll() {
        return basePersistence.getAllEntities(Department.class);
    }

    @Override
    public PageInfo<Department> findByParams(Department department,int currentPage,int pageSize) {
        List<Expression> params = new ArrayList<Expression>();
        if(StringUtils.isNotEmpty(department.getName())) {
            Expression nameExp = new Expression();
            nameExp.setFiledName("name");
            nameExp.setFiledValue(department.getName());
            nameExp.setOperation(Expression.Operation.AllLike);
            params.add(nameExp);
        }
        PageInfo<Department> pageInfo = basePersistence.getEntitiesByExpressions(Department.class, params, "id", currentPage, pageSize);
        return pageInfo;
    }

    @Override
    public Department save(Department department) {
        return basePersistence.save(department);
    }

    @Override
    public void delete(Department department) {
        basePersistence.removeEntity(department);
    }

    @Override
    public List<Department> findByName(String name) {
        return basePersistence.getEntitiesByField(Department.class,"name",name);
    }

    @Override
    public List<Department> findByEmpId(Long empId) {
        return basePersistence.getEntitiesByField(Department.class,"leader.id",empId);
    }




}
