package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-8
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class UserBase {

    @DatabaseField(id = true)
    private Integer userId;

    @DatabaseField
    private String userEmail;

    @DatabaseField
    private String password;

    @DatabaseField
    private String nickName;

    @DatabaseField
    private String sex;

    @DatabaseField
    private String uuid;

    private UserInfo userInfo;

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
}
