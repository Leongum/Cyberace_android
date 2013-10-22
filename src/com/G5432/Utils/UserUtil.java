package com.G5432.Utils;

import android.content.SharedPreferences;
import android.util.Log;
import com.G5432.Entity.UserBase;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class UserUtil {

    public static SharedPreferences sharedPreferences;

    public static Boolean messageSynced = true;
    public static Boolean missionSynced = true;
    public static Boolean systemSynced = true;
    public static Boolean historySynced = true;
    public static Boolean userSynced = true;

    public static Integer getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }

    public static String getUserUuid() {
        return sharedPreferences.getString("uuid", "");
    }

    public static Date getSystemTime() {
        String dateString = sharedPreferences.getString("systemTime", "");
        return CommonUtil.parseDate(dateString);
    }

    public static void logout() {
        String missionUpdateTime = getLastUpdateTime("MissionUpdateTime");
        String systemMessageUpdateTime = getLastUpdateTime("SystemMessageUpdateTime");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("userId", -1);
        editor.putString("MissionUpdateTime", missionUpdateTime);
        editor.putString("SystemMessageUpdateTime", systemMessageUpdateTime);
        editor.commit();
    }

    public static String getLastUpdateTime(String key) {
        return sharedPreferences.getString(key, "2000-01-01 00:00:00");
    }

    public static void saveLastUpdateTime(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, sharedPreferences.getString("systemTime", ""));
        editor.commit();
    }

    public static void saveSystemTime(Date systemTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("systemTime", CommonUtil.parseDateToString(systemTime));
        editor.commit();
    }

    public static void saveUserInfoToList(UserBase user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", user.getUserId());
        editor.putString("nickName", user.getNickName());
        editor.putString("uuid", user.getUuid());
        editor.putString("sex", user.getSex());
        editor.putFloat("weight", user.getUserInfo().getWeight().floatValue());
        editor.putFloat("height", user.getUserInfo().getHeight().floatValue());
        editor.commit();
    }

    public static void saveBodySetting(UserBase user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sex", user.getSex());
        editor.putFloat("weight", user.getWeight().floatValue());
        editor.putFloat("height", user.getHeight().floatValue());
        editor.commit();
    }

    public static String getUserSex() {
        return UserUtil.sharedPreferences.getString("sex", "男");
    }

    public static float getUserHeight() {
        return UserUtil.sharedPreferences.getFloat("height", 175);
    }

    public static float getUserWeight() {
        return UserUtil.sharedPreferences.getFloat("weight", 65);
    }
}
