package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.Expression;
import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.RecruitNeed;
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
public class RecruitNeedServiceImpl implements RecruitNeedService {
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public RecruitNeed save(RecruitNeed need) {
        return iBasePersistence.save(need);
    }

    @Override
    public void delete(RecruitNeed need) {
        iBasePersistence.removeEntity(need);
    }

    @Override
    public PageInfo<RecruitNeed> findByParams(Long depId, String needStatus, int currentPage, int pageSize) {
        List<Expression> expList = new ArrayList<Expression>();
        if(depId != null){
            Expression depIdExp = new Expression("department.id",depId, Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(depIdExp);
        }
        if(StringUtils.isNotEmpty(needStatus)){
            Expression needStatusExp = new Expression("recruitStatus", RecruitNeed.RecruitStatus.valueOf(needStatus), Expression.Relation.AND, Expression.Operation.Equal);
            expList.add(needStatusExp);
        }
        return iBasePersistence.getEntitiesByExpressions(RecruitNeed.class,expList,"id",currentPage,pageSize);
    }

    @Override
    public RecruitNeed findById(Long id) {
        RecruitNeed need = new RecruitNeed() ;
        need.setId(id);
        return iBasePersistence.getEntityById(RecruitNeed.class,need);
    }

    @Override
    public List<RecruitNeed> findAll() {
        return iBasePersistence.getAllEntities(RecruitNeed.class);
    }

    @Override
    public List<RecruitNeed> findByEmpId(Long empId) {
        return iBasePersistence.getEntitiesByField(RecruitNeed.class,"recruitEmp.id",empId);
    }

    @Override
    public List<RecruitNeed> findByDepId(Long depId) {
        return iBasePersistence.getEntitiesByField(RecruitNeed.class,"department.id",depId);
    }
}
