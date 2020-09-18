package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/16/2020 12:51
 * @description TODO
 */

@ApiModel(description = "Category", value = "Category")
@Table(name = "tb_category")
public class Category implements Serializable {

    /**
     * 分类 ID
     */
    @ApiModelProperty(value = "分类ID", required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称", required = false)
    @Column(name = "name")
    private String name;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量", required = false)
    @Column(name = "goods_num")
    private Integer goodsNum;

    /**
     * 是否显示
     */
    @ApiModelProperty(value = "是否显示", required = false)
    @Column(name = "is_show")
    private String isShow;

    /**
     * 是否导航
     */
    @ApiModelProperty(value = "是否导航", required = false)
    @Column(name = "is_menu")
    private String isMenu;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    @Column(name = "seq")
    private Integer seq;

    /**
     * 上级 ID
     */
    @ApiModelProperty(value = "上级ID", required = false)
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 模板 ID
     */
    @ApiModelProperty(value = "模板ID", required = false)
    @Column(name = "template_id")
    private Integer templateId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
