package com.taobao.test;

import java.math.BigDecimal;

/**
 * Created by star on 15/7/5.
 */
public class Add {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("28.9");
        BigDecimal bigDecimal1 = new BigDecimal("28.9");
        System.out.println(bigDecimal.subtract(bigDecimal1));
        int i = bigDecimal.compareTo(bigDecimal1);
        System.out.println(i);
    }
}
