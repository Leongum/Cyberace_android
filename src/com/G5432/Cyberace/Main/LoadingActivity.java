package com.G5432.Cyberace.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.sharesdk.framework.ShareSDK;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.HttpClient.BackendSyncRequest;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class LoadingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();


    private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer.cancel();
                Intent intent = new Intent();
                intent.setClass(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        UserUtil.sharedPreferences = getSharedPreferences("UserBaseInfo", MODE_APPEND);
        doSyncData();
        startTimerChecker();
    }

    private void startTimerChecker() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            int time = 0;
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "begin time task check");

                // 定义一个消息传过去
                Message msg = new Message();
                msg.what = (UserUtil.systemSynced && UserUtil.missionSynced && UserUtil.messageSynced && UserUtil.historySynced && UserUtil.userSynced) ? 1 : 0;
                if(time == 5){
                    msg.what = 1;
                }
                time ++;
                handler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 3000, 1000);
    }

    private void doSyncData() {
        HttpClientHelper httpClientHelper = new HttpClientHelper();
        UserUtil.systemSynced = false;
        httpClientHelper.get(CommonUtil.getUrl(Constant.VERSION_URL), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    BackendSyncRequest backendSyncRequest = new BackendSyncRequest(getHelper());
                    VersionControl versionControl = gson.fromJson(response, VersionControl.class);
                    UserUtil.saveSystemTime(versionControl.getSystemTime());
                    Date missionLastUpdateTime = versionControl.getMissionLastUpdateTime();
                    Date messageLastUpdateTIme = versionControl.getMessageLastUpdateTime();
                    Date syncedMissionUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("MissionUpdateTime"));
                    Date syncedMessageUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("SystemMessageUpdateTime"));
                    if (syncedMissionUpdateTime.before(missionLastUpdateTime)) {
                        UserUtil.missionSynced = false;
                        backendSyncRequest.syncMissions();
                    }
                    if (syncedMessageUpdateTime.before(messageLastUpdateTIme)) {
                        UserUtil.messageSynced = false;
                        backendSyncRequest.syncSystemMessages();
                    }
                } else {
                    Log.e(this.getClass().getName(), response);
                }
                UserUtil.systemSynced = true;
            }

            @Override
            public void onFailure(Throwable error, String content) {
                UserUtil.systemSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });

        if (UserUtil.getUserId() > 0) {
            UserUtil.userSynced = false;
            UserUtil.historySynced = false;
            BackendSyncRequest backendSyncRequest = new BackendSyncRequest(getHelper());
            backendSyncRequest.syncRunningHistories();
            backendSyncRequest.uploadRunningHistories();
            backendSyncRequest.syncUserInfo();
        }
    }
}
