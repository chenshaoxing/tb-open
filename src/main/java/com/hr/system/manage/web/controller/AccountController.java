package com.hr.system.manage.web.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.domain.Account;
import com.hr.system.manage.service.AccountService;
import com.hr.system.manage.web.controller.vo.AccountVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 17:10
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AccountController {
    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/account/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute AccountVo vo) throws Exception{
        Account account = accountService.getById(vo.getId());
        if(account.getPwd().equals(vo.getOldPwd())){
            account.setPwd(vo.getNewOwd());
            accountService.save(account);
        }else{
            return ResultJson.resultError();
        }
        return ResultJson.resultSuccess();
    }
}
