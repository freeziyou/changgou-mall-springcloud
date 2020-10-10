package com.changgou.order.pojo;

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
@Table(name="tb_order_log")
public class OrderLog implements Serializable{

	/**
	 * ID
	 */
	@Id
    @Column(name = "id")
	private String id;

	/**
	 * 操作员
	 */
    @Column(name = "operater")
	private String operater;

	/**
	 * 操作时间
	 */
    @Column(name = "operate_time")
	private Date operateTime;

	/**
	 * 订单ID
	 */
    @Column(name = "order_id")
	private String orderId;

	/**
	 * 订单状态,0未完成，1已完成，2，已退货
	 */
    @Column(name = "order_status")
	private String orderStatus;

	/**
	 * 付款状态  0:未支付，1：已支付，2：支付失败
	 */
    @Column(name = "pay_status")
	private String payStatus;

	/**
	 * 发货状态
	 */
    @Column(name = "consign_status")
	private String consignStatus;

	/**
	 * 备注
	 */
    @Column(name = "remarks")
	private String remarks;

	/**
	 * 支付金额
	 */
    @Column(name = "money")
	private Integer money;

	/**
	 * 
	 */
    @Column(name = "username")
	private String username;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getConsignStatus() {
		return consignStatus;
	}

	public void setConsignStatus(String consignStatus) {
		this.consignStatus = consignStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
