package com.changgou.goods.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:admin
 * @Description:Sku构建
 * @Date 2019/6/14 19:13
 *****/
@ApiModel(description = "Sku", value = "Sku")
@Table(name = "tb_sku")
public class Sku implements Serializable {

    @ApiModelProperty(value = "商品id", required = false)
    @Id
    @Column(name = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//商品id
    @ApiModelProperty(value = "商品条码", required = false)
    @Column(name = "sn")
    private String sn;//商品条码
    @ApiModelProperty(value = "SKU名称", required = false)
    @Column(name = "name")
    private String name;//SKU名称
    @ApiModelProperty(value = "价格（分）", required = false)
    @Column(name = "price")
    private Integer price;//价格（分）
    @ApiModelProperty(value = "库存数量", required = false)
    @Column(name = "num")
    private Integer num;//库存数量
    @ApiModelProperty(value = "库存预警数量", required = false)
    @Column(name = "alert_num")
    private Integer alertNum;//库存预警数量
    @ApiModelProperty(value = "商品图片", required = false)
    @Column(name = "image")
    private String image;//商品图片
    @ApiModelProperty(value = "商品图片列表", required = false)
    @Column(name = "images")
    private String images;//商品图片列表
    @ApiModelProperty(value = "重量（克）", required = false)
    @Column(name = "weight")
    private Integer weight;//重量（克）
    @ApiModelProperty(value = "创建时间", required = false)
    @Column(name = "create_time")
    private Date createTime;//创建时间
    @ApiModelProperty(value = "更新时间", required = false)
    @Column(name = "update_time")
    private Date updateTime;//更新时间
    @ApiModelProperty(value = "SPUID", required = false)
    @Column(name = "spu_id")
    private Long spuId;//SPUID
    @ApiModelProperty(value = "类目ID", required = false)
    @Column(name = "category_id")
    private Integer categoryId;//类目ID
    @ApiModelProperty(value = "类目名称", required = false)
    @Column(name = "category_name")
    private String categoryName;//类目名称
    @ApiModelProperty(value = "品牌名称", required = false)
    @Column(name = "brand_name")
    private String brandName;//品牌名称
    @ApiModelProperty(value = "规格", required = false)
    @Column(name = "spec")
    private String spec;//规格
    @ApiModelProperty(value = "销量", required = false)
    @Column(name = "sale_num")
    private Integer saleNum;//销量
    @ApiModelProperty(value = "评论数", required = false)
    @Column(name = "comment_num")
    private Integer commentNum;//评论数
    @ApiModelProperty(value = "商品状态 1-正常，2-下架，3-删除", required = false)
    @Column(name = "status")
    private String status;//商品状态 1-正常，2-下架，3-删除

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
    public Integer getPrice() {
        return price;
    }

    //set方法
    public void setPrice(Integer price) {
        this.price = price;
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
    public Integer getAlertNum() {
        return alertNum;
    }

    //set方法
    public void setAlertNum(Integer alertNum) {
        this.alertNum = alertNum;
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
    public Integer getWeight() {
        return weight;
    }

    //set方法
    public void setWeight(Integer weight) {
        this.weight = weight;
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
    public Date getUpdateTime() {
        return updateTime;
    }

    //set方法
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    //get方法
    public Long getSpuId() {
        return spuId;
    }

    //set方法
    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    //get方法
    public Integer getCategoryId() {
        return categoryId;
    }

    //set方法
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    //get方法
    public String getCategoryName() {
        return categoryName;
    }

    //set方法
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //get方法
    public String getBrandName() {
        return brandName;
    }

    //set方法
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    //get方法
    public String getSpec() {
        return spec;
    }

    //set方法
    public void setSpec(String spec) {
        this.spec = spec;
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
    public String getStatus() {
        return status;
    }

    //set方法
    public void setStatus(String status) {
        this.status = status;
    }


}
