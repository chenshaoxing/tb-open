package com.taobao.service;

import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemPriceUpdateRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemPriceUpdateResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.common.Constants;
import com.taobao.entity.TaoBaoProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-04
 * Time: 17:58
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "taoBaoProductInfoService")
public class TaoBaoProductInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(TaoBaoProductInfoService.class);

    @Resource(name = "taoBaoClient")
    private TaobaoClient taobaoClient ;

    public ItemsOnsaleGetResponse search(Long currentPage,Long pageSize,String nameSearch,String sessionKey) throws Exception{
        try{
            ItemsOnsaleGetRequest req=new ItemsOnsaleGetRequest();
            req.setFields("num_iid,num,pic_url,detail_url,nick,sold_quantity,title,price");
            req.setPageNo(currentPage);
            req.setQ(nameSearch);
            req.setOrderBy("list_time:desc");
            req.setIsTaobao(true);
            req.setPageSize(pageSize);
            ItemsOnsaleGetResponse response = taobaoClient.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public boolean updateProductPrice(TaoBaoProduct taoBaoProduct,String sessionKey){
        try{
            ItemPriceUpdateRequest req=new ItemPriceUpdateRequest();
            req.setNumIid(taoBaoProduct.getNumIid());
            req.setPrice(taoBaoProduct.getPrice() + "");
            ItemPriceUpdateResponse response = taobaoClient.execute(req , sessionKey);
            return response.isSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            return  false;
        }
    }


    public static void main(String[] args) {

//        search();
    }


}
