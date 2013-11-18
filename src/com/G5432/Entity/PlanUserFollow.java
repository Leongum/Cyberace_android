package com.G5432.Entity;

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

    @DatabaseField
    private Integer userId;
    @DatabaseField
    private Integer followUserId;
    @DatabaseField
    private Integer status;
    @DatabaseField
    private Date addTime;
    @DatabaseField
    private Integer updated;

    public PlanUserFollow(){

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
