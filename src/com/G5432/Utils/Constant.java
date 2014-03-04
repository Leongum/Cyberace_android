package com.G5432.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-9
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class Constant {

    public static final String SERVICE_HOST = "http://121.199.56.231:8080/usavichV2/service/api";

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

    public static final String WEATHER_URL = "http://www.weather.com.cn/data/sk/{0}.html";

    //define version 2.0 plan new api
    public static final String USER_FOLLOWERS_DETAIL_URL = SERVICE_HOST + "/account/follower/{0}?pageNo={1}";
    public static final String PLAN_INFO_URL = SERVICE_HOST + "/plans/plan/{0}?lastUpdateTime={1}";
    public static final String HOT_PLAN_URL = SERVICE_HOST + "/plans/list?pageNo={0}";
    public static final String POST_PLAN_URL = SERVICE_HOST + "/plans/plan/post/{0}";
    public static final String USER_LAST_UPDATE_PLAN_URL = SERVICE_HOST + "/plans/history/lastupdate/{0}";
    public static final String USER_COLLECT_PLAN_URL = SERVICE_HOST + "/plans/collect/{0}?lastUpdateTime={1}";
    public static final String PUT_USER_COLLECT_PLAN_URL = SERVICE_HOST + "/plans/collect/put/{0}";
    public static final String USER_PLAN_HISTORY_URL = SERVICE_HOST + "/plans/history/{0}?lastUpdateTime={1}";
    public static final String PUT_USER_PLAN_HISTORY_URL = SERVICE_HOST + "/plans/history/put/{0}";
    public static final String PLAN_HISTORY_BY_PLANID_URL = SERVICE_HOST + "/plans/history/running/plan/{0}?pageNo={1}";
    public static final String PLAN_HISTORY_BY_USERID_URL = SERVICE_HOST + "/plans/history/running/user/{0}?pageNo={1}";
    public static final String USER_FOLLOWER_LIST_URL = SERVICE_HOST + "/plans/follow/{0}?lastUpdateTime={1}";
    public static final String PUT_USER_FOLLOWER_LIST_URL = SERVICE_HOST + "/plans/follow/put/{0}";

    public static final int CONNECTION_ERROR = 1;
    public static final int LOGIN_ERROR = 2;
    public static final int REGISTER_SUCCESS = 3;
    public static final int REGISTER_FAIL = 4;
    public static final int LOGIN_INPUT_CHECK = 5;
    public static final int REGISTER_INPUT_CHECK = 6;
    public static final int CONNECTION_ERROR_CONTENT = 7;
    public static final int SYNC_DATA_SUCCESS = 8;
    public static final int SYNC_DATA_FAIL = 9;
    public static final int SYNC_MODE_ALL = 10;
    public static final int SYNC_MODE_WIFI = 11;
    public static final int SHARE_TO_PLATFORM_LIST = 12;
    public static final int SNS_BIND_ERROR = 13;
    public static final int SELECT_SHARE_PLATFORM_ERROR = 14;
    public static final int SHARE_DEFAULT_CONTENT = 15;
    public static final int SHARE_DEFAULT_TITLE = 16;
    public static final int SHARE_DEFAULT_URL = 17;
    public static final int SHARE_DEFAULT_DESCRIPTION = 18;
    public static final int SHARE_SUBMITTED = 19;
    public static final int NO_HISTORY = 20;
    public static final int GPS_SETTING_ERROR = 21;
    public static final int STATISTICS_DISTANCE_MESSAGE = 22;
    public static final int STATISTICS_SPEED_MESSAGE = 23;
    public static final int STATISTICS_CALORIE_MESSAGE = 24;

    public static final String CANCEL_BUTTON = "知道呢！";
    public static final String SEARCHING_LOCATION = "定位中...";
    public static final String START_RUNNING_BUTTON = "走你";
    public static final String CANCEL_RUNNING_BUTTON = "取消";
    public static final String FINISH_RUNNING_BUTTON = "完成";
    public static final String PAUSE_RUNNING_BUTTON = "歇会儿";
    public static final String CONTINUE_RUNNING_BUTTON = "再走你";
    public static final String ALERT_VIEW_TITLE = "提示";
    public static final String LOGOUT_ALERT_TITLE = "注销";
    public static final String LOGOUT_ALERT_CONTENT = "确定要注销吗？";
    public static final String CANCEL_BUTTON_CANCEL = "取消";
    public static final String OK_BUTTON_OK = "确定";


    //setting
    public static final String SYNC_MODE_WIFI_ONLY = "Only_Wifi";
    public static final String SYNC_MODE_ALL_MODE = "All_Mode";
    public static final String SPEED_FORMAT_KM_PER_HOUR = "km/h";
    public static final String SPEED_FORMAT_MIN_PER_KM = "min/km";

}
