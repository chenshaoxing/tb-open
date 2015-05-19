package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Applicant;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.repository.domain.RecruitNeed;
import com.hr.system.manage.service.ApplicantService;
import com.hr.system.manage.service.DepartmentService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.service.RecruitNeedService;
import com.hr.system.manage.web.controller.vo.ApplicantVo;
import com.hr.system.manage.web.controller.vo.RecruitNeedVo;
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
public class ApplicantController {

    @Resource
    private ApplicantService applicantService;

    @Resource
    private RecruitNeedService recruitNeedService;

    @RequestMapping(value = "/applicant")
    public String index(Model model) {
        return "applicant/applicant";
    }


    @RequestMapping(value = "/applicant/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute ApplicantVo vo) throws Exception {
        try {
            Applicant applicant = null;
            if(vo.getId() != null){
                applicant = applicantService.findById(vo.getId());
            }else{
                applicant = new Applicant();
            }
            RecruitNeed  recruitNeed =  recruitNeedService.findById(vo.getRecruitNeedId());
            applicant.setRecruitNeed(recruitNeed);
            applicant.setAge(vo.getAge());
            applicant.setApplicantName(vo.getApplicantName());
            applicant.setApplyResult(Applicant.ApplicantResult.valueOf(vo.getApplyResult()));
            applicant.setApplyStatus(Applicant.ApplicantStatus.valueOf(vo.getApplyStatus()));
            applicant.setDiploma(vo.getDiploma());
            applicant.setSex(Employee.Sex.valueOf(vo.getSex()));
            applicant.setTel(vo.getTel());
            applicantService.save(applicant);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/applicant/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
                                            @RequestParam(required = false) Long depId,
                                            @RequestParam(required = false) String applicantStatus,
                                            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            PageInfo<Applicant> pageInfo = applicantService.findByParams(depId,applicantStatus,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/applicant/findById")
    @ResponseBody
    public Map<String, Object> findById(@RequestParam Long id) throws Exception{
        try {
            Applicant applicant = applicantService.findById(id);
            return ResultJson.resultSuccess(applicant);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/applicant/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try {
            Applicant applicant = new Applicant();
            applicant.setId(id);
            applicantService.delete(applicant);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


}
