package com.changgou.user.pojo;

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
@Table(name="tb_user")
public class User implements Serializable{

	/**
	 * 用户名
	 */
	@Id
    @Column(name = "username")
	private String username;

	/**
	 * 密码，加密存储
	 */
    @Column(name = "password")
	private String password;

	/**
	 * 注册手机号
	 */
    @Column(name = "phone")
	private String phone;

	/**
	 * 注册邮箱
	 */
    @Column(name = "email")
	private String email;

	/**
	 * 创建时间
	 */
    @Column(name = "created")
	private Date created;

	/**
	 * 修改时间
	 */
    @Column(name = "updated")
	private Date updated;

	/**
	 * 会员来源：1:PC，2：H5，3：Android，4：IOS
	 */
    @Column(name = "source_type")
	private String sourceType;

	/**
	 * 昵称
	 */
    @Column(name = "nick_name")
	private String nickName;

	/**
	 * 真实姓名
	 */
    @Column(name = "name")
	private String name;

	/**
	 * 使用状态（1正常 0非正常）
	 */
    @Column(name = "status")
	private String status;

	/**
	 * 头像地址
	 */
    @Column(name = "head_pic")
	private String headPic;

	/**
	 * QQ号码
	 */
    @Column(name = "qq")
	private String qq;

	/**
	 * 手机是否验证 （0否  1是）
	 */
    @Column(name = "is_mobile_check")
	private String isMobileCheck;

	/**
	 * 邮箱是否检测（0否  1是）
	 */
    @Column(name = "is_email_check")
	private String isEmailCheck;

	/**
	 * 性别，1男，0女
	 */
    @Column(name = "sex")
	private String sex;

	/**
	 * 会员等级
	 */
    @Column(name = "user_level")
	private Integer userLevel;

	/**
	 * 积分
	 */
    @Column(name = "points")
	private Integer points;

	/**
	 * 经验值
	 */
    @Column(name = "experience_value")
	private Integer experienceValue;

	/**
	 * 出生年月日
	 */
    @Column(name = "birthday")
	private Date birthday;

	/**
	 * 最后登录时间
	 */
    @Column(name = "last_login_time")
	private Date lastLoginTime;



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIsMobileCheck() {
		return isMobileCheck;
	}

	public void setIsMobileCheck(String isMobileCheck) {
		this.isMobileCheck = isMobileCheck;
	}

	public String getIsEmailCheck() {
		return isEmailCheck;
	}

	public void setIsEmailCheck(String isEmailCheck) {
		this.isEmailCheck = isEmailCheck;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getExperienceValue() {
		return experienceValue;
	}

	public void setExperienceValue(Integer experienceValue) {
		this.experienceValue = experienceValue;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
