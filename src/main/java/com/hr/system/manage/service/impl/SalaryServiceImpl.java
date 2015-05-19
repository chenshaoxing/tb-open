package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Salary;
import com.hr.system.manage.service.SalaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by star on 3/30/15.
 */
@Service
public class SalaryServiceImpl implements SalaryService {

    public enum SalaryFilterType{
        BETWEEN_BEFORE,BETWEEN_AFTER
    }
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public Salary save(Salary salary) {
        return iBasePersistence.save(salary);
    }

    @Override
    public PageInfo<Salary> findByParams(Map<String, Object> params,int currentPage,int pageSize) {
        List<Expression> expList = new ArrayList();
        if(params.get("empId") != null){
            Expression empIdExp = new Expression();
            empIdExp.setFiledName("employee.id");
            empIdExp.setFiledValue(params.get("empId"));
            empIdExp.setOperation(Expression.Operation.Equal);
            expList.add(empIdExp);
        }
        if(params.get("salaryType") != null){
            Expression salaryTypeExp = new Expression();
            salaryTypeExp.setFiledName("salaryType");
            salaryTypeExp.setFiledValue(Salary.SalaryType.valueOf(params.get("salaryType").toString()));
            salaryTypeExp.setOperation(Expression.Operation.Equal);
            salaryTypeExp.setRelation(Expression.Relation.AND);
            expList.add(salaryTypeExp);
        }
        if(params.get("year") != null){
            Expression yearExp = new Expression();
            yearExp.setFiledName("year");
            yearExp.setFiledValue(params.get("year"));
            yearExp.setOperation(Expression.Operation.Equal);
            yearExp.setRelation(Expression.Relation.AND);
            expList.add(yearExp);
        }
        if(params.get("month") != null){
            Expression monthExp = new Expression();
            monthExp.setFiledName("month");
            monthExp.setFiledValue(params.get("month"));
            monthExp.setRelation(Expression.Relation.AND);
            monthExp.setOperation(Expression.Operation.Equal);
            expList.add(monthExp);
        }
        return iBasePersistence.getEntitiesByExpressions(Salary.class, expList, "id", currentPage, pageSize);
    }

    public List<Salary> findByEmpIdAndDate(Long empId,Integer year,Integer month){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("employee.id",empId);
        params.put("year",year);
        params.put("month",month);
        return iBasePersistence.getEntitiesByFieldList(Salary.class,params);
    }

    @Override
    public void delete(Salary salary) {
        iBasePersistence.removeEntity(salary);
    }

    @Override
    public List<Salary> findByEmpId(Long empId) {
        return iBasePersistence.getEntitiesByField(Salary.class,"employee.id",empId);
    }

    @Override
    public Long countSalaryInterval(Map<SalaryFilterType, Float> params) {
        List<Expression> expList = new ArrayList();
        Expression statusExp = new Expression();
        statusExp.setFiledName("status");
        statusExp.setFiledValue(true);
        statusExp.setOperation(Expression.Operation.Equal);
        statusExp.setRelation(Expression.Relation.AND);
        expList.add(statusExp);
        if(params.get(SalaryFilterType.BETWEEN_BEFORE) != null){
            Expression lessRealSalaryExp = new Expression();
            lessRealSalaryExp.setFiledName("realSalary");
            lessRealSalaryExp.setFiledValue(params.get(SalaryFilterType.BETWEEN_BEFORE));
            lessRealSalaryExp.setOperation(Expression.Operation.GreaterThan);
            lessRealSalaryExp.setRelation(Expression.Relation.AND);
            expList.add(lessRealSalaryExp);
        }
        if(params.get(SalaryFilterType.BETWEEN_AFTER) != null){
            Expression greaterRealSalary = new Expression();
            greaterRealSalary.setFiledName("realSalary");
            greaterRealSalary.setFiledValue(params.get(SalaryFilterType.BETWEEN_AFTER));
            greaterRealSalary.setOperation(Expression.Operation.LessThan);
            greaterRealSalary.setRelation(Expression.Relation.AND);
            expList.add(greaterRealSalary);
        }
        return iBasePersistence.getEntitiesByExpressionsTotalCount(Salary.class,expList);
    }
}
