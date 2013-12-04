package com.G5432.Cyberace.History;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.Enum.*;
import com.G5432.Entity.RunningHistory;
import com.G5432.HttpClient.BackendSyncRequest;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Service.SystemService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-23
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryActivity extends OrmLiteBaseActivity<DatabaseHelper> implements GestureDetector.OnGestureListener {

    private ViewFlipper viewFlipper;

    private GestureDetector detector;

    private Button btnReturn;
    private Button btnFilter;
    private Button btnFilterRun;
    private Button btnFilterChallenge;
    private ImageView imgFilterRun;
    private ImageView imgFilterChallenge;
    private Button btnSync;
    private TextView txtNoRecord;

    private RunningHistoryService runningHistoryService;
    private SystemService systemService;

    private boolean challengeFilter = true;

    private boolean runFilter = true;

    //detail
    private ListView recordList;
    private List<RunningHistory> runningHistoryList;

    //total
    private TextView txtDistance;
    private TextView txtDistanceTip;
    private TextView txtSpeed;
    private TextView txtSpeedTip;
    private TextView txtCar;
    private TextView txtCarTip;
    private TextView txtChallenge;
    private RelativeLayout layoutContent;

    private Map<String, String> totalMap;

    protected MotionEvent mLastOnDownEvent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runhistory);
        viewFlipper = (ViewFlipper) this.findViewById(R.id.historyrunFlipper);
        viewFlipper.addView(addDetailView());
        viewFlipper.addView(addTotalView());
        btnReturn = (Button) findViewById(R.id.historyBtnReturn);
        btnFilter = (Button) findViewById(R.id.historyBtnFilter);
        btnFilterRun = (Button) findViewById(R.id.historyBtnFilterRun);
        btnFilterChallenge = (Button) findViewById(R.id.historyBtnFilterChallenge);
        btnSync = (Button) findViewById(R.id.historyBtnSync);
        txtNoRecord = (TextView) findViewById(R.id.historyTxtNoRecord);
        imgFilterRun = (ImageView) findViewById(R.id.historyImgFilterRun);
        imgFilterChallenge = (ImageView) findViewById(R.id.historyImgFilterChallenge);
        runningHistoryService = new RunningHistoryService(getHelper());
        systemService = new SystemService(getHelper());
        runningHistoryList = intiRunHistory();
        totalMap = initContent();
        detector = new GestureDetector(getApplicationContext(), this);
        initPage();
    }

    private List<RunningHistory> intiRunHistory() {
        List<Integer> filter = new ArrayList<Integer>();
        if (challengeFilter) {
            filter.add(MissionTypeEnum.Challenge.ordinal());
        }
        if (runFilter) {
            filter.add(MissionTypeEnum.NormalRun.ordinal());
        }
        if (filter.size() > 0) {
            return runningHistoryService.fetchRunHistoryByUserIdAndMissionType(UserUtil.getUserId(), filter);
        } else {
            return new ArrayList<RunningHistory>();
        }
    }

    private Map<String, String> initContent() {
        List<Integer> filter = new ArrayList<Integer>();
        if (challengeFilter) {
            filter.add(MissionTypeEnum.Challenge.ordinal());
        }
        if (runFilter) {
            filter.add(MissionTypeEnum.NormalRun.ordinal());
        }
        if (filter.size() > 0) {
            return runningHistoryService.fetchTotalByUserIdAndMissionType(UserUtil.getUserId(), filter);
        } else {
            return new HashMap<String, String>();
        }
    }

    private void initPage() {
        btnFilterRun.setVisibility(View.INVISIBLE);
        btnFilterChallenge.setVisibility(View.INVISIBLE);
        imgFilterRun.setVisibility(View.INVISIBLE);
        imgFilterChallenge.setVisibility(View.INVISIBLE);
        if (runningHistoryList == null || runningHistoryList.size() <= 0) {
            recordList.setVisibility(View.GONE);
            layoutContent.setVisibility(View.GONE);
        } else {
            txtNoRecord.setVisibility(View.GONE);
            showHistoryList(runningHistoryList);
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
                            List<RunningHistory> newHistory = intiRunHistory();
                            showHistoryList(newHistory);
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
                            List<RunningHistory> newHistory = intiRunHistory();
                            showHistoryList(newHistory);
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
                finish();
                Intent intent = new Intent(RunHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private View addDetailView() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View detailView = layoutInflater.inflate(R.layout.historydetail, null);
        recordList = (ListView) detailView.findViewById(R.id.historydetailListDetails);
        recordList.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RunHistoryActivity.this.detector.onTouchEvent(event);
                return false;
            }

        });
        return detailView;
    }

    private View addTotalView() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View totalView = layoutInflater.inflate(R.layout.historytotal, null);
        txtDistance = (TextView) totalView.findViewById(R.id.historytotalTxtDistance);
        txtDistanceTip = (TextView) totalView.findViewById(R.id.historytotalTxtDistanceTip);
        txtSpeed = (TextView) totalView.findViewById(R.id.historytotalTxtSpeed);
        txtSpeedTip = (TextView) totalView.findViewById(R.id.historytotalTxtSpeedTip);
        txtCar = (TextView) totalView.findViewById(R.id.historytotalTxtCar);
        txtCarTip = (TextView) totalView.findViewById(R.id.historytotalTxtCarTip);
        txtChallenge = (TextView) totalView.findViewById(R.id.historytotalTxtChallenge);
        layoutContent = (RelativeLayout) totalView.findViewById(R.id.historytotalLayoutContent);
        return totalView;
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
                ToastUtil.showMessage(getApplicationContext(), systemService.getSystemMessage(Constant.SYNC_DATA_SUCCESS));
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
        if (showList == null || showList.size() <= 0) {
            recordList.setVisibility(View.GONE);
            txtNoRecord.setVisibility(View.VISIBLE);
        } else {
            txtNoRecord.setVisibility(View.GONE);
            recordList.setVisibility(View.VISIBLE);
            ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
            Date lastDate = new Date();
            for (RunningHistory runningHistory : showList) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("titleTag", false);
                map.put("validate", runningHistory.getValid());
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        mLastOnDownEvent = motionEvent;
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        if (motionEvent==null)
            motionEvent = mLastOnDownEvent;
        if (motionEvent==null || motionEvent2==null)
            return false;
        if (motionEvent.getX() - motionEvent2.getX() > 60) {
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.viewFlipper.showNext();
            return true;
        } else if (motionEvent.getX() - motionEvent2.getX() < - 60) {
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.viewFlipper.showPrevious();
            return true;
        }
        return false;
    }

}
