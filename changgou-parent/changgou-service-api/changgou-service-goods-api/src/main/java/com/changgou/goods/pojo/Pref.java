package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:Pref构建
 * @Date 2019/6/14 19:13
 *****/
@ApiModel(description = "Pref",value = "Pref")
@Table(name="tb_pref")
public class Pref implements Serializable{

	@ApiModelProperty(value = "ID",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID
	@ApiModelProperty(value = "分类ID",required = false)
    @Column(name = "cate_id")
	private Integer cateId;//分类ID
	@ApiModelProperty(value = "消费金额",required = false)
    @Column(name = "buy_money")
	private Integer buyMoney;//消费金额
	@ApiModelProperty(value = "优惠金额",required = false)
    @Column(name = "pre_money")
	private Integer preMoney;//优惠金额
	@ApiModelProperty(value = "活动开始日期",required = false)
    @Column(name = "start_time")
	private Date startTime;//活动开始日期
	@ApiModelProperty(value = "活动截至日期",required = false)
    @Column(name = "end_time")
	private Date endTime;//活动截至日期
	@ApiModelProperty(value = "类型,1:普通订单，2：限时活动",required = false)
    @Column(name = "type")
	private String type;//类型,1:普通订单，2：限时活动
	@ApiModelProperty(value = "状态,1:有效，0：无效",required = false)
    @Column(name = "state")
	private String state;//状态,1:有效，0：无效


	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public Integer getCateId() {
		return cateId;
	}

	//set方法
	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}
	//get方法
	public Integer getBuyMoney() {
		return buyMoney;
	}

	//set方法
	public void setBuyMoney(Integer buyMoney) {
		this.buyMoney = buyMoney;
	}
	//get方法
	public Integer getPreMoney() {
		return preMoney;
	}

	//set方法
	public void setPreMoney(Integer preMoney) {
		this.preMoney = preMoney;
	}
	//get方法
	public Date getStartTime() {
		return startTime;
	}

	//set方法
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	//get方法
	public Date getEndTime() {
		return endTime;
	}

	//set方法
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	//get方法
	public String getType() {
		return type;
	}

	//set方法
	public void setType(String type) {
		this.type = type;
	}
	//get方法
	public String getState() {
		return state;
	}

	//set方法
	public void setState(String state) {
		this.state = state;
	}


}
