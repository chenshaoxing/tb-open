package com.taobao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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
}
