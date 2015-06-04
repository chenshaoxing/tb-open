package com.taobao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回json数据的容器工具类
 * Created by yajunz on 2014/7/23.
 */
public class ResultJson {
    /**
     * 返回一个map容器
     * @return
     */
    public static Map<String, Object> resultMap(){
        return new HashMap<String, Object>();
    }

    /**
     * 返回成功JsonMap
     * @return
     */
    public static Map<String, Object> resultSuccess(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return  result;
    }

    /**
     * 返回成功JsonMap
     * @param data 数据对象
     * @return
     */
    public static Map<String, Object> resultSuccess(Object data){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data", data);
        return  result;
    }

    /**
     * 返回失败JsonMap
     * @return
     */
    public static Map<String, Object> resultError(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        return  result;
    }

    /**
     * 返回失败JsonMap
     * @param msg 错误消息
     * @return
     */
    public static Map<String, Object> resultError(String msg){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("msg", msg);
        return  result;
    }
}
