package com.G5432.Cyberace.Run;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.BMapInfo;
import com.G5432.Entity.RunningHistory;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Utils.BMapApplication;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.UserUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-1
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class RunInfoActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;

    private BMapApplication bMapApp = null;

    BMapManager mBMapMan = null;

    MapView mMapView = null;

    LocationData locData = null;

    public MyLocationListener myListener = new MyLocationListener();

    //定位图层
    locationOverlay myLocationOverlay = null;

    private MapController mMapController = null;

    boolean isRequest = false;//是否手动触发请求定位
    boolean isFirstLoc = true;//是否首次定位

    private BMapInfo mapInfo;

    private RunningHistoryService runningHistoryService= null;

    private RunningHistory testRunningHistory = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //init location params
        bMapApp = (BMapApplication)this.getApplication();
        bMapApp.mLocationClient.registerLocationListener(myListener);
        //bMapApp.setMapLocationOption();
        //bMapApp.mLocationClient.start();
        //bMapApp.mLocationClient.requestLocation();

        setContentView(R.layout.runinfopage);
        mMapView=(MapView)findViewById(R.id.bmapsView);
        runningHistoryService = new RunningHistoryService(getHelper());

        testRunningHistory = runningHistoryService.fetchRunHistoryByRunId("46C54DCC-87A5-462F-AB55-A431BDD8B333");
        mapInfo = CommonUtil.getRoutesFromString(testRunningHistory.getMissionRoute());

        RouteOverlay routeOverlay = new RouteOverlay(RunInfoActivity.this, mMapView);
        routeOverlay.setData(mapInfo.getRoute());

        mMapController = mMapView.getController();

        //mMapView.getController().setZoom(16);
        mMapView.getController().enableClick(true);
        //向地图添加构造好的RouteOverlay
        mMapView.getOverlays().add(routeOverlay);

        //执行刷新使生效
        mMapView.refresh();
        startTimerChecker();



//        mMapView=(MapView)findViewById(R.id.bmapsView);
//        locData = new LocationData();
//        mMapController = mMapView.getController();
//        mMapView.getController().setZoom(16);
//        mMapView.getController().enableClick(true);
//        mMapView.setBuiltInZoomControls(true);
//
//        //定位图层初始化
//        myLocationOverlay = new locationOverlay(mMapView);
//        //设置定位数据
//        myLocationOverlay.setData(locData);
//        //添加定位图层
//        mMapView.getOverlays().add(myLocationOverlay);
//        myLocationOverlay.enableCompass();
//        //修改定位数据后刷新图层生效
//        mMapView.refresh();
    }

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

        timer.schedule(timerTask, 3000, 2000);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;

//            locData.latitude = location.getLatitude();
//            locData.longitude = location.getLongitude();
//            //如果不显示定位精度圈，将accuracy赋值为0即可
//            locData.accuracy = location.getRadius();
//            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
//            locData.direction = location.getDerect();
//            //更新定位数据
//            myLocationOverlay.setData(locData);
//            //更新图层数据执行刷新后生效
//            //mMapView.refresh();
//            //是手动触发请求或首次定位时，移动到定位点
//            if (isRequest || isFirstLoc){
//                //移动地图到定位点
//                Log.d("LocationOverlay", "receive location, animate to it");
//                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
//                isRequest = false;
//                myLocationOverlay.setLocationMode(MyLocationOverlay.LocationMode.FOLLOWING);
//            }
//            //首次定位完成
//            isFirstLoc = false;
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }

    //继承MyLocationOverlay重写dispatchTap实现点击处理
    public class locationOverlay extends MyLocationOverlay{

        public locationOverlay(MapView mapView) {
            super(mapView);
            // TODO Auto-generated constructor stub
        }
        @Override
        protected boolean dispatchTap() {
            // TODO Auto-generated method stub
            return true;
        }

    }


    @Override
    protected void onDestroy(){
        mMapView.destroy();
        if(mBMapMan!=null){
            mBMapMan.destroy();
            mBMapMan=null;
        }
        super.onDestroy();
    }
    @Override
    protected void onPause(){
        mMapView.onPause();
        if(mBMapMan!=null){
            mBMapMan.stop();
        }
        super.onPause();
    }
    @Override
    protected void onResume(){
        mMapView.onResume();
        if(mBMapMan!=null){
            mBMapMan.start();
        }
        super.onResume();
    }
}
