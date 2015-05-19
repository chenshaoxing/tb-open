package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.DepartmentChange;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.service.DepartmentChangeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-03-31.
 */
@Service
public class DepartmentChangeServiceImpl implements DepartmentChangeService{
    @Resource
    private IBasePersistence iBasePersistence;

    public DepartmentChange save(DepartmentChange change){
        return iBasePersistence.save(change);
    }

    public void delete(DepartmentChange change){
        iBasePersistence.removeEntity(change);
    }

    public PageInfo<DepartmentChange> findByEmp(Long empId,String empName, int currentPage, int pageSize){
        List<Expression> expList = new ArrayList<Expression>();
        if(empId != null){
            Expression empIdExp = new Expression("employee.id",empId, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(empIdExp);
        }
        if(StringUtils.isNotEmpty(empName)){
            Expression empNameExp = new Expression("employee.name",empName, Expression.Relation.AND, Expression.Operation.AllLike);
            expList.add(empNameExp);
        }
        return iBasePersistence.getEntitiesByExpressions(DepartmentChange.class,expList,"id",currentPage,pageSize);
    }

    public DepartmentChange findById(Long id){
        DepartmentChange change = new DepartmentChange();
        change.setId(id);
        return iBasePersistence.getEntityById(DepartmentChange.class,change);
    }

    @Override
    public List<DepartmentChange> findByEmpId(Long empId) {
        return iBasePersistence.getEntitiesByField(DepartmentChange.class, "employee.id", empId);
    }

    @Override
    public List<DepartmentChange> findByDepId(Long depId) {
        List<Expression> expList = new ArrayList<Expression>();
        Expression beforeIdExp = new Expression("beforeDepartment.id",depId, Expression.Relation.OR, Expression.Operation.Equal);
        expList.add(beforeIdExp);
        Expression afterIdExp = new Expression("afterDepartment.id",depId, Expression.Relation.OR, Expression.Operation.Equal);
        expList.add(afterIdExp);
        return iBasePersistence.getEntitiesByExpressions(DepartmentChange.class,expList,"id",0,Integer.MAX_VALUE).getList();
    }

}
