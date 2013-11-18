package com.G5432.Entity;

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

    @DatabaseField
    private Integer planId;
    @DatabaseField
    private Integer nextMissionId;
    @DatabaseField
    private Date startTime;
    @DatabaseField
    private Date endTime;
    @DatabaseField
    private String planRunUuid;

    private Plan planInfo;
    private Mission mission;
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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public PlanRunHistory getPlanRunHistory() {
        return planRunHistory;
    }

    public void setPlanRunHistory(PlanRunHistory planRunHistory) {
        this.planRunHistory = planRunHistory;
    }
}
