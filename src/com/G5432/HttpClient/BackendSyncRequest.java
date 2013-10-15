package com.G5432.HttpClient;

import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Enums.MissionType;
import com.G5432.Service.*;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class BackendSyncRequest {

    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private DatabaseHelper databaseHelper = null;

    private Gson gson = new Gson();

    public BackendSyncRequest(DatabaseHelper helper) {
        this.databaseHelper = helper;
    }

    public void syncSystemMessages() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("SystemMessageUpdateTime");
        String url = MessageFormat.format(Constant.SYSTEM_MESSAGE_URL, lastUpdateTime);
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (statusCode == 200 || statusCode == 204) {
                    SystemService systemService = new SystemService(databaseHelper);
                    List<SystemMessage> systemMessageList = gson.fromJson(response, new TypeToken<List<SystemMessage>>() {
                    }.getType());
                    systemService.saveSystemMessageToDB(systemMessageList);
                    UserUtil.saveLastUpdateTime("SystemMessageUpdateTime");
                } else {
                    Log.e(this.getClass().getName(), response);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void syncMissions() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("MissionUpdateTime");
        String url = MessageFormat.format(Constant.MISSION_URL, lastUpdateTime);
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (statusCode == 200 || statusCode == 204) {
                    MissionService missionService = new MissionService(databaseHelper);
                    List<Mission> missionList = gson.fromJson(response, new TypeToken<List<Mission>>() {
                    }.getType());
                    for (Mission mission : missionList) {
                        if (mission.getMissionTypeId() == MissionType.Challenge.ordinal() && mission.getChallengeId() != null) {
                            List<MissionChallenge> missionChallengeList = gson.fromJson(mission.getMissionChallenges(), new TypeToken<List<MissionChallenge>>() {
                            }.getType());
                            mission.setMissionChallengesList(missionChallengeList);
                        }
                    }
                    missionService.saveMissionToDB(missionList);
                    UserUtil.saveLastUpdateTime("MissionUpdateTime");
                } else {
                    Log.e(this.getClass().getName(), response);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void syncRunningHistories() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("RunningHistoryUpdateTime");
        String url = MessageFormat.format(Constant.RUNNING_HISTORY_URL, UserUtil.getUserId(), lastUpdateTime);
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (statusCode == 200 || statusCode == 204) {
                    RunningHistoryService runningHistoryService = new RunningHistoryService(databaseHelper);
                    List<RunningHistory> runningHistoryList = gson.fromJson(response, new TypeToken<List<RunningHistory>>() {
                    }.getType());
                    ;
                    runningHistoryService.saveRunListToDB(runningHistoryList);
                    UserUtil.saveLastUpdateTime("RunningHistoryUpdateTime");
                } else {
                    Log.e(this.getClass().getName(), response);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void uploadRunningHistories() {
        RunningHistoryService runningHistoryService = new RunningHistoryService(databaseHelper);
        List<RunningHistory> runningHistoryList = runningHistoryService.fetchUnsyncedRunHistory(UserUtil.getUserId());
        if (runningHistoryList != null && runningHistoryList.size() > 0) {
            String url = MessageFormat.format(Constant.POST_RUNNING_HISTORY_URL, UserUtil.getUserId());
            httpClientHelper.post(url, null, gson.toJson(runningHistoryList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    if (statusCode == 200 || statusCode == 204) {
                        RunningHistoryService runningHistoryService = new RunningHistoryService(databaseHelper);
                        runningHistoryService.updateUnsyncedRunHistories(UserUtil.getUserId());
                    } else {
                        Log.e(this.getClass().getName(), response);
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                }
            });
        }
    }
}
