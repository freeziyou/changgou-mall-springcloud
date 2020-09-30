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
@Table(name = "tb_cities")
public class Cities implements Serializable {

    /**
     * 城市ID
     */
    @Id
    @Column(name = "cityid")
    private String cityid;

    /**
     * 城市名称
     */
    @Column(name = "city")
    private String city;

    /**
     * 省份ID
     */
    @Column(name = "provinceid")
    private String provinceid;


    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

}
