package com.taobao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 15/5/20.
 */
public class Utils {
    public static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, Object> toMap(Object javaBean)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods)
        {
            try
            {
                if (method.getName().startsWith("get"))
                {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[])null);
                    result.put(field, null == value ? "" : value);
                }
            }
            catch (Exception e)
            {
                LOG.error(e.getMessage());
            }
        }

        return result;
    }

    /*
* 毫秒转化
*/
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay+"天"+strHour+"小时"+strMinute+"分"+strSecond+"秒";
    }

    public static Long getUserId() throws Exception{
        try{
            CipherTools cipherTools = new CipherTools();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie [] cookies = request.getCookies();
            Long id = null;
            for(Cookie c:cookies){
                if(c.getName().equals("id")){
                    id = Long.valueOf(cipherTools.decrypt(c.getValue(),Constants.COOKIE_CIPHER_KEY));
                    break;
                }
            }
            return id;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public static String getToDayStartTimeStr(Date date,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String start = format.format(date);
        start = start+" 00:00:00";
        return  start;
    }

    public static String getToDayEndTimeStr(Date date,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String end = format.format(date);
        end = end+" 23:59:59";
        return end;
    }
}
