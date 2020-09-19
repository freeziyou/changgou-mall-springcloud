package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_pref")
public class Pref implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 分类ID
     */
    @Column(name = "cate_id")
    private Integer cateId;

    /**
     * 消费金额
     */
    @Column(name = "buy_money")
    private Integer buyMoney;

    /**
     * 优惠金额
     */
    @Column(name = "pre_money")
    private Integer preMoney;

    /**
     * 活动开始日期
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 活动截至日期
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 类型,1:普通订单，2：限时活动
     */
    @Column(name = "type")
    private String type;

    /**
     * 状态,1:有效，0：无效
     */
    @Column(name = "state")
    private String state;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Integer buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Integer getPreMoney() {
        return preMoney;
    }

    public void setPreMoney(Integer preMoney) {
        this.preMoney = preMoney;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
