package com.changgou.goods.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_stock_back")
public class StockBack implements Serializable {

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * SKU的id
     */
    @Id
    @Column(name = "sku_id")
    private String skuId;

    /**
     * 回滚数量
     */
    @Column(name = "num")
    private Integer num;

    /**
     * 回滚状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 回滚时间
     */
    @Column(name = "back_time")
    private Date backTime;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

}
