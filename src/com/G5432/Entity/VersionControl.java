package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
public class VersionControl {
    @Expose
    @DatabaseField(id = true)
    private String platform;
    @Expose
    @DatabaseField
    private Integer version;
    @Expose
    @DatabaseField
    private Integer subVersion;
    @Expose
    @DatabaseField
    private String description;
    @Expose
    @DatabaseField
    private Date systemTime;
    @Expose
    @DatabaseField
    private Date missionLastUpdateTime;
    @Expose
    @DatabaseField
    private Date messageLastUpdateTime;

    public VersionControl() {

    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubVersion() {
        return subVersion;
    }

    public void setSubVersion(Integer subVersion) {
        this.subVersion = subVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    public Date getMissionLastUpdateTime() {
        return missionLastUpdateTime;
    }

    public void setMissionLastUpdateTime(Date missionLastUpdateTime) {
        this.missionLastUpdateTime = missionLastUpdateTime;
    }

    public Date getMessageLastUpdateTime() {
        return messageLastUpdateTime;
    }

    public void setMessageLastUpdateTime(Date messageLastUpdateTime) {
        this.messageLastUpdateTime = messageLastUpdateTime;
    }
}
