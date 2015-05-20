package com.taobao.controller;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.response.TraderateAddResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 15/5/15.
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/auth")
    @ResponseBody
    public void save(@RequestParam String code,@RequestParam String state) throws Exception{


    }

    @RequestMapping(value = "/main")
    public String index(Model model) {
        return "main/main";
    }

    public static void main(String[] args) throws Exception {
//        getSession();
        ping();
    }

    public static void getSession() throws UnsupportedEncodingException {
        String url="https://oauth.taobao.com/token";
        Map<String,String> props=new HashMap<String,String>();
        props.put("grant_type","authorization_code");
/*测试时，需把test参数换成自己应用对应的值*/
        props.put("code","SNY0GVCd7a8iQMwMoqMIpvNd634456");
        props.put("client_id","23175152");
        props.put("client_secret","fe3900d9e3c1e8c65b37da4d0237a953");
        props.put("redirect_uri","http://csx1991.nat123.net:15569");
        props.put("view","web");
        props.put("state","1212");
        String s="";
        try{
            s= WebUtils.doPost(url, props, 30000, 30000);
            System.out.println(s);
        }catch(IOException e){
            e.printStackTrace();}
    }

    public static void ping() throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23175152", "fe3900d9e3c1e8c65b37da4d0237a953");
        TraderateAddRequest req=new TraderateAddRequest();
        req.setTid(1048548482818420L);
//        req.setOid(1234L);
        req.setResult("good");
        req.setRole("seller");
        req.setContent("很好的买家");
        req.setAnony(true);
        TraderateAddResponse response = client.execute(req , "6200107f34966f3ZZb7395547912167221b5990a9ebbc63178766584");
        System.out.println(response.getTradeRate().getTid());
    }
}
