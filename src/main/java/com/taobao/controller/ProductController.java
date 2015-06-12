package com.taobao.controller;

import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.common.ResultJson;
import com.taobao.common.Utils;
import com.taobao.dao.PageInfo;
import com.taobao.entity.*;
import com.taobao.service.*;
import com.taobao.service.impl.UserRelationJdServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-21
 * Time: 10:29
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;

    @Resource
    private JDProductService jdProductService;

    @Resource
    private TaoBaoProductInfoService taoBaoProductInfoService;

    @Resource
    private TaoBaoProductService taoBaoProductService;

    @Resource
    private UserRelationJdService userRelationJdService;

    @Resource
    private CrawlJdProductInfoService crawlJdProductInfoService;

    @Resource
    private TbRelationJdService tbRelationJdService;


    @RequestMapping("/product")
    public String index(){
        return "product/list";
    }

    @RequestMapping("/jd-product")
    public String jdIndex(){
        return "product/jd-list";
    }

    @RequestMapping(value = "/product/info")
    @ResponseBody
    public Map<String, Object> getProductInfo(@RequestParam Long numId
                                             ) throws Exception{
        try{

//            if(userId != null){
            User user = userService.findById(Utils.getUserId());
                Item item = productService.getProductByNumId(numId,user.getSessionKey());
                return ResultJson.resultSuccess(item);
//            }else{
//                Item item = productService.getProductByNumId(numId);
//                return ResultJson.resultSuccess(item);
//            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping("/product/list")
    @ResponseBody
    public  Map<String,Object> getOnSellProduct(@RequestParam Long currentPage,
                                                @RequestParam Long pageSize,
                                                @RequestParam(required = false) String name) throws Exception{
        try{
            User user = userService.findById(Utils.getUserId());
            ItemsOnsaleGetResponse response = taoBaoProductInfoService.search(currentPage,pageSize,name,user.getSessionKey());
            PageInfo pageInfo = new PageInfo(pageSize,response.getTotalResults());
            pageInfo.setList(response.getItems());
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping("/product/jd-list")
    @ResponseBody
    public  Map<String,Object> getJdProduct(@RequestParam int currentPage,
                                                @RequestParam int pageSize,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String skuId) throws Exception{
        try{
            User user = userService.findById(Utils.getUserId());
            PageInfo pageInfo = userRelationJdService.getList(currentPage,pageSize,name,skuId,user.getId());
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping("/product/add-jd")
    @ResponseBody
    public  Map<String,Object> addJdProduct(@RequestParam String skuIds) throws Exception{
        try{
            Map<String,Object> result = new HashMap<String, Object>();
            List<String> success = new ArrayList<String>();
            List<String> failed = new ArrayList<String>();
            User user  = userService.findById(Utils.getUserId());
            if(StringUtils.isNotEmpty(skuIds)){
                String [] ids = skuIds.split(",");
                for(String id:ids){
                    try{
                        Map<String,Object> map = crawlJdProductInfoService.crawl(id);
                        if(map != null && map.size() > 0){
                            JDProduct jdProduct = jdProductService.save(map);
                            UserRelationJd relationJd = new UserRelationJd();
                            relationJd.setProduct(jdProduct);
                            relationJd.setUser(user);
                            userRelationJdService.add(relationJd);
                            success.add(skuIds);
                        }else{
                            failed.add(skuIds);
                        }
                    }catch (Exception e){
                        failed.add(skuIds);
                        continue;
                    }
                }
            }
            result.put("success",success);
            result.put("failed",failed);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping("/product/update-jd")
    @ResponseBody
    public  Map<String,Object> updateJdProduct(@RequestParam String skuId) throws Exception{
        try{
            JDProduct jdProduct = jdProductService.findBySkuId(skuId);
            if(jdProduct != null){
                Map<String,Object> data = crawlJdProductInfoService.crawl(skuId);
                jdProductService.save(data);
            }
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping("/product/delete-jd")
    @ResponseBody
    public  Map<String,Object> deleteJdProduct(@RequestParam Long id) throws Exception{
        try{
            UserRelationJd userRelationJd = new UserRelationJd();
            userRelationJd.setId(id);
            userRelationJd = userRelationJdService.findById(userRelationJd);
            userRelationJdService.delete(userRelationJd);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping("/product/relation-jd")
    @ResponseBody
    public  Map<String,Object> relationJdProduct(@RequestParam Long jdId,
                                                 @RequestParam Long numIid,
                                                 @RequestParam Float tbPrice,
                                                 @RequestParam Float differenceOfPrices) throws Exception{
        try{
            User user = userService.findById(Utils.getUserId());
            JDProduct jdProduct = jdProductService.findById(jdId);
            TaoBaoProduct taoBaoProduct = new TaoBaoProduct();
            taoBaoProduct.setUser(user);
            taoBaoProduct.setPrice(tbPrice);
            taoBaoProduct.setDifferenceOfPrices(differenceOfPrices);
            taoBaoProduct.setNumIid(numIid);
            boolean flag = taoBaoProductService.addAndRelationJd(taoBaoProduct,jdProduct);
            if(flag){
                return ResultJson.resultSuccess();
            }else{
                return ResultJson.resultError();
            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
