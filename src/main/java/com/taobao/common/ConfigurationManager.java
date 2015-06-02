/*
 * Copyright (c) 2010, 2014, Jumei and/or its affiliates. All rights reserved.
 * JUMEI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.taobao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 将Squirrel配置信息加入<code>Configuration</code>
 *
 * Created by 黄刚 on 2014/8/10.
 */
public class ConfigurationManager extends Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);

    public static Configuration addSquirrelResources(Configuration conf) {
        conf.addResource("squirrel-default.xml");
        conf.addResource("squirrel-filelog-default.xml");
        conf.addResource("squirrel-site.xml");

        //checkDefaultsVersion(conf);
        //checkForClusterFreeMemoryLimit(conf);
        return conf;
    }

    /**
     * 用Squirrel资源文件创建配置信息对象
     * @return a Squirrel配置信息对象
     */
    public static Configuration create() {
        Configuration conf = new Configuration();
        return addSquirrelResources(conf);
    }

    /**
     * @param that 将被克隆的Configuration对象.
     * @return 用squirrel-*.xml和附加的<tt>that</tt>资源文件生成的配置信息对象
     */
    public static Configuration create(final Configuration that) {
        Configuration conf = create();
        merge(conf, that);
        return conf;
    }

    /**
     * 对存在的两个配置信息进行合并
     * @param destConf 默认配置信息
     * @param srcConf 自定义配置信息，可能对默认配置信息中配置项进行了重新定义
     **/
    public static void merge(Configuration destConf, Configuration srcConf) {
        for (Map.Entry<String, String> e : srcConf) {
            destConf.set(e.getKey(), e.getValue());
        }
    }

    /**
     * 用于测试输出xml格式的配置型信息
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = ConfigurationManager.create();
        int rpcTimeout = conf.getInt("squirrel.rpc.imeout", 200);
        System.err.println("Eric said: "+rpcTimeout);
        conf.writeXml(System.out);
    }
}
