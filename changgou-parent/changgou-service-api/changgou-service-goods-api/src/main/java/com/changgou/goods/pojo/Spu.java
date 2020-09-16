package com.changgou.goods.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:Spu构建
 * @Date 2019/6/14 19:13
 *****/
@ApiModel(description = "Spu",value = "Spu")
@Table(name="tb_spu")
public class Spu implements Serializable{

	@ApiModelProperty(value = "主键",required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	@Id
	@Column(name = "id")
	private Long id;//主键
	@ApiModelProperty(value = "货号",required = false)
    @Column(name = "sn")
	private String sn;//货号
	@ApiModelProperty(value = "SPU名",required = false)
    @Column(name = "name")
	private String name;//SPU名
	@ApiModelProperty(value = "副标题",required = false)
    @Column(name = "caption")
	private String caption;//副标题
	@ApiModelProperty(value = "品牌ID",required = false)
    @Column(name = "brand_id")
	private Integer brandId;//品牌ID
	@ApiModelProperty(value = "一级分类",required = false)
    @Column(name = "category1_id")
	private Integer category1Id;//一级分类
	@ApiModelProperty(value = "二级分类",required = false)
    @Column(name = "category2_id")
	private Integer category2Id;//二级分类
	@ApiModelProperty(value = "三级分类",required = false)
    @Column(name = "category3_id")
	private Integer category3Id;//三级分类
	@ApiModelProperty(value = "模板ID",required = false)
    @Column(name = "template_id")
	private Integer templateId;//模板ID
	@ApiModelProperty(value = "运费模板id",required = false)
    @Column(name = "freight_id")
	private Integer freightId;//运费模板id
	@ApiModelProperty(value = "图片",required = false)
    @Column(name = "image")
	private String image;//图片
	@ApiModelProperty(value = "图片列表",required = false)
    @Column(name = "images")
	private String images;//图片列表
	@ApiModelProperty(value = "售后服务",required = false)
    @Column(name = "sale_service")
	private String saleService;//售后服务
	@ApiModelProperty(value = "介绍",required = false)
    @Column(name = "introduction")
	private String introduction;//介绍
	@ApiModelProperty(value = "规格列表",required = false)
    @Column(name = "spec_items")
	private String specItems;//规格列表
	@ApiModelProperty(value = "参数列表",required = false)
    @Column(name = "para_items")
	private String paraItems;//参数列表
	@ApiModelProperty(value = "销量",required = false)
    @Column(name = "sale_num")
	private Integer saleNum;//销量
	@ApiModelProperty(value = "评论数",required = false)
    @Column(name = "comment_num")
	private Integer commentNum;//评论数
	@ApiModelProperty(value = "是否上架",required = false)
    @Column(name = "is_marketable")
	private String isMarketable;//是否上架
	@ApiModelProperty(value = "是否启用规格",required = false)
    @Column(name = "is_enable_spec")
	private String isEnableSpec;//是否启用规格
	@ApiModelProperty(value = "是否删除",required = false)
    @Column(name = "is_delete")
	private String isDelete;//是否删除
	@ApiModelProperty(value = "审核状态",required = false)
    @Column(name = "status")
	private String status;//审核状态


	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getSn() {
		return sn;
	}

	//set方法
	public void setSn(String sn) {
		this.sn = sn;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public String getCaption() {
		return caption;
	}

	//set方法
	public void setCaption(String caption) {
		this.caption = caption;
	}
	//get方法
	public Integer getBrandId() {
		return brandId;
	}

	//set方法
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	//get方法
	public Integer getCategory1Id() {
		return category1Id;
	}

	//set方法
	public void setCategory1Id(Integer category1Id) {
		this.category1Id = category1Id;
	}
	//get方法
	public Integer getCategory2Id() {
		return category2Id;
	}

	//set方法
	public void setCategory2Id(Integer category2Id) {
		this.category2Id = category2Id;
	}
	//get方法
	public Integer getCategory3Id() {
		return category3Id;
	}

	//set方法
	public void setCategory3Id(Integer category3Id) {
		this.category3Id = category3Id;
	}
	//get方法
	public Integer getTemplateId() {
		return templateId;
	}

	//set方法
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	//get方法
	public Integer getFreightId() {
		return freightId;
	}

	//set方法
	public void setFreightId(Integer freightId) {
		this.freightId = freightId;
	}
	//get方法
	public String getImage() {
		return image;
	}

	//set方法
	public void setImage(String image) {
		this.image = image;
	}
	//get方法
	public String getImages() {
		return images;
	}

	//set方法
	public void setImages(String images) {
		this.images = images;
	}
	//get方法
	public String getSaleService() {
		return saleService;
	}

	//set方法
	public void setSaleService(String saleService) {
		this.saleService = saleService;
	}
	//get方法
	public String getIntroduction() {
		return introduction;
	}

	//set方法
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	//get方法
	public String getSpecItems() {
		return specItems;
	}

	//set方法
	public void setSpecItems(String specItems) {
		this.specItems = specItems;
	}
	//get方法
	public String getParaItems() {
		return paraItems;
	}

	//set方法
	public void setParaItems(String paraItems) {
		this.paraItems = paraItems;
	}
	//get方法
	public Integer getSaleNum() {
		return saleNum;
	}

	//set方法
	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}
	//get方法
	public Integer getCommentNum() {
		return commentNum;
	}

	//set方法
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	//get方法
	public String getIsMarketable() {
		return isMarketable;
	}

	//set方法
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	//get方法
	public String getIsEnableSpec() {
		return isEnableSpec;
	}

	//set方法
	public void setIsEnableSpec(String isEnableSpec) {
		this.isEnableSpec = isEnableSpec;
	}
	//get方法
	public String getIsDelete() {
		return isDelete;
	}

	//set方法
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}


}
