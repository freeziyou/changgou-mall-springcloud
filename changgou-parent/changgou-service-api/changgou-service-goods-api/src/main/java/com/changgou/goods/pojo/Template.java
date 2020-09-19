package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_template")
public class Template implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 规格数量
     */
    @Column(name = "spec_num")
    private Integer specNum;

    /**
     * 参数数量
     */
    @Column(name = "para_num")
    private Integer paraNum;


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

    public Integer getSpecNum() {
        return specNum;
    }

    public void setSpecNum(Integer specNum) {
        this.specNum = specNum;
    }

    public Integer getParaNum() {
        return paraNum;
    }

    public void setParaNum(Integer paraNum) {
        this.paraNum = paraNum;
    }

}
