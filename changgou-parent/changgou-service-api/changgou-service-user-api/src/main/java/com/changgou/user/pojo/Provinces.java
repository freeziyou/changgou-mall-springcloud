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
@Table(name = "tb_provinces")
public class Provinces implements Serializable {

    /**
     * 省份ID
     */
    @Id
    @Column(name = "provinceid")
    private String provinceid;

    /**
     * 省份名称
     */
    @Column(name = "province")
    private String province;


    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
