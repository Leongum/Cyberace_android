package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-10
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    @Expose
    @DatabaseField(id = true)
    private Integer userId;
    @Expose
    @DatabaseField
    private Double level;
    @Expose
    @DatabaseField
    private Double scores;
    @Expose
    @DatabaseField
    private Double experience;
    @Expose
    @DatabaseField
    private Double maxPower;
    @Expose
    @DatabaseField
    private Double remainingPower;
    @Expose
    @DatabaseField
    private Double endurance;
    @Expose
    @DatabaseField
    private Double spirit;
    @Expose
    @DatabaseField
    private Double rapidly;
    @Expose
    @DatabaseField
    private Double recoverSpeed;
    @Expose
    @DatabaseField
    private Double weight;
    @Expose
    @DatabaseField
    private Double height;
    @Expose
    @DatabaseField
    private Integer age;
    @Expose
    @DatabaseField
    private double totalDistance;
    @Expose
    @DatabaseField
    private double avgSpeed;
    @Expose
    @DatabaseField
    private double spendCarlorie;
    @Expose
    @DatabaseField
    private Integer totalRunTimes;

    public UserInfo() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double level) {
        this.level = level;
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

    public Double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Double maxPower) {
        this.maxPower = maxPower;
    }

    public Double getRemainingPower() {
        return remainingPower;
    }

    public void setRemainingPower(Double remainingPower) {
        this.remainingPower = remainingPower;
    }

    public Double getEndurance() {
        return endurance;
    }

    public void setEndurance(Double endurance) {
        this.endurance = endurance;
    }

    public Double getSpirit() {
        return spirit;
    }

    public void setSpirit(Double spirit) {
        this.spirit = spirit;
    }

    public Double getRapidly() {
        return rapidly;
    }

    public void setRapidly(Double rapidly) {
        this.rapidly = rapidly;
    }

    public Double getRecoverSpeed() {
        return recoverSpeed;
    }

    public void setRecoverSpeed(Double recoverSpeed) {
        this.recoverSpeed = recoverSpeed;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getSpendCarlorie() {
        return spendCarlorie;
    }

    public void setSpendCarlorie(double spendCarlorie) {
        this.spendCarlorie = spendCarlorie;
    }

    public Integer getTotalRunTimes() {
        return totalRunTimes;
    }

    public void setTotalRunTimes(Integer totalRunTimes) {
        this.totalRunTimes = totalRunTimes;
    }
}
