package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-8
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class UserBase {
    @Expose
    @DatabaseField(id = true)
    private Integer userId;
    @Expose
    @DatabaseField
    private String userEmail;
    @Expose
    @DatabaseField
    private String password;
    @Expose
    @DatabaseField
    private String nickName;
    @Expose
    @DatabaseField
    private String sex;
    @Expose
    @DatabaseField
    private String uuid;

    private UserInfo userInfo;

    //additional for update self info
    private Double height;

    //additional for update self info
    private Double weight;

    public UserBase() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
