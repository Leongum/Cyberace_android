package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-10
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {

    @DatabaseField(id = true)
    private Integer userId;

    @DatabaseField
    private Double level;

    @DatabaseField
    private Double scores;

    @DatabaseField
    private Double experience;

    @DatabaseField
    private Double maxPower;

    @DatabaseField
    private Double remainingPower;

    @DatabaseField
    private Double endurance;

    @DatabaseField
    private Double spirit;

    @DatabaseField
    private Double rapidly;

    @DatabaseField
    private Double recoverSpeed;

    @DatabaseField
    private Double weight;

    @DatabaseField
    private Double height;

    @DatabaseField
    private Integer age;

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
}
