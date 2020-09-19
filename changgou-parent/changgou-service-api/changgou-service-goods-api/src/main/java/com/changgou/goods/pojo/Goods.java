package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/19/2020 13:54
 * @description TODO
 */
public class Goods implements Serializable {

    /**
     * SPU
     */
    private Spu spu;

    /**
     * SKU 集合
     */
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "spu=" + spu +
                ", skuList=" + skuList +
                '}';
    }
}
