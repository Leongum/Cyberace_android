package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
public class Mission {

    @DatabaseField(id = true)
    private Integer missionId;

    @DatabaseField
    private Integer missionTypeId;

    @DatabaseField
    private String missionName;

    @DatabaseField
    private String missionContent;

    @DatabaseField
    private Double scores;

    @DatabaseField
    private Double experience;

    @DatabaseField
    private Integer missionFlag;

    @DatabaseField
    private Double levelLimited;

    @DatabaseField
    private Integer missionTime;

    @DatabaseField
    private Double missionDistance;

    @DatabaseField
    private Double cycleTime;

    @DatabaseField
    private String missionFrom;

    @DatabaseField
    private String missionTo;

    @DatabaseField
    private Integer missionPlacePackageId;

    //private String missionPlacePackages;

    private List<MissionPlacePackage> missionPlacePackages;

    @DatabaseField
    private Integer challengeId;

    //private String missionChallenges;

    private List<MissionChallenge> missionChallenges;

    @DatabaseField
    private Integer missionSteps;

    @DatabaseField
    private Double missionSpeed;

    @DatabaseField
    private Date lastUpdateTime;

    @DatabaseField
    private String subMissionList;

    @DatabaseField
    private Integer missionPackageId;

    @DatabaseField
    private Integer sequence;

    @DatabaseField
    private Integer planId;

    public Mission() {

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

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionContent() {
        return missionContent;
    }

    public void setMissionContent(String missionContent) {
        this.missionContent = missionContent;
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

    public Integer getMissionFlag() {
        return missionFlag;
    }

    public void setMissionFlag(Integer missionFlag) {
        this.missionFlag = missionFlag;
    }

    public Double getLevelLimited() {
        return levelLimited;
    }

    public void setLevelLimited(Double levelLimited) {
        this.levelLimited = levelLimited;
    }

    public Integer getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Integer missionTime) {
        this.missionTime = missionTime;
    }

    public Double getMissionDistance() {
        return missionDistance;
    }

    public void setMissionDistance(Double missionDistance) {
        this.missionDistance = missionDistance;
    }

    public Double getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Double cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getMissionFrom() {
        return missionFrom;
    }

    public void setMissionFrom(String missionFrom) {
        this.missionFrom = missionFrom;
    }

    public String getMissionTo() {
        return missionTo;
    }

    public void setMissionTo(String missionTo) {
        this.missionTo = missionTo;
    }

    public Integer getMissionPlacePackageId() {
        return missionPlacePackageId;
    }

    public void setMissionPlacePackageId(Integer missionPlacePackageId) {
        this.missionPlacePackageId = missionPlacePackageId;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public Integer getMissionSteps() {
        return missionSteps;
    }

    public void setMissionSteps(Integer missionSteps) {
        this.missionSteps = missionSteps;
    }

    public Double getMissionSpeed() {
        return missionSpeed;
    }

    public void setMissionSpeed(Double missionSpeed) {
        this.missionSpeed = missionSpeed;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSubMissionList() {
        return subMissionList;
    }

    public void setSubMissionList(String subMissionList) {
        this.subMissionList = subMissionList;
    }

    public Integer getMissionPackageId() {
        return missionPackageId;
    }

    public void setMissionPackageId(Integer missionPackageId) {
        this.missionPackageId = missionPackageId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<MissionPlacePackage> getMissionPlacePackages() {
        return missionPlacePackages;
    }

    public void setMissionPlacePackages(List<MissionPlacePackage> missionPlacePackages) {
        this.missionPlacePackages = missionPlacePackages;
    }

    public List<MissionChallenge> getMissionChallenges() {
        return missionChallenges;
    }

    public void setMissionChallenges(List<MissionChallenge> missionChallenges) {
        this.missionChallenges = missionChallenges;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
