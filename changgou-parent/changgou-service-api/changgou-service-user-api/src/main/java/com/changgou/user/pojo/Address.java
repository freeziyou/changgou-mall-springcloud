package com.changgou.user.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name="tb_address")
public class Address implements Serializable{

	/**
	 * 
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;

	/**
	 * 用户名
	 */
    @Column(name = "username")
	private String username;

	/**
	 * 省
	 */
    @Column(name = "provinceid")
	private String provinceid;

	/**
	 * 市
	 */
    @Column(name = "cityid")
	private String cityid;

	/**
	 * 县/区
	 */
    @Column(name = "areaid")
	private String areaid;

	/**
	 * 电话
	 */
    @Column(name = "phone")
	private String phone;

	/**
	 * 详细地址
	 */
    @Column(name = "address")
	private String address;

	/**
	 * 联系人
	 */
    @Column(name = "contact")
	private String contact;

	/**
	 * 是否是默认 1默认 0否
	 */
    @Column(name = "is_default")
	private String isDefault;

	/**
	 * 别名
	 */
    @Column(name = "alias")
	private String alias;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
