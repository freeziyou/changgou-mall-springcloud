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
@Table(name="tb_category_report")
public class CategoryReport implements Serializable{

	/**
	 * 1级分类
	 */
    @Column(name = "category_id1")
	private Integer categoryId1;

	/**
	 * 2级分类
	 */
    @Column(name = "category_id2")
	private Integer categoryId2;

	/**
	 * 3级分类
	 */
    @Column(name = "category_id3")
	private Integer categoryId3;

	/**
	 * 统计日期
	 */
	@Id
    @Column(name = "count_date")
	private Date countDate;

	/**
	 * 销售数量
	 */
    @Column(name = "num")
	private Integer num;

	/**
	 * 销售额
	 */
    @Column(name = "money")
	private Integer money;



	public Integer getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(Integer categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public Integer getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(Integer categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public Integer getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Integer categoryId3) {
		this.categoryId3 = categoryId3;
	}

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
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

}
