package com.G5432.Entity;

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

    @DatabaseField
    private Integer userId;

    @DatabaseField(id = true)
    private String runUuid;

    @DatabaseField
    private Integer missionId;

    @DatabaseField
    private Integer missionTypeId;

    @DatabaseField
    private String missionRoute;

    @DatabaseField
    private String waveForm;

    @DatabaseField
    private Date missionStartTime;

    @DatabaseField
    private Date missionEndTime;

    @DatabaseField
    private Date missionDate;

    @DatabaseField
    private Double spendCarlorie;

    @DatabaseField
    private Integer duration;

    @DatabaseField
    private Double avgSpeed;

    @DatabaseField
    private Integer steps;

    @DatabaseField
    private Double distance;

    @DatabaseField
    private String offerUsers;

    @DatabaseField
    private Integer missionGrade;

    @DatabaseField
    private Double scores;

    @DatabaseField
    private Double experience;

    @DatabaseField
    private Double extraExperience;

    @DatabaseField
    private String comment;

    @DatabaseField
    private Date commitTime;

    @DatabaseField
    private String uuid;

    @DatabaseField
    private Integer grade;

    @DatabaseField
    private Integer valid;

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
}
