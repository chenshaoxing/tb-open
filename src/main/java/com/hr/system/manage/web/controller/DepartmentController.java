package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.DepartmentChange;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.repository.domain.RecruitNeed;
import com.hr.system.manage.service.DepartmentChangeService;
import com.hr.system.manage.service.DepartmentService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.service.RecruitNeedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by star on 3/28/15.
 */
@Controller
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private DepartmentChangeService departmentChangeService;

    @Resource
    private RecruitNeedService recruitNeedService;

    @RequestMapping(value = "/department")
    public String index(Model model) {
        return "department/department";
    }


    @RequestMapping(value = "/department/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute Department department) throws Exception {
        try {
            List<Department> list = departmentService.findByName(department.getName());
            if(department.getId() > 0L){
                if(list != null && list.size() > 0){
                    if(list.get(0).getId() != department.getId()){
                        return ResultJson.resultError();
                    }
                }
            }else{
                if(list != null && list.size() > 0) {
                    return ResultJson.resultError();
                }
            }
            departmentService.save(department);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/department/findAll")
    @ResponseBody
    public Map<String, Object> findAll() throws Exception {
        try {
            List<Department> departmentList = departmentService.findAll();
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("list", departmentList);
            return ResultJson.resultSuccess(result);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/department/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(@RequestParam(required = false) String name,@RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            Department department = new Department();
            department.setName(name);
            PageInfo<Department> pageInfo = departmentService.findByParams(department,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }



    @RequestMapping(value = "/department/delete")
    @ResponseBody
    public Map<String, Object> findByParams(@RequestParam Long id) throws Exception {
        try {
            Department department = new Department();
            department.setId(id);
            List<Employee> employees = employeeService.findByDepId(id);
            if(employees != null && employees.size()>0){
                return ResultJson.resultError();
            }
            List<DepartmentChange> departmentChanges = departmentChangeService.findByDepId(id);
            if(departmentChanges != null && departmentChanges.size()>0){
                return ResultJson.resultError();
            }
            List<RecruitNeed> recruitNeeds = recruitNeedService.findByDepId(id);
            if(recruitNeeds != null && recruitNeeds.size()>0){
                return ResultJson.resultError();
            }
            departmentService.delete(department);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/department/getById")
    @ResponseBody
    public Map<String, Object> getById(@RequestParam Long id) throws Exception {
        try {
            return ResultJson.resultSuccess(departmentService.findByDepId(id));
        } catch (Exception e) {
            throw e;
        }
    }

}