package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.*;
import com.hr.system.manage.service.*;
import com.hr.system.manage.util.WriteExcel;
import com.hr.system.manage.web.controller.vo.EmployeeVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by star on 3/28/15.
 */
@Controller
public class EmployeeController {
    @Resource
    private ApplicantService applicantService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployeeDetailService employeeDetailService;

    @Resource
    private SalaryService salaryService;

    @Resource
    private ContractInfoService contractInfoService;

    @Resource
    private DepartmentChangeService departmentChangeService;

    @Resource
    private RecruitNeedService recruitNeedService;


    @Resource
    private AttendanceService attendanceService;

    @RequestMapping(value = "/employee")
    public String index(Model model) {
        return "employee/employee";
    }

    @RequestMapping(value = "/employee-sex-num")
    public String indexSexNum(Model model) {
        return "employee/employee-sex-num";
    }


    @RequestMapping(value = "/employee/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute EmployeeVo employeeVo) throws Exception {
        try {
            Employee employee = null;
            List<Employee> list = employeeService.findByName(employeeVo.getName());
            if(employeeVo.getId() != null){
                if(list != null && list.size() > 0){
                    if(list.get(0).getId() != employeeVo.getId()){
                        return ResultJson.resultError();
                    }else{
                        employee = list.get(0);
                    }
                }else {
                    employee = employeeService.findById(employeeVo.getId());
                }
            }else{
                if(list != null && list.size() > 0) {
                    return ResultJson.resultError();
                }else{
                    employee = new Employee();
                }
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Department department = departmentService.findByDepId(employeeVo.getDepId());
            employee.setName(employeeVo.getName());
            employee.setEmail(employeeVo.getEmail());
            employee.setSex(Employee.Sex.valueOf(employeeVo.getSex()));
            employee.setTelephone(employeeVo.getTelephone());
            employee.setAge(employeeVo.getAge());
            employee.setIdCard(employeeVo.getIdCard());
            employee.setJiGUan(employeeVo.getJiGUan());
            employee.setMarry(employeeVo.isMarry());
            employee.setPeople(employeeVo.getPeople());
            employee.setPoliticsFace(employeeVo.getPoliticsFace());
            employee.setPosition(employeeVo.getPosition());
            employee.setResidenceAddress(employeeVo.getResidenceAddress());
            employee.setDepartment(department);
            employee.setBirthday(sf.parse(employeeVo.getBirthday()));
            employee = employeeService.save(employee);

            EmployeeDetail employeeDetail = new EmployeeDetail();
            employeeDetail.setUrgentMobile(employeeVo.getUrgentMobile());
            employeeDetail.setUrgentPeople(employeeVo.getUrgentPeople());
            employeeDetail.setGraduatedDate(sf.parse(employeeVo.getGraduatedDate()));
            employeeDetail.setComputerLevel(employeeVo.getComputerLevel());
            employeeDetail.setEnglishLevel(employeeVo.getEnglishLevel());
            employeeDetail.setDiploma(employeeVo.getDiploma());
            employeeDetail.setEduSchool(employeeVo.getEduSchool());
            employeeDetail.setFamilyAddress(employeeVo.getFamilyAddress());
            employeeDetail.setSpecialty(employeeVo.getSpecialty());
            employeeDetail.setEmployee(employee);
            employeeDetailService.save(employeeDetail);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/employee/findAll")
    @ResponseBody
    public Map<String, Object> findAll() throws Exception {
        try {
            List<Employee> empList = employeeService.findAll();
            return ResultJson.resultSuccess(empList);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/employee/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(@RequestParam(required = false) String name,@RequestParam(required = false) String sex,@RequestParam(required = false) Long depId ,@RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            Employee emp = new Employee();
            emp.setName(name);
            Department dep = new Department();
            if(depId != null){
                dep.setId(depId);
            }
            emp.setDepartment(dep);
            if (StringUtils.isNotEmpty(sex)){
                emp.setSex(Employee.Sex.valueOf(sex));
            }
            PageInfo<Employee> pageInfo = employeeService.query(currentPage,pageSize,emp);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }



    @RequestMapping(value = "/employee/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception {
        try {
            Employee emp = new Employee();
            emp.setId(id);
            EmployeeDetail employeeDetail = employeeDetailService.findByEmpId(id);

            ContractInfo contractInfo = contractInfoService.findByEmpId(id);
            if(contractInfo != null){
                contractInfoService.delete(contractInfo);
            }

            List<DepartmentChange>  changeList = departmentChangeService.findByEmpId(id);
            if(changeList != null && changeList.size() > 0){
                for(DepartmentChange d:changeList){
                    departmentChangeService.delete(d);
                }
            }

            List<Department> department = departmentService.findByEmpId(id);
            if(department != null && department.size() > 0){
                for(Department  d:department){
                    d.setLeader(null);
                    departmentService.save(d);
                }
            }

            List<RecruitNeed> list = recruitNeedService.findByEmpId(id);
            if(list != null && list.size() > 0){
                for(RecruitNeed  r:list){
                    List<Applicant> applicants = applicantService.findByRecruitId(r.getId());
                    if(applicants != null && applicants.size() > 0){
                        for(Applicant a:applicants){
                            applicantService.delete(a);
                        }
                    }
                    recruitNeedService.delete(r);
                }
            }

            List<Attendance> attendances = attendanceService.findByEmpId(id);
            if(attendances != null && attendances.size() > 0){
                for(Attendance a:attendances){
                    attendanceService.delete(a);
                }
            }

            List<Salary> salaries = salaryService.findByEmpId(id);
            if(salaries != null && salaries.size() > 0){
                for(Salary  s:salaries){
                    salaryService.delete(s);
                }
            }
            employeeDetailService.delete(employeeDetail);
            employeeService.remove(emp);
            return ResultJson.resultSuccess();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/employee/getById")
    @ResponseBody
    public Map<String, Object> getById(@RequestParam Long id) throws Exception {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            EmployeeDetail employeeDetail = employeeDetailService.findByEmpId(id);
            result.put("empDetail",employeeDetail);
            Employee employee = employeeService.findById(id);
            result.put("emp",employee);
            return ResultJson.resultSuccess(result);
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/employee/download")
    public String download(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String sex,
                           @RequestParam(required = false) Long depId) throws Exception {
        try {
            List<String> title = new ArrayList<String>();
            List<Map<String,String>> datalist = new ArrayList<Map<String,String>>();
            Map<String,String> keyValue = new HashMap<String, String>();
            keyValue.put("name", "名字");
            keyValue.put("sex","性别");
            keyValue.put("people","名族");
            keyValue.put("isMarry","是否婚配");
            keyValue.put("jiGUan","籍贯");
            keyValue.put("politicsFace","政治面貌");
            keyValue.put("residenceAddress","户口所在地");
            keyValue.put("birthday","生日");
            keyValue.put("telephone","电话");
            keyValue.put("email","邮箱");
            keyValue.put("idCard","身份证");
            keyValue.put("position","职位");
            keyValue.put("department","部门");
            keyValue.put("diploma","学历");
            keyValue.put("eduSchool","毕业学校");
            keyValue.put("englishLevel","英语等级");
            keyValue.put("computerLevel","计算机等级");
            keyValue.put("graduatedDate","毕业时间");
            keyValue.put("specialty","专业");
            keyValue.put("familyAddress","家庭地址");
            keyValue.put("urgentPeople","紧急联系人");
            keyValue.put("urgentMobile","紧急联系人电话");
            title.add("name");
            title.add("sex");
            title.add("people");
            title.add("isMarry");
            title.add("jiGUan");
            title.add("politicsFace");
            title.add("residenceAddress");
            title.add("birthday");
            title.add("telephone");
            title.add("email");
            title.add("idCard");
            title.add("position");
            title.add("department");
            title.add("diploma");
            title.add("eduSchool");
            title.add("englishLevel");
            title.add("computerLevel");
            title.add("graduatedDate");
            title.add("specialty");
            title.add("familyAddress");
            title.add("urgentPeople");
            title.add("urgentMobile");


            Employee emp = new Employee();
            emp.setName(name);
            Department dep = new Department();
            if(depId != null){
                dep.setId(depId);
            }
            emp.setDepartment(dep);
            if (StringUtils.isNotEmpty(sex)){
                emp.setSex(Employee.Sex.valueOf(sex));
            }
            List<Employee> empList = employeeService.query(1,Integer.MAX_VALUE,emp).getList();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            for(Employee employee:empList){
                Map<String,String> data = new HashMap<String, String>();
                EmployeeDetail employeeDetail = employeeDetailService.findByEmpId(employee.getId());
                data.put("name", employee.getName());
                data.put("sex", employee.getSex() == Employee.Sex.MAN?"男":"女");
                data.put("people",employee.getPeople());
                data.put("isMarry",employee.isMarry()?"已婚":"未婚");
                data.put("jiGUan",employee.getJiGUan());
                data.put("politicsFace",employee.getPoliticsFace());
                data.put("residenceAddress",employee.getResidenceAddress());
                data.put("birthday",sf.format(employee.getBirthday()));
                data.put("telephone",employee.getTelephone());
                data.put("email",employee.getEmail());
                data.put("idCard",employee.getIdCard());
                data.put("position",employee.getPosition());
                data.put("department",employee.getDepartment().getName());
                data.put("diploma",employeeDetail.getDiploma());
                data.put("eduSchool",employeeDetail.getEduSchool());
                data.put("englishLevel",employeeDetail.getEnglishLevel());
                data.put("computerLevel",employeeDetail.getComputerLevel());
                data.put("graduatedDate",sf.format(employeeDetail.getGraduatedDate()));
                data.put("specialty",employeeDetail.getSpecialty());
                data.put("familyAddress",employeeDetail.getFamilyAddress());
                data.put("urgentPeople",employeeDetail.getUrgentPeople());
                data.put("urgentMobile",employeeDetail.getUrgentMobile());
                datalist.add(data);
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                WriteExcel.create2007Excel(keyValue, title, datalist, os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(("employee.xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 组装图表数据
     * @return
     */
    private Map<String,Object> getEmployeeSexNum(){
        Map<String,Object> result = new HashMap();
        Map<String,Object> data = new HashMap();
        boolean isView = true;

        Map<String,Object> tooltip = new HashMap();          //鼠标移动到线上弹出提示
        tooltip.put("trigger","axis");

        Map<String,Object> legend = new HashMap();          //图例

        List<Map<String,Object>> xAxis = new ArrayList();       //X轴
        Map<String,Object> xAxi = new HashMap();       //X轴信息
        xAxi.put("type","category");
        xAxi.put("boundaryGap","false");


        legend.put("data",new String[]{"员工男女比例"});         //设置图例信息

        Map<String,Object> toolbox = new HashMap();
        toolbox.put("show",true);
        Map<String,Object> feature = new HashMap();
        Map<String,Object> mark = new HashMap();
        mark.put("show",true);
        Map<String,Object> dataView = new HashMap();
        dataView.put("show",true);
        dataView.put("readOnly",true);
        Map<String,Object> magicType = new HashMap();
        magicType.put("show",true);
        magicType.put("type",new String[]{"line","bar"});
        Map<String,Object> restore = new HashMap();
        restore.put("show",true);
        Map<String,Object> saveAsImage = new HashMap();
        saveAsImage.put("show",true);
        feature.put("mark",mark);
        feature.put("dataView",dataView);
        feature.put("magicType",magicType);
        feature.put("restore",restore);
        feature.put("saveAsImage",saveAsImage);
        toolbox.put("feature",feature);


        Map<String,Object> title = new HashMap();      //折线图 左上角 title
        title.put("text","员工男女比例");

        Map<String,Object> params = new HashMap();

        title.put("subtext","员工男女比例");
        List<String> xAxiList = new ArrayList();   //X轴时间维度
        List<Long> dataList = new ArrayList();

        Map<String,String> map = new HashMap<String, String>();
        map.put("sex","MAN");
        xAxiList.add("男");
        dataList.add(employeeService.countByParams(map));
        map.clear();
        map.put("sex","WOMAN");
        xAxiList.add("女");
        dataList.add(employeeService.countByParams(map));

        xAxi.put("data",xAxiList);
        xAxis.add(xAxi);

        List<Map<String,Object>> yAxis = new ArrayList();       //Y 轴

        Map<String,Object> yAxi = new HashMap();          //Y 轴对象
        yAxi.put("type","value");

        Map<String,Object> axisLabel = new HashMap();    //轴标签
        axisLabel.put("formatter","{value} 人");

        yAxi.put("axisLabel",axisLabel);             //Y 轴添加轴标签
        yAxis.add(yAxi);                             //添加到Y 轴数据


        List<Map<String,Object>> series = new ArrayList();      //数据对象
        Map<String,Object> seriesMap = new HashMap();
        seriesMap.put("name","男女比例");
        seriesMap.put("type","bar");
        seriesMap.put("data",dataList);

                 //添加到数据对象里面
        series.add(seriesMap);

        data.put("title", title);
        data.put("tooltip", tooltip);
        data.put("legend", legend);
        data.put("xAxis", xAxis);
        data.put("yAxis", yAxis);
        data.put("series",series);
        data.put("toolbox",toolbox);
        result.put("data",data);
        result.put("isView",isView);
        return result;
    }
    @RequestMapping("/employee/getEmpSexNum")
    @ResponseBody
    public Map<String, Object> getEmpSexNum() throws Exception {
        try {
                Map<String,Object> map = getEmployeeSexNum();
            return ResultJson.resultSuccess(map);
        } catch (Exception e) {
            throw e;
        }
    }

}