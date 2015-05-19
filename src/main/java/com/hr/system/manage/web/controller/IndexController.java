package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.domain.Account;
import com.hr.system.manage.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by shaz on 2015/2/25.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "main")
    public String index(Model model){
        return "main";
    }
//    @RequestMapping(value = "/staffInfo")
//    public String index1(Model model){
//        return "staffInfo";
//    }

    @RequestMapping(value = "/login",produces = "application/json")
    @ResponseBody
    public Map<String,Object> login(@RequestParam String user ,@RequestParam String pass,HttpServletResponse response) throws Exception{
        try {
            Account account = accountService.findByName(user);
            if(account != null && account.getPwd().equals(pass)){
                Cookie idCookie = new Cookie("id",String.valueOf(account.getId()));
                Cookie nameCookie = new Cookie("name",account.getUsername());
                response.addCookie(idCookie);
                response.addCookie(nameCookie);
                return ResultJson.resultSuccess();
            }else{
                return ResultJson.resultError();
            }
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
}
