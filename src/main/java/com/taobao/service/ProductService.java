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

/**
 * Created by star on 15/5/19.
 */
@Service
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    /**
     *  通过Num_iid 获取商品信息
     * @param numId
     * @return
     */
    public Item getProductByNumId(Long numId) throws Exception{
        TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                Constants.TB_SANDBOX_APP_KEY,
                Constants.TB_SANDBOX_APP_SECRET);
        ItemGetRequest req=new ItemGetRequest();
        req.setFields("num_iid,title,price,desc_modules,sell_point,nike,detail_url");
        req.setNumIid(numId);
        try {
            ItemGetResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response.getItem();
        } catch (ApiException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
