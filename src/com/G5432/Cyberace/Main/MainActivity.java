package com.G5432.Cyberace.Main;

import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.G5432.Cyberace.History.RunHistoryDetailActivity;
import com.G5432.Cyberace.R;
import com.G5432.Cyberace.Run.ChallengeMainActivity;
import com.G5432.Cyberace.Setting.SettingActivity;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.Service.UserService;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-17
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private LocationManager lm;
    private Criteria criteria;
    private Geocoder geocoder;
    private Button btnLogin;
    private Button btnTraffic;
    private Button btnRun;
    private Button btnChallengeRun;
    private Button btnHistory;
    private Button btnSetting;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnLogin = (Button) findViewById(R.id.mianBtnLogin);
        btnTraffic = (Button) findViewById(R.id.mianBtnTraffic);
        btnRun = (Button) findViewById(R.id.mianBtnRun);
        btnChallengeRun = (Button) findViewById(R.id.mianBtnChallengeRun);
        btnHistory = (Button) findViewById(R.id.mianBtnHistory);
        btnSetting = (Button) findViewById(R.id.mianBtnSetting);

        initPage();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        if (!lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
        }
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);    // 设置精确度
        criteria.setAltitudeRequired(false);                // 设置请求海拔
        criteria.setBearingRequired(false);                // 设置请求方位
        criteria.setCostAllowed(true);                    // 设置允许运营商收费
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = lm.getBestProvider(criteria, true);
        Location location = lm.getLastKnownLocation(provider);
        newLocalGPS(location);

        LocationListener listener = new locationListener();
        lm.requestLocationUpdates(provider, 1000, 0, listener);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //todo::do nothing now
    }


    private void newLocalGPS(Location location) {
        if (location != null) {
            double latitude = location.getLatitude(); //经度
            double longitude = location.getLongitude(); // 纬度
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    //todo::add weather info
                    //address.getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            // 未获取地理信息位置
            Log.e(this.getClass().getName(), "地理信息位置未知或正在获取地理信息位置中...");
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

        btnTraffic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:: add onclick
            }
        });

        btnRun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:: add onclick
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
                Intent intent = new Intent(MainActivity.this, RunHistoryDetailActivity.class);
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

    public class locationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if (location.getTime() <= (new Date()).getTime() + 2000) {
                newLocalGPS(location);
                lm.removeUpdates(this);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            newLocalGPS(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }
}
