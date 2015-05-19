package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.*;
import com.hr.system.manage.service.*;
import com.hr.system.manage.web.controller.vo.DepartmentChangeVo;
import com.hr.system.manage.web.controller.vo.RecruitNeedVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 3/30/15.
 */
@Controller
public class RecruitNeedController {

    @Resource
    private RecruitNeedService recruitNeedService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ApplicantService applicantService;


    @RequestMapping(value = "/recruit-need")
    public String index(Model model) {
        return "recruit-need/recruit-need";
    }


    @RequestMapping(value = "/recruit-need/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute RecruitNeedVo vo) throws Exception {
        try {
            RecruitNeed need = null;
            if(vo.getId() != null){
                need = recruitNeedService.findById(vo.getId());
            }else{
                need = new RecruitNeed();
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            Employee employee = employeeService.findById(vo.getEmpId());
            Department dep = departmentService.findByDepId(vo.getDepId());
            need.setDepartment(dep);
            need.setMaxSalary(vo.getMaxSalary());
            need.setMinSalary(vo.getMinSalary());
            need.setPosition(vo.getPosition());
            need.setRecruitCondition(vo.getRecruitCondition());
            need.setRecruitEmp(employee);
            need.setRecruitDate(sf.parse(vo.getRecruitDate()));
            need.setRecruitStatus(RecruitNeed.RecruitStatus.valueOf(vo.getRecruitStatus()));
            recruitNeedService.save(need);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/recruit-need/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
                                            @RequestParam(required = false) Long depId,
                                            @RequestParam(required = false) String needStatus,
                                            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            PageInfo<RecruitNeed> pageInfo = recruitNeedService.findByParams(depId,needStatus,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/recruit-need/findById")
    @ResponseBody
    public Map<String, Object> findById(@RequestParam Long id) throws Exception{
        try {
            RecruitNeed need = recruitNeedService.findById(id);
            return ResultJson.resultSuccess(need);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/recruit-need/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try {
            RecruitNeed recruitNeed = new RecruitNeed();
            recruitNeed.setId(id);
            List<Applicant> applicants =  applicantService.findByRecruitId(id);
            if(applicants != null && applicants.size() > 0){
                return ResultJson.resultError();
            }
            recruitNeedService.delete(recruitNeed);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/recruit-need/findAll")
    @ResponseBody
    public Map<String, Object> findAll() throws Exception{
        try {
            return ResultJson.resultSuccess(recruitNeedService.findAll());
        } catch (Exception e) {
            throw e;
        }
    }




}
