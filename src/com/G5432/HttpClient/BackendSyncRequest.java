package com.G5432.HttpClient;

import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.*;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public BackendSyncRequest(DatabaseHelper helper) {
        this.databaseHelper = helper;
    }

    public void syncSystemMessages() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("SystemMessageUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.SYSTEM_MESSAGE_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    SystemService systemService = new SystemService(databaseHelper);
                    List<SystemMessage> systemMessageList = gson.fromJson(response, new TypeToken<List<SystemMessage>>() {
                    }.getType());
                    systemService.saveSystemMessageToDB(systemMessageList);
                    UserUtil.saveLastUpdateTime("SystemMessageUpdateTime");
                } else {
                    Log.e(this.getClass().getName(), response);
                }
                UserUtil.messageSynced = true;
            }

            @Override
            public void onFailure(Throwable error, String content) {
                UserUtil.messageSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void syncMissions() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("MissionUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.MISSION_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    MissionService missionService = new MissionService(databaseHelper);
                    List<Mission> missionList = gson.fromJson(response, new TypeToken<List<Mission>>() {
                    }.getType());
                    missionService.saveMissionToDB(missionList);
                    UserUtil.saveLastUpdateTime("MissionUpdateTime");
                } else {
                    Log.e(this.getClass().getName(), response);
                }
                UserUtil.missionSynced = true;
            }

            @Override
            public void onFailure(Throwable error, String content) {
                UserUtil.missionSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void syncRunningHistories() {
        String lastUpdateTime = UserUtil.getLastUpdateTime("RunningHistoryUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.RUNNING_HISTORY_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
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
                UserUtil.historySynced = true;
            }

            @Override
            public void onFailure(Throwable error, String content) {
                UserUtil.historySynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public void uploadRunningHistories() {
        RunningHistoryService runningHistoryService = new RunningHistoryService(databaseHelper);
        List<RunningHistory> runningHistoryList = runningHistoryService.fetchUnsyncedRunHistory(UserUtil.getUserId());
        if (runningHistoryList != null && runningHistoryList.size() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.POST_RUNNING_HISTORY_URL, UserUtil.getUserId()));
            httpClientHelper.post(url, null, gson.toJson(runningHistoryList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    if (statusCode == 200 || statusCode == 204) {
                        RunningHistoryService runningHistoryService = new RunningHistoryService(databaseHelper);
                        runningHistoryService.updateUnsyncedRunHistories(UserUtil.getUserId());
                    } else {
                        Log.e(this.getClass().getName(), response);
                    }
                    syncUserInfo();
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                }
            });
        } else {
            UserUtil.userSynced = true;
        }
    }

    public void upLoadUserCollect() {
        final PlanService planService = new PlanService(databaseHelper);
        List<PlanCollect> planCollectList = planService.fetchUnsyncedPlanCollect(UserUtil.getUserId());
        if (planCollectList != null && planCollectList.size() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.PUT_USER_COLLECT_PLAN_URL, UserUtil.getUserId()));
            httpClientHelper.post(url, null, gson.toJson(planCollectList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    planService.updateUnsyncedPlanCollect(UserUtil.getUserId());
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                }
            });
        }
    }


    public void upLoadUserFollow() {
        final PlanService planService = new PlanService(databaseHelper);
        List<PlanUserFollow> planUserFollowList = planService.fetchUnsyncedPlanUserFollow(UserUtil.getUserId());
        if (planUserFollowList != null && planUserFollowList.size() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.PUT_USER_FOLLOWER_LIST_URL, UserUtil.getUserId()));
            httpClientHelper.post(url, null, gson.toJson(planUserFollowList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    planService.updateUnsyncedPlanUserFollow(UserUtil.getUserId());
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                }
            });
        }
    }

    public void syncUserInfo() {
        if (UserUtil.getUserId() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.USER_INFO, UserUtil.getUserId()));
            httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    if (statusCode == 200 || statusCode == 204) {
                        UserService userService = new UserService(databaseHelper);
                        UserBase userBase = gson.fromJson(response, UserBase.class);
                        UserInfo userInfo = gson.fromJson(response, UserInfo.class);
                        userBase.setUserInfo(userInfo);
                        userService.saveUserInfoToDB(userBase);
                        UserUtil.saveUserInfoToList(userBase);
                    } else {
                        Log.e(this.getClass().getName(), response);
                    }
                    UserUtil.userSynced = true;
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    UserUtil.userSynced = true;
                    Log.e(this.getClass().getName(), error.getMessage());
                }
            });
        } else {
            UserUtil.userSynced = true;
        }
    }
}
