package com.G5432.Cyberace.History;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.BMapInfo;
import com.G5432.Entity.RunningHistory;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Utils.BMapApplication;
import com.G5432.Utils.CommonUtil;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-12-5
 * Time: 上午9:36
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryLargeMapActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private MapView mMapView;

    private BMapApplication bMapApp = null;
    private MapController mMapController = null;
    private BMapInfo mapInfo;
    private RunningHistoryService runningHistoryService = null;
    String runUuid = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapApp = (BMapApplication) this.getApplication();
        Bundle extras = getIntent().getExtras();
        runUuid = extras.getString("runUuid");
        setContentView(R.layout.largemappage);
        btnReturn = (Button) findViewById(R.id.largeMapBtnReturn);
        mMapView = (MapView) findViewById(R.id.largeMapMap);
        showHistory(runUuid);
        initPage();
    }

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showHistory(String runUuid) {

        runningHistoryService = new RunningHistoryService(getHelper());

        RunningHistory runningHistory = runningHistoryService.fetchRunHistoryByRunId(runUuid);
        mapInfo = CommonUtil.getRoutesFromString(runningHistory.getMissionRoute());

        RouteOverlay routeOverlay = new RouteOverlay(RunHistoryLargeMapActivity.this, mMapView);
        routeOverlay.setData(mapInfo.getRoute());

        mMapController = mMapView.getController();

        mMapView.getController().enableClick(true);
        //向地图添加构造好的RouteOverlay
        mMapView.getOverlays().add(routeOverlay);

        //执行刷新使生效
        mMapView.refresh();
        startTimerChecker();
    }

    private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer.cancel();
                mMapController.zoomToSpan(mapInfo.getSpanLat(), mapInfo.getSpanLon());
                mMapController.animateTo(mapInfo.getCenterPoint());
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
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
