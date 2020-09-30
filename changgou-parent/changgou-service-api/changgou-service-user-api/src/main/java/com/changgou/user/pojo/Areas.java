package com.changgou.user.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "tb_areas")
public class Areas implements Serializable {

    /**
     * 区域ID
     */
    @Id
    @Column(name = "areaid")
    private String areaid;

    /**
     * 区域名称
     */
    @Column(name = "area")
    private String area;

    /**
     * 城市ID
     */
    @Column(name = "cityid")
    private String cityid;


    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

}
