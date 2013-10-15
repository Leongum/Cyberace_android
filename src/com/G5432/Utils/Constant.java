package com.G5432.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-9
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class Constant {

    public static final String SERVICE_HOST = "http://121.199.56.231:8080/usavich/service/api";

    public static final String LOGIN_URL = SERVICE_HOST + "/account/{0}/{1}";
    public static final String REGISTER_URL = SERVICE_HOST + "/account";
    public static final String USER_ADDITIONAL_UPDATE = SERVICE_HOST + "/account/additional/{0}";
    public static final String USER_INFO = SERVICE_HOST + "/account/{0}?checkUuid=true";
    public static final String POST_RUNNING_HISTORY_URL = SERVICE_HOST + "/running/history/{0}";
    public static final String RUNNING_HISTORY_URL = SERVICE_HOST + "/running/history/{0}?lastUpdateTime={1}";
    public static final String MISSION_URL = SERVICE_HOST + "/missions/mission?lastUpdateTime={0}";
    public static final String VERSION_URL = SERVICE_HOST + "/system/version/android";
    public static final String SYSTEM_MESSAGE_URL = SERVICE_HOST + "/system/message/{0}";
    public static final String FEEDBACK_URL = SERVICE_HOST + "/system/feedback";
    public static final String PM25_URL = SERVICE_HOST + "/weather/pm25?cityName={0}&provinceName={1}";

    public static final String WEATHER_URL = SERVICE_HOST + "http://www.weather.com.cn/data/sk/{0}.html";

}
