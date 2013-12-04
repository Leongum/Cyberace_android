package com.G5432.Cyberace.History;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.G5432.Cyberace.R;
import com.G5432.Cyberace.Setting.BodySettingActivity;
import com.G5432.Cyberace.ShareSNS.ShareSNSActivity;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.BMapInfo;
import com.G5432.Entity.RunningHistory;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Utils.BMapApplication;
import com.G5432.Utils.CommonUtil;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-30
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryFinishedActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnShare;
    private MapView mMapView;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtDistance;
    private TextView txtSpeed;
    private TextView txtCar;
    private TextView txtGrade;
    private ImageView imgMap;
    private RelativeLayout layoutSNSShare;
    private Button btnSNSShare;
    private Button btnWeixinShare;

    private BMapApplication bMapApp = null;

    private MapController mMapController = null;

    private BMapInfo mapInfo;

    private RunningHistoryService runningHistoryService= null;

    String runUuid = "";

    String imagePath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapApp = (BMapApplication)this.getApplication();
        Bundle extras = getIntent().getExtras();
        runUuid = extras.getString("runUuid");
        setContentView(R.layout.historyfinishedpage);
        btnReturn = (Button) findViewById(R.id.historyFinishedBtnReturn);
        btnShare = (Button) findViewById(R.id.historyFinishedBtnShare);
        mMapView = (MapView) findViewById(R.id.historyFinishedMap);
        txtDate = (TextView) findViewById(R.id.historyFinishedTxtData);
        txtTime = (TextView) findViewById(R.id.historyFinishedTxtTime);
        txtDistance = (TextView) findViewById(R.id.historyFinishedTxtDistance);
        txtSpeed = (TextView) findViewById(R.id.historyFinishedTxtSpeed);
        txtCar = (TextView) findViewById(R.id.historyFinishedTxtCar);
        txtGrade = (TextView) findViewById(R.id.historyFinishedTxtGrade);
        imgMap = (ImageView) findViewById(R.id.historyFinishedImgMap);
        btnSNSShare = (Button) findViewById(R.id.historyFinishedBtnSNSShare);
        btnWeixinShare = (Button) findViewById(R.id.historyFinishedBtnWeixinShare);
        layoutSNSShare = (RelativeLayout) findViewById(R.id.historyFinishedShareBg);

        mMapView.regMapViewListener(BMapApplication.getInstance().mBMapManager, mMapListener);
        showHistory(runUuid);
        initPage();
    }

    MKMapViewListener mMapListener = new MKMapViewListener() {
        @Override
        public void onMapMoveFinish() {
            /**
             * 在此处理地图移动完成回调
             * 缩放，平移等操作完成后，此回调被触发
             */
        }

        @Override
        public void onClickMapPoi(MapPoi mapPoiInfo) {
            /**
             * 在此处理底图poi点击事件
             * 显示底图poi名称并移动至该点
             * 设置过： mMapController.enableClick(true); 时，此回调才能被触发
             *
             */
        }

        @Override
        public void onGetCurrentMap(Bitmap bitmap) {
            /**
             *  当调用过 mMapView.getCurrentMap()后，此回调会被触发
             *  可在此保存截图至存储设备
             */
            imgMap.setVisibility(View.VISIBLE);
            mMapView.setVisibility(View.GONE);
            BitmapDrawable bd = new BitmapDrawable(bitmap);
            imgMap.setBackgroundDrawable(bd);
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        }

        @Override
        public void onMapAnimationFinish() {
            /**
             *  地图完成带动画的操作（如: animationTo()）后，此回调被触发
             */
        }

        @Override
        public void onMapLoadFinish() {
            // TODO Auto-generated method stub

        }
    };

    private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer.cancel();
                mMapController.zoomToSpan(mapInfo.getSpanLat(),mapInfo.getSpanLon());
                mMapController.animateTo(mapInfo.getCenterPoint());
            }
            else if(msg.what == 2){
                btnReturn.setAlpha(0);
                btnShare.setAlpha(0);
                imagePath = GetandSaveCurrentImage();
                imgMap.setVisibility(View.GONE);
                mMapView.setVisibility(View.VISIBLE);
                btnReturn.setAlpha(1);
                btnShare.setAlpha(1);
                layoutSNSShare.setVisibility(View.VISIBLE);
                layoutSNSShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layoutSNSShare.setVisibility(View.GONE);
                    }
                });
            }
        }
    };

    private void startTimerChecker() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "begin time task check");
                int time = 0;
                // 定义一个消息传过去
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

    private void showHistory(String runUuid) {
        runningHistoryService = new RunningHistoryService(getHelper());

        RunningHistory runningHistory = runningHistoryService.fetchRunHistoryByRunId(runUuid);
        mapInfo = CommonUtil.getRoutesFromString(runningHistory.getMissionRoute());

        RouteOverlay routeOverlay = new RouteOverlay(RunHistoryFinishedActivity.this, mMapView);
        routeOverlay.setData(mapInfo.getRoute());

        mMapController = mMapView.getController();

        //mMapView.getController().setZoom(16);
        mMapView.getController().enableClick(true);
        //向地图添加构造好的RouteOverlay
        mMapView.getOverlays().add(routeOverlay);

        //执行刷新使生效
        mMapView.refresh();
        txtDate.setText(runningHistory.getMissionDate().toGMTString());
        txtTime.setText(CommonUtil.transSecondToStandardFormat(runningHistory.getDuration()));
        txtSpeed.setText(CommonUtil.transSpeedToStandardFormat(runningHistory.getAvgSpeed()));
        txtCar.setText(runningHistory.getSpendCarlorie().toString());
        txtDistance.setText(CommonUtil.transDistanceToStandardFormat(runningHistory.getDistance()));
        txtGrade.setText(runningHistory.getGrade().toString());
        startTimerChecker();
    }

    private void initPage() {
        btnReturn.setOnClickListener(returnListener);

        btnShare.setOnClickListener(shareListener);

        btnSNSShare.setOnClickListener(shareSNSShare);

        btnWeixinShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform plat = ShareSDK.getPlatform(getApplication(), WechatMoments.NAME);
                Wechat.ShareParams sp = new Wechat.ShareParams();
                sp.title = "赛跑乐";
                sp.text = "赛跑乐的快乐分享";
                sp.shareType = Platform.SHARE_IMAGE;
                sp.imagePath = imagePath;
                plat.share(sp);
            }
        });
    }

    private View.OnClickListener shareSNSShare = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RunHistoryFinishedActivity.this, ShareSNSActivity.class);
            intent.putExtra("imagePath", imagePath);
            intent.putExtra("runUuid", runUuid);
            startActivity(intent);
        }
    };

    private View.OnClickListener shareListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mMapView.getCurrentMap();
        }
    };


    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };


    private String GetandSaveCurrentImage() {
        //构建Bitmap
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        bitmap = decorview.getDrawingCache();
        //图片存储路径
        String SavePath = getApplicationContext().getFilesDir().getAbsolutePath() + "/ScreenImages";
        //保存Bitmap
        try {
            File path = new File(SavePath);
            //文件
            String filePath = SavePath + "/share_temp.png";
            File file = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
