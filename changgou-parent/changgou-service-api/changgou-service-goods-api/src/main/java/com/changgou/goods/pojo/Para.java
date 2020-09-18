package com.changgou.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@ApiModel(description = "Para", value = "Para")
@Table(name = "tb_para")
public class Para implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "id", required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = false)
    @Column(name = "name")
    private String name;

    /**
     * 选项
     */
    @ApiModelProperty(value = "选项", required = false)
    @Column(name = "options")
    private String options;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    @Column(name = "seq")
    private Integer seq;

    /**
     * 模板ID
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
