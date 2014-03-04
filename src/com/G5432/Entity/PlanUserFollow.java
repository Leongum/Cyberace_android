package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class PlanUserFollow {

    @DatabaseField(id = true, useGetSet = true)
    private String genId;
    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField
    private Integer followUserId;
    @Expose
    @DatabaseField
    private Integer status;
    @Expose
    @DatabaseField
    private Date addTime;
    @Expose
    @DatabaseField
    private Integer updated;

    public PlanUserFollow(){

    }

    public String getGenId() {
        return userId + "-" + followUserId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}
