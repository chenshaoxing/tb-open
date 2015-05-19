package com.hr.system.manage.service;

import com.hr.system.manage.service.impl.SalaryServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-04-01
 * Time: 17:51
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class TestService {
    public static void main(String[] args) {
        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
//        SalaryService salaryService = (SalaryService)act.getBean("salaryServiceImpl");
//        Map<SalaryServiceImpl.SalaryFilterType,Float> params = new HashMap();
//        params.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE,500F);
//        params.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,1000F);
//        System.out.println(salaryService.countSalaryInterval(params));
//        params.clear();
//        params.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE,1000F);
//        params.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,4000F);
//        System.out.println(salaryService.countSalaryInterval(params));


        EmployeeService employeeService = (EmployeeService)act.getBean("employeeServiceImpl");
        Map<String,String> map = new HashMap<String, String>();
        map.put("sex","MAN");
        System.out.println(employeeService.countByParams(map));
        map.clear();
        map.put("sex","WOMAN");
        System.out.println(employeeService.countByParams(map));

    }
}
