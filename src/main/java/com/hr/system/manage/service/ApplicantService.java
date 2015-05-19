package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Applicant;
import com.hr.system.manage.repository.domain.RecruitNeed;

import java.util.List;

/**
 * Created by Administrator on 2015-03-31.
 */
public interface ApplicantService {
    public Applicant save(Applicant applicant);

    public void delete(Applicant applicant);

    public PageInfo<Applicant> findByParams(Long depId, String applicantStatus, int currentPage, int pageSize);

    public Applicant findById(Long id);

    public List<Applicant> findByRecruitId(long recruitId);

}
