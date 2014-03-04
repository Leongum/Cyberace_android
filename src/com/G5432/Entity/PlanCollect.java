package com.G5432.Entity;

import com.google.gson.annotations.Expose;
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

    @DatabaseField(id = true, useGetSet = true)
    private String genId;
    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField
    private Integer planId;
    @Expose
    @DatabaseField
    private Date collectTime;
    @Expose
    @DatabaseField
    private Integer collectStatus; //0 collected 1 deleted.
    @Expose
    @DatabaseField
    private Integer updated;

    public PlanCollect() {

    }

    public String getGenId() {
        return userId + "-" + planId;
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
