package com.taobao.task;

import com.taobao.common.ConfigurationManager;
import com.taobao.common.Constants;
import com.taobao.entity.User;
import com.taobao.service.OnlineEmailService;
import com.taobao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-02
 * Time: 17:57
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class ScanUserOverDateTask {
    @Resource
    private UserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(ScanUserOverDateTask.class);

    @Resource
    private OnlineEmailService onlineEmailService;
    public void scan(){
        try {
            Long remindDay = ConfigurationManager.create().getLong(Constants.USER_OVER_TIME_REMIND_DAY,5);
            List<User> users = userService.findAll();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            for(User u:users){
                Date currentDate = sf.parse(sf.format(date));
                if(currentDate.getTime() < u.getOverDate().getTime()){
                    Long differ = u.getOverDate().getTime()-currentDate.getTime();
                    long day = differ/1000/60/60/24;
                    if(day < remindDay){
                        onlineEmailService.sendEmail(u.getEmail(),"自动评价服务到期提醒","您的纯洁宝自动评价服务还有"+day+"天到期,请记得提前进行续费,以免影响亲的正常使用!");
                    }
                }
            }
            LOG.info("ScanUserOverDateTask success");
        } catch (ParseException e) {
            LOG.error(e.getMessage());
            onlineEmailService.sendEmail(Constants.ADMIN_EMAIL,"异常邮件","ERROR INFO:\n"+e.getMessage());
        }
    }
}
