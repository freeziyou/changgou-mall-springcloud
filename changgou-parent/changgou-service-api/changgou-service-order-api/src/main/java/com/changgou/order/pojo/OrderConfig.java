package com.changgou.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name="tb_order_config")
public class OrderConfig implements Serializable{

	/**
	 * ID
	 */
	@Id
    @Column(name = "id")
	private Integer id;

	/**
	 * 正常订单超时时间（分）
	 */
    @Column(name = "order_timeout")
	private Integer orderTimeout;

	/**
	 * 秒杀订单超时时间（分）
	 */
    @Column(name = "seckill_timeout")
	private Integer seckillTimeout;

	/**
	 * 自动收货（天）
	 */
    @Column(name = "take_timeout")
	private Integer takeTimeout;

	/**
	 * 售后期限
	 */
    @Column(name = "service_timeout")
	private Integer serviceTimeout;

	/**
	 * 自动五星好评
	 */
    @Column(name = "comment_timeout")
	private Integer commentTimeout;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderTimeout() {
		return orderTimeout;
	}

	public void setOrderTimeout(Integer orderTimeout) {
		this.orderTimeout = orderTimeout;
	}

	public Integer getSeckillTimeout() {
		return seckillTimeout;
	}

	public void setSeckillTimeout(Integer seckillTimeout) {
		this.seckillTimeout = seckillTimeout;
	}

	public Integer getTakeTimeout() {
		return takeTimeout;
	}

	public void setTakeTimeout(Integer takeTimeout) {
		this.takeTimeout = takeTimeout;
	}

	public Integer getServiceTimeout() {
		return serviceTimeout;
	}

	public void setServiceTimeout(Integer serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
	}

	public Integer getCommentTimeout() {
		return commentTimeout;
	}

	public void setCommentTimeout(Integer commentTimeout) {
		this.commentTimeout = commentTimeout;
	}

}
