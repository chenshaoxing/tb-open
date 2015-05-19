package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Applicant;
import com.hr.system.manage.repository.domain.RecruitNeed;
import com.hr.system.manage.service.ApplicantService;
import com.hr.system.manage.service.RecruitNeedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class ApplicantServiceImpl implements ApplicantService {
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public Applicant save(Applicant applicant) {
        return iBasePersistence.save(applicant);
    }

    @Override
    public void delete(Applicant applicant) {
        iBasePersistence.removeEntity(applicant);
    }

    @Override
    public PageInfo<Applicant> findByParams(Long depId, String applicantStatus, int currentPage, int pageSize) {
        List<Expression> expList = new ArrayList<Expression>();
        if(depId != null){
            Expression depIdExp = new Expression("recruitNeed.department.id",depId, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(depIdExp);
        }
        if(StringUtils.isNotEmpty(applicantStatus)){
            Expression applyStatusExp = new Expression("applyStatus", Applicant.ApplicantStatus.valueOf(applicantStatus), Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(applyStatusExp);
        }
        return iBasePersistence.getEntitiesByExpressions(Applicant.class,expList,"id",currentPage,pageSize);
    }

    @Override
    public Applicant findById(Long id) {
        Applicant applicant = new Applicant() ;
        applicant.setId(id);
        return iBasePersistence.getEntityById(Applicant.class,applicant);
    }

    @Override
    public List<Applicant> findByRecruitId(long recruitId) {
        return iBasePersistence.getEntitiesByField(Applicant.class,"recruitNeed.id",recruitId);
    }
}
