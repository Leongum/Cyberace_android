package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public class PlanRunHistory {

    @DatabaseField
    private Integer planId;
    @DatabaseField
    private String planRunUuid;
    @DatabaseField
    private Integer nextMissionId;
    @DatabaseField
    private Integer userId;
    @DatabaseField
    private Date startTime;
    @DatabaseField
    private Date endTime;
    @DatabaseField
    private Integer rate;
    @DatabaseField
    private String rateComment;
    @DatabaseField
    private Integer remainingMissions;
    @DatabaseField
    private Integer totalMissions;
    @DatabaseField
    private Integer historyStatus; //0 execute 1 finished  -1 cancled
    @DatabaseField
    private Date  lastUpdateTime;
    @DatabaseField
    private Integer operate;

    private List<RunningHistory> runningHistoryList;

    public PlanRunHistory(){

    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanRunUuid() {
        return planRunUuid;
    }

    public void setPlanRunUuid(String planRunUuid) {
        this.planRunUuid = planRunUuid;
    }

    public Integer getNextMissionId() {
        return nextMissionId;
    }

    public void setNextMissionId(Integer nextMissionId) {
        this.nextMissionId = nextMissionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getRateComment() {
        return rateComment;
    }

    public void setRateComment(String rateComment) {
        this.rateComment = rateComment;
    }

    public Integer getRemainingMissions() {
        return remainingMissions;
    }

    public void setRemainingMissions(Integer remainingMissions) {
        this.remainingMissions = remainingMissions;
    }

    public Integer getTotalMissions() {
        return totalMissions;
    }

    public void setTotalMissions(Integer totalMissions) {
        this.totalMissions = totalMissions;
    }

    public Integer getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Integer historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public List<RunningHistory> getRunningHistoryList() {
        return runningHistoryList;
    }

    public void setRunningHistoryList(List<RunningHistory> runningHistoryList) {
        this.runningHistoryList = runningHistoryList;
    }
}
