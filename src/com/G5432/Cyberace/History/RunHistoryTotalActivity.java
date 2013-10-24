package com.G5432.Cyberace.History;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.text.MessageFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-24
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryTotalActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnFilter;
    private Button btnFilterChallenge;
    private Button btnFilterRun;
    private ImageView imgFilterChallenge;
    private ImageView imgFilterRun;
    private Button btnSync;
    private TextView txtDistance;
    private TextView txtDistanceTip;
    private TextView txtSpeed;
    private TextView txtSpeedTip;
    private TextView txtCar;
    private TextView txtCarTip;
    private TextView txtChallenge;
    private RelativeLayout layoutContent;
    private TextView txtNoRecord;

    private RunningHistoryService runningHistoryService;

    private SystemService systemService;

    private Map<String, String> totalMap;

    private boolean challengeFilter = true;

    private boolean runFilter = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historytotal);
        btnReturn = (Button) findViewById(R.id.historytotalBtnReturn);
        btnSync = (Button) findViewById(R.id.historytotalBtnSync);
        btnFilter = (Button) findViewById(R.id.historytotalBtnFilter);
        btnFilterChallenge = (Button) findViewById(R.id.historytotalBtnFilterChallenge);
        btnFilterRun = (Button) findViewById(R.id.historytotalBtnFilterRun);
        imgFilterChallenge = (ImageView) findViewById(R.id.historytotalImgChallenegCheck);
        imgFilterRun = (ImageView) findViewById(R.id.historytotalImgRunCheck);
        txtDistance = (TextView) findViewById(R.id.historytotalTxtDistance);
        txtDistanceTip = (TextView) findViewById(R.id.historytotalTxtDistanceTip);
        txtSpeed = (TextView) findViewById(R.id.historytotalTxtSpeed);
        txtSpeedTip = (TextView) findViewById(R.id.historytotalTxtSpeedTip);
        txtCar = (TextView) findViewById(R.id.historytotalTxtCar);
        txtCarTip = (TextView) findViewById(R.id.historytotalTxtCarTip);
        txtChallenge = (TextView) findViewById(R.id.historytotalTxtChallenge);
        layoutContent = (RelativeLayout) findViewById(R.id.historytotalLayoutContent);
        txtNoRecord = (TextView) findViewById(R.id.historytotalNoRecord);
        runningHistoryService = new RunningHistoryService(getHelper());
        systemService = new SystemService(getHelper());
        totalMap = initContent();
        initPage();

    }

    private Map<String, String> initContent() {
        List<Integer> filter = new ArrayList<Integer>();
        if (challengeFilter) {
            filter.add(MissionType.Challenge.ordinal());
        }
        if (runFilter) {
            filter.add(MissionType.NormalRun.ordinal());
        }
        if (filter.size() > 0) {
            return runningHistoryService.fetchTotalByUserIdAndMissionType(UserUtil.getUserId(), filter);
        } else {
            return new HashMap<String, String>();
        }
    }

    private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer.cancel();
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

    private void initPage() {
        btnFilterRun.setVisibility(View.INVISIBLE);
        btnFilterChallenge.setVisibility(View.INVISIBLE);
        imgFilterRun.setVisibility(View.INVISIBLE);
        imgFilterChallenge.setVisibility(View.INVISIBLE);
        if (totalMap == null) {
            layoutContent.setVisibility(View.GONE);
        } else {
            txtNoRecord.setVisibility(View.GONE);
            showContent(totalMap);
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
                            Map<String, String> newContent = initContent();
                            showContent(newContent);
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
                            Map<String, String> newContent = initContent();
                            showContent(newContent);
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
                onBackPressed();
            }
        });
    }

    private void showContent(Map<String, String> totalMap) {
        if (totalMap.get("distance") != null) {
            layoutContent.setVisibility(View.VISIBLE);
            txtNoRecord.setVisibility(View.GONE);
            txtChallenge.setText(totalMap.get("challenge"));
            txtDistance.setText(CommonUtil.transDistanceToStandardFormat(Double.valueOf(totalMap.get("distance"))));
            txtDistanceTip.setText(systemService.getSystemMessage(Constant.STATISTICS_DISTANCE_MESSAGE, Double.valueOf(totalMap.get("distance"))));
            txtSpeed.setText(CommonUtil.transSpeedToStandardFormat(Double.valueOf(totalMap.get("speed"))));
            txtSpeedTip.setText(systemService.getSystemMessage(Constant.STATISTICS_SPEED_MESSAGE, Double.valueOf(totalMap.get("speed"))));
            txtCar.setText(MessageFormat.format("{0} kcal", String.format("%.0f", Double.valueOf(totalMap.get("carlorie")))));
            txtCarTip.setText(systemService.getSystemMessage(Constant.STATISTICS_CALORIE_MESSAGE, Double.valueOf(totalMap.get("carlorie"))));
        } else {
            layoutContent.setVisibility(View.GONE);
            txtNoRecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
