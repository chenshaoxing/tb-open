package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.DepartmentChange;
import com.hr.system.manage.repository.domain.RecruitNeed;

import java.util.List;

/**
 * Created by Administrator on 2015-03-31.
 */
public interface RecruitNeedService {
    public RecruitNeed save(RecruitNeed need);

    public void delete(RecruitNeed need);

    public PageInfo<RecruitNeed> findByParams(Long depId, String needStatus, int currentPage, int pageSize);

    public RecruitNeed findById(Long id);

    public List<RecruitNeed> findAll();

    public List<RecruitNeed> findByEmpId(Long empId);

    public List<RecruitNeed> findByDepId(Long depId);

}
