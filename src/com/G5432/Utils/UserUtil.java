package com.G5432.Utils;

import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class UserUtil {

    public static SharedPreferences sharedPreferences;

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

    public static void logout(){
        String missionUpdateTime = getLastUpdateTime("MissionUpdateTime");
        String systemMessageUpdateTime = getLastUpdateTime("SystemMessageUpdateTime");
        sharedPreferences.edit().clear();
        sharedPreferences.edit().apply();
        sharedPreferences.edit().putInt("userId",-1);
        sharedPreferences.edit().putString("MissionUpdateTime",missionUpdateTime);
        sharedPreferences.edit().putString("SystemMessageUpdateTime",systemMessageUpdateTime);
        sharedPreferences.edit().commit();
    }

    public static String getLastUpdateTime(String key){
        return sharedPreferences.getString(key,"2000-01-01 00:00:00");
    }

    public static void saveLastUpdateTime(String key){
        sharedPreferences.edit().putString(key,sharedPreferences.getString("systemTime", ""));
    }
}
