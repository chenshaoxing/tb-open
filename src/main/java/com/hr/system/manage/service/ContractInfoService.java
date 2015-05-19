package com.hr.system.manage.service;

import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.ContractInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015-03-31.
 */
public interface ContractInfoService {
    public ContractInfo save(ContractInfo contractInfo);

    public void delete(ContractInfo contractInfo);

    public PageInfo<ContractInfo> findByParams(Long empId, String empName,String contractType,String contractStatus, int currentPage, int pageSize);

    public ContractInfo findById(Long id);

    public ContractInfo findByEmpId(Long empId);

    public List<ContractInfo> findOutTimeContract(Date currentDate);


}
