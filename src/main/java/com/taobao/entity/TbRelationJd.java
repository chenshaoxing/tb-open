package com.taobao.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by star on 15/6/11.
 */
@Entity
@Table(name = "t_tb_jd_relation")
public class TbRelationJd extends  BaseDomain{
    @ManyToOne
    @JoinColumn(name = "tb_id", nullable = false)
    private TaoBaoProduct product;


    @ManyToOne
    @JoinColumn(name = "jd_id", nullable = false)
    private JDProduct jdProduct;

    public TaoBaoProduct getProduct() {
        return product;
    }

    public void setProduct(TaoBaoProduct product) {

        this.product = product;
    }

    public JDProduct getJdProduct() {
        return jdProduct;
    }

    public void setJdProduct(JDProduct jdProduct) {
        this.jdProduct = jdProduct;
    }
}
