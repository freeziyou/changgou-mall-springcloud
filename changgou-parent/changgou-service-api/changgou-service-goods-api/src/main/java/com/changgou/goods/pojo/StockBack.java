package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:StockBack构建
 * @Date 2019/6/14 19:13
 *****/
@ApiModel(description = "StockBack",value = "StockBack")
@Table(name="tb_stock_back")
public class StockBack implements Serializable{

	@ApiModelProperty(value = "订单id",required = false)
    @Column(name = "order_id")
	private String orderId;//订单id
	@ApiModelProperty(value = "SKU的id",required = false)
	@Id
    @Column(name = "sku_id")
	private String skuId;//SKU的id
	@ApiModelProperty(value = "回滚数量",required = false)
    @Column(name = "num")
	private Integer num;//回滚数量
	@ApiModelProperty(value = "回滚状态",required = false)
    @Column(name = "status")
	private String status;//回滚状态
	@ApiModelProperty(value = "创建时间",required = false)
    @Column(name = "create_time")
	private Date createTime;//创建时间
	@ApiModelProperty(value = "回滚时间",required = false)
    @Column(name = "back_time")
	private Date backTime;//回滚时间


	//get方法
	public String getOrderId() {
		return orderId;
	}

	//set方法
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	//get方法
	public String getSkuId() {
		return skuId;
	}

	//set方法
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	//get方法
	public Integer getNum() {
		return num;
	}

	//set方法
	public void setNum(Integer num) {
		this.num = num;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getBackTime() {
		return backTime;
	}

	//set方法
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}


}
