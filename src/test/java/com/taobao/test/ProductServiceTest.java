package com.taobao.test;

import com.taobao.api.domain.Item;
import com.taobao.service.ProductService;
import org.junit.Test;

/**
 * Created by star on 15/5/19.
 */
public class ProductServiceTest {
    ProductService productService = new ProductService();
    @Test
    public void testGetProductByNumId() throws Exception {
        Long numId = 2100627571609L;
        Item item = productService.getProductByNumId(numId);
        System.out.println(item);
    }
}
