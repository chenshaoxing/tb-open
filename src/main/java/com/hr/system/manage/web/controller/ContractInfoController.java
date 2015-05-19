package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Applicant;
import com.hr.system.manage.repository.domain.ContractInfo;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.repository.domain.RecruitNeed;
import com.hr.system.manage.service.ApplicantService;
import com.hr.system.manage.service.ContractInfoService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.service.RecruitNeedService;
import com.hr.system.manage.web.controller.vo.ApplicantVo;
import com.hr.system.manage.web.controller.vo.ContractInfoVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by star on 3/30/15.
 */
@Controller
public class ContractInfoController {
    @Resource
    private ContractInfoService contractInfoService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/contract")
    public String index(Model model) {
        return "contract/contract";
    }


    @RequestMapping(value = "/contract/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute ContractInfoVo vo) throws Exception {
        try {
            ContractInfo contractInfo = null;
            if(vo.getId() != null){
                contractInfo = contractInfoService.findById(vo.getId());
            }else{
                contractInfo = new ContractInfo();
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = employeeService.findById(vo.getEmpId());
            contractInfo.setEmployee(employee);
            contractInfo.setContractStatus(ContractInfo.ContractStatus.valueOf(vo.getContractStatus()));
            contractInfo.setContractType(ContractInfo.ContractType.valueOf(vo.getContractType()));
            contractInfo.setEndDate(sf.parse(vo.getEndDate()));
            contractInfo.setSignDate(sf.parse(vo.getSignDate()));
            contractInfo.setStartDate(sf.parse(vo.getStartDate()));
            contractInfoService.save(contractInfo);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/contract/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
                                            @RequestParam(required = false) Long empId,
                                            @RequestParam(required = false)   String empName,
                                            @RequestParam(required = false) String contractType,
                                            @RequestParam(required = false) String contractStatus,
                                            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            PageInfo<ContractInfo> pageInfo = contractInfoService.findByParams(empId,empName,contractType,contractStatus,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/contract/findById")
    @ResponseBody
    public Map<String, Object> findById(@RequestParam Long id) throws Exception{
        try {
            ContractInfo contractInfo = contractInfoService.findById(id);
            return ResultJson.resultSuccess(contractInfo);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/contract/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try {
            ContractInfo contractInfo = new ContractInfo();
            contractInfo.setId(id);
            contractInfoService.delete(contractInfo);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


}
