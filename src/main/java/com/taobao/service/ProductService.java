package com.taobao.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by star on 15/5/19.
 */
@Service
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Resource(name = "taoBaoClient")
    private TaobaoClient taobaoClient;

    /**
     *  通过Num_iid 获取商品信息
     * @param numId
     * @return
     */
    public Item getProductByNumId(Long numId) throws Exception{

        ItemGetRequest req=new ItemGetRequest();
        req.setFields("num_iid,title,price,desc_modules,sell_point,nike,detail_url");
        req.setNumIid(numId);
        try {
            ItemGetResponse response = taobaoClient.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response.getItem();
        } catch (ApiException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     *  通过Num_iid 获取商品信息
     * @param numId
     * @return
     */
    public Item getProductByNumId(Long numId,String sessionKey) throws Exception{
        TaobaoClient taobaoClient1 = new DefaultTaobaoClient(Constants.TB_ONLINE_API_URL,Constants.TB_ONLINE_APP_KEY,
                Constants.TB_ONLINE_APP_SECRET);
        ItemGetRequest req=new ItemGetRequest();
        req.setFields("num_iid,detail_url");
        req.setNumIid(numId);
        try {
            ItemGetResponse response = taobaoClient1.execute(req , sessionKey);
            return response.getItem();
        } catch (ApiException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        ProductService service = new ProductService();
        service.getProductByNumId(40885824720L,"620182534f37a03b480f64bd97dde7f61ZZ9bb5b0fa4c2b178766584");
    }
}
