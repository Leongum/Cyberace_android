package com.G5432.Cyberace.History;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.RunningHistory;
import com.G5432.Enums.MissionType;
import com.G5432.HttpClient.BackendSyncRequest;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Service.SystemService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-23
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryDetailActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnFilter;
    private Button btnFilterRun;
    private Button btnFilterChallenge;
    private ImageView imgFilterRun;
    private ImageView imgFilterChallenge;
    private Button btnSync;
    private ListView recordList;
    private TextView txtNoRecord;

    private RunningHistoryService runningHistoryService;

    private List<RunningHistory> runningHistoryList;

    private boolean challengeFilter = true;

    private boolean runFilter = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historydetail);
        btnReturn = (Button) findViewById(R.id.historydetailBtnReturn);
        btnFilter = (Button) findViewById(R.id.historydetailBtnFilter);
        btnFilterRun = (Button) findViewById(R.id.historydetailBtnFilterSuibian);
        btnFilterChallenge = (Button) findViewById(R.id.historydetailBtnFilterChallenge);
        btnSync = (Button) findViewById(R.id.historydetailBtnSync);
        recordList = (ListView) findViewById(R.id.historydetailListDetails);
        txtNoRecord = (TextView) findViewById(R.id.historydetailTxtNoRecord);
        imgFilterRun = (ImageView) findViewById(R.id.historydetailImgFilterSuibian);
        imgFilterChallenge = (ImageView) findViewById(R.id.historydetailImgFilterChallenge);
        runningHistoryService = new RunningHistoryService(getHelper());
        runningHistoryList = intiRunHistory();
        initPage();
    }

    private List<RunningHistory> intiRunHistory() {
        List<Integer> filter = new ArrayList<Integer>();
        if (challengeFilter) {
            filter.add(MissionType.Challenge.ordinal());
        }
        if (runFilter) {
            filter.add(MissionType.NormalRun.ordinal());
        }
        if (filter.size() > 0) {
            return runningHistoryService.fetchRunHistoryByUserIdAndMissionType(UserUtil.getUserId(), filter);
        } else {
            return new ArrayList<RunningHistory>();
        }
    }

    private void initPage() {
        btnFilterRun.setVisibility(View.INVISIBLE);
        btnFilterChallenge.setVisibility(View.INVISIBLE);
        imgFilterRun.setVisibility(View.INVISIBLE);
        imgFilterChallenge.setVisibility(View.INVISIBLE);
        if (runningHistoryList == null || runningHistoryList.size() <= 0) {
            recordList.setVisibility(View.GONE);
        } else {
            txtNoRecord.setVisibility(View.GONE);
            showHistoryList(runningHistoryList);
        }

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackendSyncRequest backendSyncRequest = new BackendSyncRequest(getHelper());
                UserUtil.userSynced = false;
                UserUtil.historySynced = false;
                startTimerChecker();
                backendSyncRequest.syncRunningHistories();
                backendSyncRequest.uploadRunningHistories();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSync.getVisibility() == View.GONE) {
                    btnSync.setVisibility(View.VISIBLE);
                    btnFilterRun.setVisibility(View.INVISIBLE);
                    btnFilterChallenge.setVisibility(View.INVISIBLE);
                    imgFilterRun.setVisibility(View.INVISIBLE);
                    imgFilterChallenge.setVisibility(View.INVISIBLE);
                } else {
                    btnSync.setVisibility(View.GONE);
                    btnFilterRun.setVisibility(View.VISIBLE);
                    btnFilterRun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (runFilter) {
                                runFilter = false;
                                imgFilterRun.setVisibility(View.INVISIBLE);
                            } else {
                                runFilter = true;
                                imgFilterRun.setVisibility(View.VISIBLE);
                            }
                            List<RunningHistory> newHistory = intiRunHistory();
                            showHistoryList(newHistory);
                        }
                    });
                    btnFilterChallenge.setVisibility(View.VISIBLE);
                    btnFilterChallenge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (challengeFilter) {
                                challengeFilter = false;
                                imgFilterChallenge.setVisibility(View.INVISIBLE);
                            } else {
                                challengeFilter = true;
                                imgFilterChallenge.setVisibility(View.VISIBLE);
                            }
                            List<RunningHistory> newHistory = intiRunHistory();
                            showHistoryList(newHistory);
                        }
                    });
                    if (runFilter) {
                        imgFilterRun.setVisibility(View.VISIBLE);
                    } else {
                        imgFilterRun.setVisibility(View.INVISIBLE);
                    }
                    if (challengeFilter) {
                        imgFilterChallenge.setVisibility(View.VISIBLE);
                    } else {
                        imgFilterChallenge.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(RunHistoryDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer.cancel();
                SystemService systemService = new SystemService(getHelper());
                CommonUtil.showMessages(getApplicationContext(), systemService.getSystemMessage(Constant.SYNC_DATA_SUCCESS));
            }
        }
    };

    private void startTimerChecker() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "begin time task check");
                // 定义一个消息传过去
                Message msg = new Message();
                msg.what = (UserUtil.historySynced && UserUtil.userSynced) ? 1 : 0;
                handler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 0, 1000);
    }

    private void showHistoryList(List<RunningHistory> showList) {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        Date lastDate = new Date();
        for (RunningHistory runningHistory : showList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("titleTag", false);
            map.put("validate",runningHistory.getValid());
            map.put("runUuid", runningHistory.getRunUuid());
            if (!runningHistory.getMissionDate().equals(lastDate)) {
                HashMap<String, Object> titleMap = new HashMap<String, Object>();
                titleMap.put("titleTag", true);
                titleMap.put("title", CommonUtil.parseDateToStringOnlyDate(runningHistory.getMissionDate()));
                listItem.add(titleMap);
                lastDate = runningHistory.getMissionDate();
            }
            map.put("distance", CommonUtil.transDistanceToStandardFormat(runningHistory.getDistance()));
            map.put("time", CommonUtil.transSecondToStandardFormat(runningHistory.getDuration()));
            listItem.add(map);
        }

        GroupListAdapter listItemAdapter = new GroupListAdapter(this, listItem, R.layout.historylistrecord,
                new String[]{"distance", "time",},
                new int[]{R.id.historydetailTxtRecordDistance, R.id.historydetailTxtTime}
        );

        recordList.setAdapter(listItemAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}