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
@Table(name="tb_return_order_item")
public class ReturnOrderItem implements Serializable{

	/**
	 * ID
	 */
	@Id
    @Column(name = "id")
	private Long id;

	/**
	 * 分类ID
	 */
    @Column(name = "category_id")
	private Long categoryId;

	/**
	 * SPU_ID
	 */
    @Column(name = "spu_id")
	private Long spuId;

	/**
	 * SKU_ID
	 */
    @Column(name = "sku_id")
	private Long skuId;

	/**
	 * 订单ID
	 */
    @Column(name = "order_id")
	private Long orderId;

	/**
	 * 订单明细ID
	 */
    @Column(name = "order_item_id")
	private Long orderItemId;

	/**
	 * 退货订单ID
	 */
    @Column(name = "return_order_id")
	private Long returnOrderId;

	/**
	 * 标题
	 */
    @Column(name = "title")
	private String title;

	/**
	 * 单价
	 */
    @Column(name = "price")
	private Integer price;

	/**
	 * 数量
	 */
    @Column(name = "num")
	private Integer num;

	/**
	 * 总金额
	 */
    @Column(name = "money")
	private Integer money;

	/**
	 * 支付金额
	 */
    @Column(name = "pay_money")
	private Integer payMoney;

	/**
	 * 图片地址
	 */
    @Column(name = "image")
	private String image;

	/**
	 * 重量
	 */
    @Column(name = "weight")
	private Integer weight;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
