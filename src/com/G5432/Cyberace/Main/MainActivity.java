package com.G5432.Cyberace.Main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import com.G5432.Utils.BMapApplication;
import com.G5432.Cyberace.History.RunHistoryActivity;
import com.G5432.Cyberace.R;
import com.G5432.Cyberace.Run.ChallengeMainActivity;
import com.G5432.Cyberace.Run.RunInfoActivity;
import com.G5432.Cyberace.Setting.SettingActivity;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Service.UserService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.baidu.location.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

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

    public MyLocationListener myListener = new MyLocationListener();
    private BMapApplication bMapApp = null;

    private String cityName;
    private String districtName;
    private String weatherInformation = "天气信息获取中...";
    private Integer temp = Integer.MIN_VALUE;
    private String wd = "";
    private String ws = "";
    private Integer pm25 = Integer.MAX_VALUE;
    private String pm25Quality = "";
    private Integer weatherStatus = 0; //0 获取中 －1天气获取失败。

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init location params
        bMapApp = (BMapApplication)this.getApplication();
        bMapApp.mLocationClient.registerLocationListener(myListener);
        bMapApp.setWeatherLocationOption();
        bMapApp.mLocationClient.start();
        bMapApp.mLocationClient.requestLocation();

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

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                startTimerChecker();
                cityName = location.getCity();
                districtName = location.getDistrict();
                if (cityName != null || districtName != null) {
                    if (bMapApp.mLocationClient.isStarted()) bMapApp.mLocationClient.stop();
                    HttpClientHelper httpClientHelper = new HttpClientHelper();
                    String url = MessageFormat.format(Constant.WEATHER_URL, CommonUtil.getCityCode(cityName, districtName));
                    httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String response) {
                            Log.d(this.getClass().getName(), response);
                            if (statusCode == 200 || statusCode == 204) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    jsonObject = jsonObject.getJSONObject("weatherinfo");
                                    temp = jsonObject.getInt("temp");
                                    wd = jsonObject.getString("WD");
                                    ws = jsonObject.getString("WS");
                                } catch (JSONException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                            } else {
                                weatherStatus = -1;
                                Log.e(this.getClass().getName(), response);
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            weatherStatus = -1;
                            Log.e(this.getClass().getName(), error.getMessage() + content);
                        }
                    });
                    url = MessageFormat.format(Constant.PM25_URL, districtName, cityName);
                    httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String response) {
                            Log.d(this.getClass().getName(), response);
                            if (statusCode == 200 || statusCode == 204) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    pm25 = jsonObject.getInt("pm2_5");
                                    pm25Quality = jsonObject.getString("quality");
                                } catch (JSONException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                            } else {
                                weatherStatus = -1;
                                Log.e(this.getClass().getName(), response);
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            weatherStatus = -1;
                            Log.e(this.getClass().getName(), error.getMessage() + content);
                        }
                    });
                }
            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
            //do nothing
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
                if (temp != Integer.MIN_VALUE && temp != Integer.MAX_VALUE) {
                    int index = -1;
                    if (temp < 38 && pm25 < 300) {
                        index = (int) ((100 - pm25 / 3) * 0.6 + (100 - Math.abs(temp - 22) * 5) * 0.4);
                    } else {
                        index = 0;
                    }
                    weatherInformation = MessageFormat.format("{0}  {1}℃  {2}{3}  PM2.5: {4}{5}  总: {6}", districtName, temp, wd, ws, pm25, pm25Quality, index);
                    if (index > 75) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_green);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (temp < 0 || temp > 38 || pm25 > 250 || index < 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_red);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (index <= 75 && index >= 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_yellow);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    }
                }
            }
        }
    };

    private void startTimerChecker() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            int time = 0;
            @Override
            public void run() {
                Log.d(this.getClass().getName(), "begin time task check");

                // 定义一个消息传过去
                Message msg = new Message();
                if (weatherStatus == -1) {
                    msg.what = -1;
                }
                if (temp != Integer.MIN_VALUE && temp != Integer.MAX_VALUE) {
                    msg.what = 1;
                } else {
                    msg.what = -1;
                }
                if(time == 10){
                    msg.what = 1;
                }
                time ++;
                handler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 3000, 1000);
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
                if (weatherStatus == -1) {
                    ToastUtil.showMessage(getApplicationContext(), "天气信息获取失败");
                }
                if (temp != Integer.MIN_VALUE && temp != Integer.MAX_VALUE) {
                    int index = -1;
                    if (temp < 38 && pm25 < 300) {
                        index = (int) ((100 - pm25 / 3) * 0.6 + (100 - Math.abs(temp - 22) * 5) * 0.4);
                    } else {
                        index = 0;
                    }
                    weatherInformation = MessageFormat.format("{0}  {1}℃  {2}{3}  PM2.5: {4}{5}  总: {6}", districtName, temp, wd, ws, pm25, pm25Quality, index);
                    if (index > 75) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_green);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (temp < 0 || temp > 38 || pm25 > 250 || index < 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_red);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (index <= 75 && index >= 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_yellow);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    }
                    ToastUtil.showMessageLong(getApplicationContext(), weatherInformation);
                } else {
                    ToastUtil.showMessageLong(getApplicationContext(), weatherInformation);
                }
            }
        });

        btnRun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RunInfoActivity.class);
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
