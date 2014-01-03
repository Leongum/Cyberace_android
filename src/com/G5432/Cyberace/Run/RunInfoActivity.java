package com.G5432.Cyberace.Run;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.BMapInfo;
import com.G5432.Utils.BMapApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-1
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class RunInfoActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnStart;
    private Button btnCancel;
    private TextView txtTime;
    private TextView txtSpeed;
    private TextView txtDistance;


    private BMapApplication bMapApp = null;

    BMapManager mBMapMan = null;

    MapView mMapView = null;

    LocationData locData = null;

    public MyLocationListener myListener = new MyLocationListener();

    //定位图层
    locationOverlay myLocationOverlay = null;

    private MapController mMapController = null;

    private BMapInfo mapInfo;

    private RouteOverlay routeOverlay;

    private MKRoute mkRoute = new MKRoute();

    private GeoPoint start = null;
    private GeoPoint end = null;
    private GeoPoint lastPoint = null;
    private List<GeoPoint>  pointsLists = new ArrayList<GeoPoint>();

    private boolean isStart = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //init location params
        bMapApp = (BMapApplication) this.getApplication();
        bMapApp.mLocationClient.registerLocationListener(myListener);
        bMapApp.setMapLocationOption();
        bMapApp.mLocationClient.start();
        bMapApp.mLocationClient.requestLocation();

        setContentView(R.layout.runinfopage);
        btnStart = (Button) findViewById(R.id.runInfoBtnStart);
        btnCancel = (Button) findViewById(R.id.runInfoBtnCancel);
        txtTime = (TextView) findViewById(R.id.runInfoTxtTime);
        txtDistance = (TextView) findViewById(R.id.runInfoTxtDistance);
        txtSpeed = (TextView) findViewById(R.id.runInfoTxtSpeed);

        mMapView = (MapView) findViewById(R.id.runInfoMapMap);
        routeOverlay = new RouteOverlay(RunInfoActivity.this, mMapView);
        routeOverlay.setEnMarker(null);
        routeOverlay.setStMarker(null);
        locData = new LocationData();
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(16);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.getOverlays().add(routeOverlay);
        //定位图层初始化
        myLocationOverlay = new locationOverlay(mMapView);
        //设置定位数据
        myLocationOverlay.setData(locData);
        //添加定位图层
        mMapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();
        //修改定位数据后刷新图层生效
        mMapView.refresh();

        //start button init
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStart){
                    btnStart.setText("继续");
                    bMapApp.mLocationClient.stop();
                }
            }
        });
        btnStart.setClickable(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    //private Timer timer;

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                mMapView.getOverlays().remove(routeOverlay);
                routeOverlay.setData(mkRoute);
                mMapView.getOverlays().add(routeOverlay);
                mMapView.refresh();
            }
        }
    };

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            //mMapView.refresh();
            //是手动触发请求或首次定位时，移动到定位点
            //移动地图到定位点
            GeoPoint geoPoint = new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6));
            if (start == null) {
                start = geoPoint;
            }
            if(lastPoint == null || (lastPoint.getLongitudeE6() != geoPoint.getLongitudeE6() && lastPoint.getLatitudeE6() != geoPoint.getLatitudeE6())){
                Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));
                myLocationOverlay.setLocationMode(MyLocationOverlay.LocationMode.FOLLOWING);
                mMapView.refresh();
            }
            end = geoPoint;
            if(lastPoint != null && lastPoint.getLongitudeE6() != geoPoint.getLongitudeE6() && lastPoint.getLatitudeE6() != geoPoint.getLatitudeE6()){
                pointsLists.add(geoPoint);
                GeoPoint[] pl = new GeoPoint[pointsLists.size()];
                pointsLists.toArray(pl);
                //mkRoute = new MKRoute();
                mkRoute.customizeRoute(start, end, pl);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
            lastPoint = geoPoint;
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    //继承MyLocationOverlay重写dispatchTap实现点击处理
    public class locationOverlay extends MyLocationOverlay {

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
    protected void onDestroy() {
        mMapView.destroy();
        if (mBMapMan != null) {
            mBMapMan.destroy();
            mBMapMan = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mBMapMan != null) {
            mBMapMan.stop();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        if (mBMapMan != null) {
            mBMapMan.start();
        }
        super.onResume();
    }
}
