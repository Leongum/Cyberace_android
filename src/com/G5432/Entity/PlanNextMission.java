package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class PlanNextMission {
    @Expose
    @DatabaseField
    private Integer planId;
    @Expose
    @DatabaseField
    private Integer nextMissionId;
    @Expose
    @DatabaseField
    private Date startTime;
    @Expose
    @DatabaseField
    private Date endTime;
    @Expose
    @DatabaseField(id = true)
    private String planRunUuid;

    private Plan planInfo;
    private Mission nextMission;
    private PlanRunHistory planRunHistory;

    public PlanNextMission(){

    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getNextMissionId() {
        return nextMissionId;
    }

    public void setNextMissionId(Integer nextMissionId) {
        this.nextMissionId = nextMissionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlanRunUuid() {
        return planRunUuid;
    }

    public void setPlanRunUuid(String planRunUuid) {
        this.planRunUuid = planRunUuid;
    }

    public Plan getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(Plan planInfo) {
        this.planInfo = planInfo;
    }

    public Mission getNextMission() {
        return nextMission;
    }

    public void setNextMission(Mission nextMission) {
        this.nextMission = nextMission;
    }

    public PlanRunHistory getPlanRunHistory() {
        return planRunHistory;
    }

    public void setPlanRunHistory(PlanRunHistory planRunHistory) {
        this.planRunHistory = planRunHistory;
    }
}
