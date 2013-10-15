package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
public class MissionChallenge {

    @DatabaseField
    private Integer challengeId;

    @DatabaseField
    private String grade;

    @DatabaseField
    private Integer time;

    @DatabaseField
    private Double distance;

    @DatabaseField
    private Integer sequence;

    @DatabaseField
    private String note;

    @DatabaseField
    private String sex;

    @DatabaseField
    private String rule;

    public MissionChallenge(){

    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
