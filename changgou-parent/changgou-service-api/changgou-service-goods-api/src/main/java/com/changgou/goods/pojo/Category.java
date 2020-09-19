package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_category")
public class Category implements Serializable {

    /**
     * 分类ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 商品数量
     */
    @Column(name = "goods_num")
    private Integer goodsNum;

    /**
     * 是否显示
     */
    @Column(name = "is_show")
    private String isShow;

    /**
     * 是否导航
     */
    @Column(name = "is_menu")
    private String isMenu;

    /**
     * 排序
     */
    @Column(name = "seq")
    private Integer seq;

    /**
     * 上级ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 模板ID
     */
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
