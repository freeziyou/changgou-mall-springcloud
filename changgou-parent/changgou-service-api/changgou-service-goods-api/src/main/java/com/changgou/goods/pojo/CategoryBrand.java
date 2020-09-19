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
@Table(name = "tb_category_brand")
public class CategoryBrand implements Serializable {

    /**
     * 分类ID
     */
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 品牌ID
     */
    @Column(name = "brand_id")
    private Integer brandId;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

}
