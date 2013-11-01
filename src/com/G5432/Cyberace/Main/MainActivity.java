package com.G5432.Cyberace.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import com.G5432.Cyberace.History.RunHistoryActivity;
import com.G5432.Cyberace.R;
import com.G5432.Cyberace.Run.ChallengeMainActivity;
import com.G5432.Cyberace.Setting.SettingActivity;
import com.G5432.Cyberace.ShareSNS.ShareSNSActivity;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.Service.UserService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.UserUtil;
import com.baidu.location.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-17
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnLogin;
    private Button btnTraffic;
    private Button btnRun;
    private Button btnChallengeRun;
    private Button btnHistory;
    private Button btnSetting;

    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean mIsStart;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        initLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ShareSDK.initSDK(this);
        btnLogin = (Button) findViewById(R.id.mianBtnLogin);
        btnTraffic = (Button) findViewById(R.id.mianBtnTraffic);
        btnRun = (Button) findViewById(R.id.mianBtnRun);
        btnChallengeRun = (Button) findViewById(R.id.mianBtnChallengeRun);
        btnHistory = (Button) findViewById(R.id.mianBtnHistory);
        btnSetting = (Button) findViewById(R.id.mianBtnSetting);

        initPage();
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        /**——————————————————————————————————————————————————————————————————
         * 这里的AK和应用签名包名绑定，如果使用在自己的工程中需要替换为自己申请的Key
         * ——————————————————————————————————————————————————————————————————
         */
        mLocationClient.setAK("8877eb0e93c16e552be304c6333002eb");
        mLocationClient.registerLocationListener(myListener);
        mGeofenceClient = new GeofenceClient(this);
    }

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                /**
                 * 格式化显示地址信息
                 */
				sb.append("\n省：");
				sb.append(location.getProvince());
				sb.append("\n市：");
				sb.append(location.getCity());
				sb.append("\n区/县：");
				sb.append(location.getDistrict());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            sb.append("\nsdk version : ");
            sb.append(mLocationClient.getVersion());
            sb.append("\nisCellChangeFlag : ");
            sb.append(location.isCellChangeFlag());
            CommonUtil.showMessages(getApplicationContext(),sb.toString());
            Log.i(this.getClass().getName(), sb.toString());
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("Poi time : ");
            sb.append(poiLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(poiLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(poiLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(poiLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(poiLocation.getRadius());
            if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(poiLocation.getAddrStr());
            }
            if (poiLocation.hasPoi()) {
                sb.append("\nPoi:");
                sb.append(poiLocation.getPoi());
            } else {
                sb.append("noPoi information");
            }
            CommonUtil.showMessages(getApplicationContext(),sb.toString());
            Log.i(this.getClass().getName(), sb.toString());
        }
    }


    private void initPage() {


        if (UserUtil.getUserId() >= 0) {
            btnLogin.setAlpha(0);
            UserService userService = new UserService(this.getHelper());
            UserBase userBase = userService.fetchUserById(UserUtil.getUserId());
            TextView lblNickName = (TextView) findViewById(R.id.mianLblNickName);
            lblNickName.setText(userBase.getNickName());
            TextView lblLevelNum = (TextView) findViewById(R.id.mianLblLevelNum);
            lblLevelNum.setText(userBase.getUserInfo().getLevel().toString());
            TextView lblCoinNum = (TextView) findViewById(R.id.mianLblCoinNum);
            lblCoinNum.setText(userBase.getUserInfo().getScores().toString());
        } else {
            btnLogin.setAlpha(1);
            btnLogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        mIsStart = false;
        btnTraffic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsStart) {
                    setLocationOption();
                    mLocationClient.start();
                    mLocationClient.requestLocation();
                    mIsStart = true;

                } else {
                    mLocationClient.stop();
                    mIsStart = false;
                }
            }
        });

        btnRun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShareSNSActivity.class);
                startActivity(intent);
            }
        });

        btnChallengeRun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChallengeMainActivity.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RunHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);                //打开gps

        option.setScanSpan(3000);
        option.setPriority(LocationClientOption.GpsFirst);        //不设置，默认是gps优先

        option.setPoiNumber(10);
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //todo::do nothing now
    }


    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }
}
