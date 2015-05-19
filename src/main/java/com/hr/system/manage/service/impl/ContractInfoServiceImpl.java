package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Applicant;
import com.hr.system.manage.repository.domain.ContractInfo;
import com.hr.system.manage.service.ApplicantService;
import com.hr.system.manage.service.ContractInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 11:01
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public ContractInfo save(ContractInfo contractInfo) {
        return iBasePersistence.save(contractInfo);
    }

    @Override
    public void delete(ContractInfo contractInfo) {
        iBasePersistence.removeEntity(contractInfo);
    }

    @Override
    public PageInfo<ContractInfo> findByParams(Long empId, String empName,String contractType,String contractStatus, int currentPage, int pageSize) {
        List<Expression> expList = new ArrayList<Expression>();
        if(empId != null){
            Expression empIdExp = new Expression("employee.id",empId, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(empIdExp);
        }
        if(StringUtils.isNotEmpty(empName)){
            Expression empNameExp = new Expression("employee.name",empName, Expression.Relation.AND, Expression.Operation.AllLike);
            expList.add(empNameExp);
        }
        if(StringUtils.isNotEmpty(contractType)){
            Expression contractTypeExp = new Expression("contractType", ContractInfo.ContractType.valueOf(contractType), Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(contractTypeExp);
        }
        if(StringUtils.isNotEmpty(contractStatus)){
            Expression contractTypeExp = new Expression("contractStatus", ContractInfo.ContractStatus.valueOf(contractStatus), Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(contractTypeExp);
        }
        return iBasePersistence.getEntitiesByExpressions(ContractInfo.class,expList,"id",currentPage,pageSize);
    }

    @Override
    public ContractInfo findById(Long id) {
        ContractInfo contractInfo = new ContractInfo() ;
        contractInfo.setId(id);
        return iBasePersistence.getEntityById(ContractInfo.class,contractInfo);
    }

    @Override
    public ContractInfo findByEmpId(Long empId) {
        return iBasePersistence.getEntityByField(ContractInfo.class,"employee.id",empId);
    }

    @Override
    public List<ContractInfo> findOutTimeContract(Date currentDate) {
        List<Expression> expList = new ArrayList<Expression>();
        if(currentDate != null){
            Expression endDateExp = new Expression("endDate",currentDate, Expression.Relation.AND, Expression.Operation.LessThanEqual);
            expList.add(endDateExp);
        }
        return iBasePersistence.getEntitiesByExpressions(ContractInfo.class,expList,"id",0,Integer.MAX_VALUE).getList();
    }
}
