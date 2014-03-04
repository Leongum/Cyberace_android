package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午9:00
 * To change this template use File | Settings | File Templates.
 */
public class RunningHistory {

    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField(id = true)
    private String runUuid;
    @Expose
    @DatabaseField
    private Integer missionId;
    @Expose
    @DatabaseField
    private Integer missionTypeId;
    @Expose
    @DatabaseField
    private String missionRoute;
    @Expose
    @DatabaseField
    private String waveForm;
    @Expose
    @DatabaseField
    private Date missionStartTime;
    @Expose
    @DatabaseField
    private Date missionEndTime;
    @Expose
    @DatabaseField
    private Date missionDate;
    @Expose
    @DatabaseField
    private Double spendCarlorie;
    @Expose
    @DatabaseField
    private Integer duration;
    @Expose
    @DatabaseField
    private Double avgSpeed;
    @Expose
    @DatabaseField
    private Integer steps;
    @Expose
    @DatabaseField
    private Double distance;
    @Expose
    @DatabaseField
    private String offerUsers;
    @Expose
    @DatabaseField
    private Integer missionGrade;
    @Expose
    @DatabaseField
    private Double scores;
    @Expose
    @DatabaseField
    private Double experience;
    @Expose
    @DatabaseField
    private Double extraExperience;
    @Expose
    @DatabaseField
    private String comment;
    @Expose
    @DatabaseField
    private Date commitTime;
    @Expose
    @DatabaseField
    private String uuid;
    @Expose
    @DatabaseField
    private Integer grade;
    @Expose
    @DatabaseField
    private Integer valid;
    @Expose
    @DatabaseField
    private String planRunUuid;
    @Expose
    @DatabaseField
    private Integer sequence;
    @Expose
    @DatabaseField
    private String speedList;

    public RunningHistory() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRunUuid() {
        return runUuid;
    }

    public void setRunUuid(String runUuid) {
        this.runUuid = runUuid;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getMissionTypeId() {
        return missionTypeId;
    }

    public void setMissionTypeId(Integer missionTypeId) {
        this.missionTypeId = missionTypeId;
    }

    public String getMissionRoute() {
        return missionRoute;
    }

    public void setMissionRoute(String missionRoute) {
        this.missionRoute = missionRoute;
    }

    public String getWaveForm() {
        return waveForm;
    }

    public void setWaveForm(String waveForm) {
        this.waveForm = waveForm;
    }

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public Date getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(Date missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public Date getMissionDate() {
        return missionDate;
    }

    public void setMissionDate(Date missionDate) {
        this.missionDate = missionDate;
    }

    public Double getSpendCarlorie() {
        return spendCarlorie;
    }

    public void setSpendCarlorie(Double spendCarlorie) {
        this.spendCarlorie = spendCarlorie;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getOfferUsers() {
        return offerUsers;
    }

    public void setOfferUsers(String offerUsers) {
        this.offerUsers = offerUsers;
    }

    public Integer getMissionGrade() {
        return missionGrade;
    }

    public void setMissionGrade(Integer missionGrade) {
        this.missionGrade = missionGrade;
    }

    public Double getScores() {
        return scores;
    }

    public void setScores(Double scores) {
        this.scores = scores;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }

    public Double getExtraExperience() {
        return extraExperience;
    }

    public void setExtraExperience(Double extraExperience) {
        this.extraExperience = extraExperience;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getPlanRunUuid() {
        return planRunUuid;
    }

    public void setPlanRunUuid(String planRunUuid) {
        this.planRunUuid = planRunUuid;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getSpeedList() {
        return speedList;
    }

    public void setSpeedList(String speedList) {
        this.speedList = speedList;
    }
}
