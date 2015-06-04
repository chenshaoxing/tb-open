package com.taobao.controller;

import com.taobao.api.domain.Item;
import com.taobao.common.ResultJson;
import com.taobao.common.Utils;
import com.taobao.entity.User;
import com.taobao.service.ProductService;
import com.taobao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
}
