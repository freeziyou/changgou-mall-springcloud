package com.changgou.goods.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_brand")
public class Brand implements Serializable {

    /**
     * 品牌id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 品牌名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 品牌图片地址
     */
    @Column(name = "image")
    private String image;

    /**
     * 品牌的首字母
     */
    @Column(name = "letter")
    private String letter;

    /**
     * 排序
     */
    @Column(name = "seq")
    private Integer seq;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}
