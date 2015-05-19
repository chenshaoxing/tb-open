package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.DepartmentChange;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.service.DepartmentChangeService;
import com.hr.system.manage.service.DepartmentService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.web.controller.vo.DepartmentChangeVo;
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
public class DepartmentChangeController {

    @Resource
    private DepartmentChangeService dcChangeService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private DepartmentService departmentService;


    @RequestMapping(value = "/department-change")
    public String index(Model model) {
        return "department-change/department-change";
    }


    @RequestMapping(value = "/department-change/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute DepartmentChangeVo vo) throws Exception {
        try {
//            List<DepartmentChange> dcList =  dcChangeService.findByEmpId(vo.getEmpId());

            DepartmentChange change = null;
            if(vo.getId() != null){
                change = dcChangeService.findById(vo.getId());
//                if(dcList != null && dcList.size() > 0 ){
//                    if(dcList.get(0).getId() != change.getId()){
//                        return  ResultJson.resultError();
//                    }
//                }
            }else{
                change = new DepartmentChange();
//                if(dcList != null && dcList.size() > 0 ){
//                    return  ResultJson.resultError();
//                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = employeeService.findById(vo.getEmpId());
            employee.setPosition(vo.getAfterPosition());
            employee = employeeService.save(employee);
            Department beforeDep = departmentService.findByDepId(vo.getBeforeDepId());
            Department afterDep = departmentService.findByDepId(vo.getAfterDepId());
            change.setEmployee(employee);
            change.setAfterPosition(vo.getAfterPosition());
            change.setBeforePosition(vo.getBeforePosition());
            change.setCause(vo.getCause());
            change.setChangeDate(sf.parse(vo.getChangeDate()));
            change.setAfterDepartment(afterDep);
            change.setBeforeDepartment(beforeDep);
            dcChangeService.save(change);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/department-change/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
                                            @RequestParam(required = false) Long empId,
                                            @RequestParam(required = false) String empName,
                                            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            PageInfo<DepartmentChange> pageInfo = dcChangeService.findByEmp(empId,empName,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/department-change/findById")
    @ResponseBody
    public Map<String, Object> findById(@RequestParam Long id) throws Exception{
        try {
            DepartmentChange dcChange = dcChangeService.findById(id);
            return ResultJson.resultSuccess(dcChange);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/department-change/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try {
            DepartmentChange departmentChange = new DepartmentChange();
            departmentChange.setId(id);
            dcChangeService.delete(departmentChange);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


}
