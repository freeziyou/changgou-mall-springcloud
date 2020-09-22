package com.changgou.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/22/2020 14:25
 * @description TODO
 */
@Document(indexName = "skuinfo", type = "docs")
public class SkuInfo implements Serializable {
    /**
     * 商品 id, 同时也是商品编号
     */
    @Id
    private Long id;

    /**
     * SKU名称
     * FieldType.Text: 类型, Text 支持分词
     * index = true: 添加数据时, 是否分词
     * analyzer = "ik_smart": 创建索引的分词器
     * store = false: 是否存储
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", index = true, store = false)
    private String name;

    /**
     * 商品价格, 单位为元
     */
    @Field(type = FieldType.Double)
    private Long price;

    /**
     * 库存数量
     */
    private Integer num;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    private String status;

    private Date createTime;

    private Date updateTime;

    private String isDefault;

    private Long spuId;

    private Long categoryId;

    /**
     * 类目名称
     * type = FieldType.Keyword: 不分词
     */
    @Field(type = FieldType.Keyword)
    private String categoryName;

    /**
     * 品牌名称
     * type = FieldType.Keyword: 不分词
     */
    @Field(type = FieldType.Keyword)
    private String brandName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 规格参数
     */
    private Map<String, Object> specMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Map<String, Object> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, Object> specMap) {
        this.specMap = specMap;
    }

    @Override
    public String toString() {
        return "SkuInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDefault='" + isDefault + '\'' +
                ", spuId=" + spuId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", spec='" + spec + '\'' +
                ", specMap=" + specMap +
                '}';
    }
}
