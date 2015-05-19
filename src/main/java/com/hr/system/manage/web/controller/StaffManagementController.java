package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.domain.Department;
import com.hr.system.manage.repository.domain.Staff;
import com.hr.system.manage.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaz on 2015/2/27.
 */
@Controller
public class StaffManagementController {
    private static final Logger logger = LoggerFactory
            .getLogger(StaffManagementController.class);
//    @Resource
//    private StaffService staffService;
//    @Resource
//    private DepartmentService departmentService;
//    @Resource
//    private DepartmentPositionService departmentPositionService;
//    @Resource
//    private PositionService positionService;
//    @Resource
//    private EducationDegreeService educationDegreeService;



    @RequestMapping(value = "/findAllStaff")
    @ResponseBody
    public Map<String,Object> findAllStaff(@RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize") Integer pageSize) throws Exception{
        try {
            Map<String, Object> result = new HashMap();
//            PageInfo pageInfo = staffService.findAll(currentPage,pageSize);
//            result.put("result",true);
//            result.put("list", pageInfo);
            return result;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    @RequestMapping(value = "/getDepList")
    @ResponseBody
    public Map<String,Object> getDepList() throws Exception{
        try {
//            List<Department> list = departmentService.findAll();
            return ResultJson.resultSuccess(null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    @RequestMapping(value = "/findPosByDep")
    @ResponseBody
    public Map<String,Object> getDepList(@RequestParam Long depId) throws Exception{
        try {
//            List<DepartmentPosition> list = departmentPositionService.findByDepId(depId);
//            for(int i=0 ; i<list.size() ; i++){
//                Map map = new HashMap();
//                Long posId = list.get(i).getPosId();
//                String posName = positionService.findByPosId(posId).getName();
//                map.put("posName",posName);
//                map.put("posId",posId);
//                list.get(i).setMap(map);
//            }
            return ResultJson.resultSuccess(null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
    @RequestMapping(value = "/find")
    @ResponseBody
    public Map<String,Object> find(@RequestParam String name,@RequestParam String sex,@RequestParam Integer currentPage,@RequestParam Integer pageSize,
                                   @RequestParam String depId,@RequestParam Long posId) throws Exception{
        try {
            Map<String, Object> result = new HashMap();
            Staff staff = new Staff();
            staff.setName(name);
            staff.setSex(sex);
            staff.setDepId(depId);
            staff.setPosId(posId);
//            PageInfo pageInfo = staffService.find(currentPage, pageSize, staff);
//            result.put("result",true);
//            result.put("list", pageInfo);
            return result;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    @RequestMapping(value = "/addStaffInfo")
    @ResponseBody
    public Map<String,Object> addStaffInfo(@RequestParam Long posId,@RequestParam String depId,@RequestParam String name,@RequestParam String sex,
                       @RequestParam String age,@RequestParam Long  telephone,@RequestParam String  eduName,@RequestParam String specialty,@RequestParam String  isMarry,
                       @RequestParam String race,@RequestParam Long idCard ,@RequestParam String origin , @RequestParam String household,@RequestParam String eduSchool) throws Exception{
        try {
            Staff staff = new Staff();
            staff.setEduName(eduName);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            Date date=sdf.parse(age);
            staff.setAge(date);
            staff.setSpecialty(specialty);
            staff.setSex(sex);
            staff.setDepId(depId);
            staff.setPosId(posId);
            staff.setTelephone(telephone);
            staff.setName(name);
            staff.setIsMarry(isMarry);
            staff.setRace(race);
            staff.setIdCard(idCard);
            staff.setOrigin(origin);
            staff.setHousehold(household);
            staff.setEduSchool(eduSchool);
//            staffService.add(staff);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    @RequestMapping(value = "/findAllEdu")
    @ResponseBody
    public Map<String,Object> findAllEdu() throws Exception{
        try {
//            List<EducationDegree> list = educationDegreeService.findAll();
            return ResultJson.resultSuccess(null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
    @RequestMapping(value = "/findStaffById")
    @ResponseBody
    public Map<String,Object> findStaffById(@RequestParam Long id) throws Exception{
        try {
//            Staff staff =staffService.findById(id);
//            return ResultJson.resultSuccess(staff);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
        return null;
    }

    @RequestMapping(value = "/updateStaff")
    @ResponseBody
    public Map<String,Object> updateStaff(@RequestParam Long id,@RequestParam String posName,@RequestParam String depId,@RequestParam String name,@RequestParam String sex,
                                           @RequestParam String age,@RequestParam Long  telephone,@RequestParam String  eduName,@RequestParam String specialty,@RequestParam String  isMarry,
                                           @RequestParam String race,@RequestParam Long idCard ,@RequestParam String origin , @RequestParam String household,@RequestParam String eduSchool) throws Exception{
        try {
            Staff staff = new Staff();
            staff.setEduName(eduName);
//            staff.setPosId(positionService.findByPosName(posName).getId());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            Date date=sdf.parse(age);
            staff.setAge(date);
            staff.setSpecialty(specialty);
            staff.setSex(sex);
            staff.setDepId(depId);
            staff.setTelephone(telephone);
            staff.setName(name);
            staff.setIsMarry(isMarry);
            staff.setRace(race);
            staff.setIdCard(idCard);
            staff.setOrigin(origin);
            staff.setHousehold(household);
            staff.setEduSchool(eduSchool);
            staff.setId(id);
//                staffService.updateById(staff);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
    @RequestMapping(value =  "/deleteStaffById")
    @ResponseBody
    public Map<String,Object> deleteStaffById(@RequestParam Long id) throws Exception{
        try {
//            staffService.deleteById(id);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }












}
