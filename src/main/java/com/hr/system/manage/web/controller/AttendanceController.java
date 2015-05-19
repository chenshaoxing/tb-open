package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.Attendance;
import com.hr.system.manage.repository.domain.ContractInfo;
import com.hr.system.manage.repository.domain.Employee;
import com.hr.system.manage.service.AttendanceService;
import com.hr.system.manage.service.ContractInfoService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.web.controller.vo.AttendanceVo;
import com.hr.system.manage.web.controller.vo.ContractInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 4/3/15.
 */
@Controller
public class AttendanceController {
    @Resource
    private AttendanceService attendanceService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/attendance")
    public String index(Model model) {
        return "attendance/attendance";
    }



    @RequestMapping(value = "/attendance/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute AttendanceVo vo) throws Exception {
        try {

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            Date  attDate = sf.parse(vo.getAttendanceDate());
            PageInfo<Attendance> pageInfo = attendanceService.findByParams(vo.getEmpId(),attDate,1,Integer.MAX_VALUE);
            List<Attendance> attendanceList = pageInfo.getList();
            Attendance attendance = null;
            if(vo.getId() != null){
                if(attendanceList != null && attendanceList.size() == 1){
                    if(attendanceList.get(0).getId() == vo.getId()) {
                        attendance = attendanceService.findById(vo.getId());
                    }else{
                        return ResultJson.resultError();
                    }
                }

            }else{
                attendance = new Attendance();
                if(attendanceList != null && attendanceList.size() > 0){
                    return ResultJson.resultError();
                }
            }
            Employee employee = employeeService.findById(vo.getEmpId());
            attendance.setEmployee(employee);
            attendance.setAttendanceDate(sf.parse(vo.getAttendanceDate()));
            attendance.setCausalLeave(vo.getCausalLeave());
            attendance.setSickLeave(vo.getSickLeave());
            attendance.setSkipWork(vo.getSkipWork());
            attendance.setTakeOff(vo.getTakeOff());
            attendanceService.save(attendance);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/attendance/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
            @RequestParam(required = false) Long empId,
            @RequestParam(required = false)   String attendanceDate,
            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            Date date = null;
            if(StringUtils.isNotEmpty(attendanceDate)){
                date = sf.parse(attendanceDate);
            }
            PageInfo<Attendance> pageInfo = attendanceService.findByParams(empId,date,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/attendance/findById")
    @ResponseBody
    public Map<String, Object> findById(@RequestParam Long id) throws Exception{
        try {
            Attendance attendance = attendanceService.findById(id);
            return ResultJson.resultSuccess(attendance);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/attendance/findAll")
    @ResponseBody
    public Map<String, Object> findAll() throws Exception{
        try {
            List<Attendance> attendanceList = attendanceService.findAll();
            return ResultJson.resultSuccess(attendanceList);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/attendance/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try {
            Attendance attendance = new Attendance();
            attendance.setId(id);
            attendanceService.delete(attendance);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

}
