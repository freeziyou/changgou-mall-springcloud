package com.changgou.goods.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_spu")
public class Spu implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 货号
     */
    @Column(name = "sn")
    private String sn;

    /**
     * SPU名
     */
    @Column(name = "name")
    private String name;

    /**
     * 副标题
     */
    @Column(name = "caption")
    private String caption;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 一级分类
     */
    @Column(name = "category1_id")
    private Integer category1Id;

    /**
     * 二级分类
     */
    @Column(name = "category2_id")
    private Integer category2Id;

    /**
     * 三级分类
     */
    @Column(name = "category3_id")
    private Integer category3Id;

    /**
     * 模板ID
     */
    @Column(name = "template_id")
    private Integer templateId;

    /**
     * 运费模板id
     */
    @Column(name = "freight_id")
    private Integer freightId;

    /**
     * 图片
     */
    @Column(name = "image")
    private String image;

    /**
     * 图片列表
     */
    @Column(name = "images")
    private String images;

    /**
     * 售后服务
     */
    @Column(name = "sale_service")
    private String saleService;

    /**
     * 介绍
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * 规格列表
     */
    @Column(name = "spec_items")
    private String specItems;

    /**
     * 参数列表
     */
    @Column(name = "para_items")
    private String paraItems;

    /**
     * 销量
     */
    @Column(name = "sale_num")
    private Integer saleNum;

    /**
     * 评论数
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 是否上架,0已下架，1已上架
     */
    @Column(name = "is_marketable")
    private String isMarketable;

    /**
     * 是否启用规格
     */
    @Column(name = "is_enable_spec")
    private String isEnableSpec;

    /**
     * 是否删除,0:未删除，1：已删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 审核状态，0：未审核，1：已审核，2：审核不通过
     */
    @Column(name = "status")
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(Integer category1Id) {
        this.category1Id = category1Id;
    }

    public Integer getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(Integer category2Id) {
        this.category2Id = category2Id;
    }

    public Integer getCategory3Id() {
        return category3Id;
    }

    public void setCategory3Id(Integer category3Id) {
        this.category3Id = category3Id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getFreightId() {
        return freightId;
    }

    public void setFreightId(Integer freightId) {
        this.freightId = freightId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSaleService() {
        return saleService;
    }

    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecItems() {
        return specItems;
    }

    public void setSpecItems(String specItems) {
        this.specItems = specItems;
    }

    public String getParaItems() {
        return paraItems;
    }

    public void setParaItems(String paraItems) {
        this.paraItems = paraItems;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable;
    }

    public String getIsEnableSpec() {
        return isEnableSpec;
    }

    public void setIsEnableSpec(String isEnableSpec) {
        this.isEnableSpec = isEnableSpec;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
