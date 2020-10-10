package com.changgou.order.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name="tb_preferential")
public class Preferential implements Serializable{

	/**
	 * ID
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;

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
	 * 品类ID
	 */
    @Column(name = "category_id")
	private Long categoryId;

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
	 * 状态
	 */
    @Column(name = "state")
	private String state;

	/**
	 * 类型1不翻倍 2翻倍
	 */
    @Column(name = "type")
	private String type;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
