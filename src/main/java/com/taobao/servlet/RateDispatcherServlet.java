package com.taobao.servlet;

import com.taobao.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-04
 * Time: 10:06
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class RateDispatcherServlet extends DispatcherServlet {
    private static final Logger LOG = LoggerFactory.getLogger("browseIpLogger");

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip = getIp(request);
        LOG.info("browse ip:"+ip);
        super.doDispatch(request, response);
    }




    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
              //多次反向代理后会有多个ip值，第一个ip才是真实ip
           int index = ip.indexOf(",");
           if(index != -1){
                return ip.substring(0,index);
           }else{
                return ip;
           }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
