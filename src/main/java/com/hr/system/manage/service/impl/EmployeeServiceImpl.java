package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 3/25/15.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public Employee save(Employee employee) {
        return iBasePersistence.save(employee);
    }

    @Override
    public void remove(Employee employee) {
        iBasePersistence.removeDetachedEntity(employee);
    }

    @Override
    public PageInfo<Employee> query(int currentPage,int pageSize,Employee employee) {
      //  Class<T> clazz, List<Expression > expressions, String orderField,int pageIndex, int pageSize
        List<Expression> params = new ArrayList();
        if(StringUtils.isNotEmpty(employee.getName())){
            Expression nameExp = new Expression();
            nameExp.setFiledName("name");
            nameExp.setFiledValue(employee.getName());
            nameExp.setOperation(Expression.Operation.AllLike);
            nameExp.setRelation(Expression.Relation.AND);
            params.add(nameExp);
        }
        if(employee.getDepartment().getId() > 0){
            Expression depExp = new Expression();
            depExp.setFiledName("department.id");
            depExp.setFiledValue(employee.getDepartment().getId());
            depExp.setOperation(Expression.Operation.Equal);
            params.add(depExp);
        }
        if(employee.getSex() != null){
            Expression sexExp = new Expression();
            sexExp.setFiledName("sex");
            sexExp.setFiledValue(employee.getSex());
            sexExp.setOperation(Expression.Operation.Equal);
            params.add(sexExp);
        }
        return iBasePersistence.getEntitiesByExpressions(Employee.class,params,"id",currentPage,pageSize);
    }

    @Override
    public Employee findById(long id) {
        Employee employee = new Employee();
        employee.setId(id);
        return iBasePersistence.getEntityById(Employee.class,employee);
    }

    @Override
    public List<Employee> findByName(String name) {
        return iBasePersistence.getEntitiesByField(Employee.class,"name",name);
    }

    @Override
    public List<Employee> findAll() {
        return iBasePersistence.getAllEntities(Employee.class);
    }

    @Override
    public List<Employee> findByDepId(Long depId) {
        return iBasePersistence.getEntitiesByField(Employee.class,"department.id",depId);
    }

    @Override
    public long countByParams(Map<String, String> params) {
        List<Expression> expList = new ArrayList<Expression>();
        if(StringUtils.isNotEmpty(params.get("sex"))){
            Expression sexExp = new Expression();
            sexExp.setFiledName("sex");
            sexExp.setFiledValue(Employee.Sex.valueOf(params.get("sex")));
            sexExp.setOperation(Expression.Operation.Equal);
            expList.add(sexExp);
        }
        return iBasePersistence.getEntitiesByExpressionsTotalCount(Employee.class,expList);
    }
}
