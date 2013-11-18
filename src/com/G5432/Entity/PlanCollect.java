package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class PlanCollect {

    @DatabaseField
    private Integer userId;
    @DatabaseField
    private Integer planId;
    @DatabaseField
    private Date collectTime;
    @DatabaseField
    private Integer collectStatus; //0 collected 1 deleted.
    @DatabaseField
    private Integer updated;

    public PlanCollect(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}
