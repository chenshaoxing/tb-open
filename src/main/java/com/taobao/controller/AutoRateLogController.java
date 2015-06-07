package com.taobao.controller;

import com.taobao.common.ResultJson;
import com.taobao.common.Utils;
import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateLog;
import com.taobao.service.AutoRateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by star on 15/5/23.
 */
@Controller
public class AutoRateLogController {
    private static final Logger LOG = LoggerFactory.getLogger(AutoRateLogController.class);

    @Resource
    private AutoRateLogService autoRateLogService;

    @RequestMapping(value = "/auto-rate-log")
    public String index(){
        return "rate/auto-rate-log";
    }


    @RequestMapping(value = "/auto-rate-log/report")
    public String indexReport(){
        return "rate/auto-rate-log-image";
    }




    @RequestMapping(value = "/auto-rate-log/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestParam int currentPage,
                                       @RequestParam int pageSize,
                                       @RequestParam(required = false) String buyerNick,
                                       @RequestParam(required = false) Long tradeId,
                                       @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime
                                       ) throws Exception{
        try{
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("tradeId",tradeId);
            params.put("buyerNick",buyerNick);
            params.put("startTime",startTime);
            params.put("endTime",endTime);
            params.put("userId", Utils.getUserId());
            PageInfo<AutoRateLog> pageInfo = autoRateLogService.getList(currentPage, pageSize, params);
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 组装图表数据
     * @return
     */
    private Map<String,Object> getCountRate(Long userId){
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


        legend.put("data",new String[]{""});         //设置图例信息

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
        title.put("text","");

        Map<String,Object> params = new HashMap();

        title.put("subtext","最近一月评价统计");
        List<String> xAxiList = new ArrayList();   //X轴时间维度
        List<Long> dataList = new ArrayList();

        List<Map<String,Object>> resultList = autoRateLogService.countNumByUserId(userId);
        if(resultList == null || resultList.size() <1){
            return null;
        }
        for(Map<String,Object> single:resultList){
            Date date = (Date)single.get("rateDate");
            xAxiList.add(Utils.getTimeStr(date,"yyyy-MM-dd"));
            dataList.add(Long.valueOf(single.get("num").toString()));
        }
//
//        Map<SalaryServiceImpl.SalaryFilterType,Float> map = new HashMap();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,1000f);
//        xAxiList.add("1000以下");
//
//        dataList.add(salaryService.countSalaryInterval(map));
//
//        map.clear();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 1000f);
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,3000f);
//        xAxiList.add("1000-3000之间");
//        dataList.add(salaryService.countSalaryInterval(map));
//
//
//        map.clear();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 3000f);
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,5000f);
//        xAxiList.add("3000-5000之间");
//        dataList.add(salaryService.countSalaryInterval(map));
//
//        map.clear();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 5000f);
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,7000f);
//        xAxiList.add("5000-7000之间");
//        dataList.add(salaryService.countSalaryInterval(map));
//
//        map.clear();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 7000f);
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_AFTER,10000f);
//        xAxiList.add("7000-10000之间");
//        dataList.add(salaryService.countSalaryInterval(map));
//
//        map.clear();
//        map.put(SalaryServiceImpl.SalaryFilterType.BETWEEN_BEFORE, 10000f);
//        xAxiList.add("10000以上");
//        dataList.add(salaryService.countSalaryInterval(map));


        xAxi.put("data",xAxiList);
        xAxis.add(xAxi);

        List<Map<String,Object>> yAxis = new ArrayList();       //Y 轴

        Map<String,Object> yAxi = new HashMap();          //Y 轴对象
        yAxi.put("type","value");

        Map<String,Object> axisLabel = new HashMap();    //轴标签
        axisLabel.put("formatter","{value} 个");

        yAxi.put("axisLabel",axisLabel);             //Y 轴添加轴标签
        yAxis.add(yAxi);                             //添加到Y 轴数据


        List<Map<String,Object>> series = new ArrayList();      //数据对象
        Map<String,Object> seriesMap = new HashMap();
        seriesMap.put("name","评价数");
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
    @RequestMapping("/auto-rate-log/report/count")
    @ResponseBody
    public Map<String, Object> getSalaryImage() throws Exception {
        try {
            Map<String,Object> map = getCountRate(Utils.getUserId());
            return ResultJson.resultSuccess(map);
        } catch (Exception e) {
            throw e;
        }
    }


}
