package com.changgou.user.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Table(name = "undo_log")
public class UndoLog implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "branch_id")
    private Long branchId;

    /**
     *
     */
    @Column(name = "xid")
    private String xid;

    /**
     *
     */
    @Column(name = "rollback_info")
    private String rollbackInfo;

    /**
     *
     */
    @Column(name = "log_status")
    private Integer logStatus;

    /**
     *
     */
    @Column(name = "log_created")
    private Date logCreated;

    /**
     *
     */
    @Column(name = "log_modified")
    private Date logModified;

    /**
     *
     */
    @Column(name = "ext")
    private String ext;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getRollbackInfo() {
        return rollbackInfo;
    }

    public void setRollbackInfo(String rollbackInfo) {
        this.rollbackInfo = rollbackInfo;
    }

    public Integer getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Integer logStatus) {
        this.logStatus = logStatus;
    }

    public Date getLogCreated() {
        return logCreated;
    }

    public void setLogCreated(Date logCreated) {
        this.logCreated = logCreated;
    }

    public Date getLogModified() {
        return logModified;
    }

    public void setLogModified(Date logModified) {
        this.logModified = logModified;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

}
