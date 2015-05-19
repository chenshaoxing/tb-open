package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.hr.system.manage.repository.domain.*;
import com.hr.system.manage.service.AttendanceService;
import com.hr.system.manage.service.EmployeeService;
import com.hr.system.manage.service.SalaryService;
import com.hr.system.manage.service.impl.SalaryServiceImpl;
import com.hr.system.manage.util.DayUtil;
import com.hr.system.manage.web.controller.vo.EmployeeVo;
import com.hr.system.manage.web.controller.vo.SalaryVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by star on 3/30/15.
 */
@Controller
public class SalaryController {
    @Resource
    private AttendanceService attendanceService;

    @Resource
    private SalaryService salaryService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/salary")
    public String index(Model model) {
        return "salary/salary";
    }


    @RequestMapping(value = "/salary-count")
    public String indexSexNum(Model model) {
        return "salary/salary-count";
    }


    @RequestMapping(value = "/salary/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute SalaryVo salaryVo) throws Exception {
        try {
            Employee employee = employeeService.findById(salaryVo.getEmpId());
            Salary salary = null;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            Date date = sf.parse(salaryVo.getSalaryDate());
            String [] dateStr = sf.format(date).split("-");

            Attendance attendance = attendanceService.findByParams(salaryVo.getEmpId(), date);
            if(attendance == null){
                return ResultJson.resultError("该薪资日期的考勤还未有纪录，请先添加该月的考勤纪录后再进行薪资信息操作！");
            }

            List<Salary> list = salaryService.findByEmpIdAndDate(salaryVo.getEmpId(), Integer.valueOf(dateStr[0]), Integer.valueOf(dateStr[1]));
            if(list != null && list.size() > 0){
                return ResultJson.resultError();
            }else {

                List<Salary> salaries = salaryService.findByEmpId(employee.getId());
                if(salaries != null && salaries.size() > 0){
                    for(Salary s:salaries){
                        s.setStatus(false);
                        salaryService.save(s);
                    }
                }
                salary = new Salary();
                int workDay = DayUtil.getMonthWorkDay(salaryVo.getSalaryDate());
                int workHours = workDay*8;
                int oweHours =  attendance.getSkipWork()+attendance.getCausalLeave()+attendance.getSickLeave();
                DecimalFormat df = new DecimalFormat("#.00");
                float baseS = Float.valueOf(df.format((workHours-oweHours)*(salaryVo.getBasicSalary()/workDay/8)));
                float attSalary = Float.valueOf(df.format(oweHours*(salaryVo.getBasicSalary()/workDay/8)));
                salary.setAttendanceDeduct(attSalary);
                salary.setBankAccount(salaryVo.getBankAccount());
//                salary.setBasicSalary(salaryVo.getBasicSalary());
                salary.setFoodSubsidies(workDay*10f);
                salary.setMedicalInsurance(salaryVo.getMedicalInsurance());
                salary.setMonth(Integer.valueOf(dateStr[1]));
                salary.setPension(salaryVo.getPension());
                salary.setPerformanceRelatedPay(salaryVo.getPerformanceRelatedPay());
                salary.setRealSalary(salaryVo.getRealSalary());
                salary.setPublicReserveFund(salaryVo.getPublicReserveFund());
                salary.setSalaryDate(date);
                salary.setBasicSalary(salaryVo.getBasicSalary());
                salary.setSalaryType(Salary.SalaryType.valueOf(salaryVo.getSalaryType()));
                salary.setTax(salaryVo.getTax());
                salary.setUnemploymentInsurance(salaryVo.getUnemploymentInsurance());
                salary.setYear(Integer.valueOf(dateStr[0]));
                salary.setEmployee(employee);
                salary.setStatus(true);
                salary.setRealSalary(baseS);
                salaryService.save(salary);
                return ResultJson.resultSuccess();
            }
        } catch (Exception e) {
            throw e;
        }
    }


    @RequestMapping(value = "/salary/findByParams")
    @ResponseBody
    public Map<String, Object> findByParams(
                                            @RequestParam(required = false) Long empId,
                                            @RequestParam(required = false) String salaryType,
                                            @RequestParam(required = false) Integer year,
                                            @RequestParam(required = false) Integer month,
                                            @RequestParam int currentPage,@RequestParam int pageSize) throws Exception {
        try {
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("empId",empId);
            params.put("salaryType",salaryType);
            params.put("year",year);
            params.put("month",month);
            PageInfo<Salary> pageInfo = salaryService.findByParams(params,currentPage,pageSize);
            return ResultJson.resultSuccess(pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }



    /**
     * 组装图表数据
     * @return
     */
    private Map<String,Object> getSalaryData(){
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


        legend.put("data",new String[]{"薪资区间统计"});         //设置图例信息

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
        title.put("text","薪资区间统计");

        Map<String,Object> params = new HashMap();

        title.put("subtext","薪资统计统计");
        List<String> xAxiList = new ArrayList();   //X轴时间维度
        List<Long> dataList = new ArrayList();

        Map<SalaryServiceImpl.SalaryFilterType,Float> map = new HashMap();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,1000f);
        xAxiList.add("1000以下");

        dataList.add(salaryService.countSalaryInterval(map));

        map.clear();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 1000f);
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,3000f);
        xAxiList.add("1000-3000之间");
        dataList.add(salaryService.countSalaryInterval(map));


        map.clear();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 3000f);
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,5000f);
        xAxiList.add("3000-5000之间");
        dataList.add(salaryService.countSalaryInterval(map));

        map.clear();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 5000f);
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,7000f);
        xAxiList.add("5000-7000之间");
        dataList.add(salaryService.countSalaryInterval(map));

        map.clear();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 7000f);
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,10000f);
        xAxiList.add("7000-10000之间");
        dataList.add(salaryService.countSalaryInterval(map));

        map.clear();
        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 10000f);
        xAxiList.add("10000以上");
        dataList.add(salaryService.countSalaryInterval(map));


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
        seriesMap.put("name","人数");
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
    @RequestMapping("/salary/getSalaryImage")
    @ResponseBody
    public Map<String, Object> getSalaryImage() throws Exception {
        try {
            Map<String,Object> map = getSalaryData();
            return ResultJson.resultSuccess(map);
        } catch (Exception e) {
            throw e;
        }
    }

}
