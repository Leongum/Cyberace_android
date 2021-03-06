package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class Plan {
    @Expose
    @DatabaseField(id = true)
    private Integer planId;
    @Expose
    @DatabaseField
    private String planName;
    @Expose
    @DatabaseField
    private String planDescription;
    @Expose
    @DatabaseField
    private Integer planType; // 0 - easy 1 - complex
    @Expose
    @DatabaseField
    private String missionIds;
    @Expose
    @DatabaseField
    private Integer totalMissions;
    @Expose
    @DatabaseField
    private Integer planShareUserId;
    @Expose
    @DatabaseField
    private String planShareUserName;
    @Expose
    @DatabaseField
    private Integer sharedPlan; // 0 system 1 shared
    @Expose
    @DatabaseField
    private Integer cycleTime;
    @Expose
    @DatabaseField
    private Integer duration;
    @Expose
    @DatabaseField
    private Integer durationType; // 1 week 2 day
    @Expose
    @DatabaseField
    private Integer planStatus; //0 enabled 1 deleted
    @Expose
    @DatabaseField
    private Integer durationLast;
    @Expose
    @DatabaseField
    private Integer planFlag;
    @Expose
    @DatabaseField
    private Date lastUpdateTime;

    @Expose
    private List<Mission> missionList;

    public Plan() {

    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getMissionIds() {
        return missionIds;
    }

    public void setMissionIds(String missionIds) {
        this.missionIds = missionIds;
    }

    public Integer getTotalMissions() {
        return totalMissions;
    }

    public void setTotalMissions(Integer totalMissions) {
        this.totalMissions = totalMissions;
    }

    public Integer getPlanShareUserId() {
        return planShareUserId;
    }

    public void setPlanShareUserId(Integer planShareUserId) {
        this.planShareUserId = planShareUserId;
    }

    public String getPlanShareUserName() {
        return planShareUserName;
    }

    public void setPlanShareUserName(String planShareUserName) {
        this.planShareUserName = planShareUserName;
    }

    public Integer getSharedPlan() {
        return sharedPlan;
    }

    public void setSharedPlan(Integer sharedPlan) {
        this.sharedPlan = sharedPlan;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getDurationLast() {
        return durationLast;
    }

    public void setDurationLast(Integer durationLast) {
        this.durationLast = durationLast;
    }

    public Integer getPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(Integer planFlag) {
        this.planFlag = planFlag;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<Mission> getMissionList() {
        return missionList;
    }

    public void setMissionList(List<Mission> missionList) {
        this.missionList = missionList;
    }
}
