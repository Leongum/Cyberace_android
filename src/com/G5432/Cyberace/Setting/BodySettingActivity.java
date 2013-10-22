package com.G5432.Cyberace.Setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.G5432.Cyberace.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.Entity.UserInfo;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Service.UserService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-21
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
public class BodySettingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private boolean newRegister = false;

    private Button btnReturn;
    private Button btnSubmit;
    private TextView txtHeight;
    private TextView txtWeight;
    private Button btnMan;
    private Button btnWoman;
    private Button btnUnknow;

    private String sex = "男";
    private float height = 175;
    private float weight = 65;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        newRegister = extras.getBoolean("NewRegister");
        setContentView(R.layout.bodysetting);
        btnReturn = (Button) findViewById(R.id.bodyBtnReturn);
        btnSubmit = (Button) findViewById(R.id.bodyBtnSubmit);
        txtHeight = (TextView) findViewById(R.id.bodyTxtHeight);
        txtWeight = (TextView) findViewById(R.id.bodyTxtWeight);
        btnMan = (Button) findViewById(R.id.bodyBtnMan);
        btnWoman = (Button) findViewById(R.id.bodyBtnWoman);
        btnUnknow = (Button) findViewById(R.id.bodyBtnUnknow);
        if (newRegister) {
            btnReturn.setAlpha(0);
        }
        initPage();
    }

    private void initPage() {
        sex = UserUtil.getUserSex();
        showSex(sex);
        height = UserUtil.getUserHeight();
        txtHeight.setText(String.valueOf(height));
        weight = UserUtil.getUserWeight();
        txtWeight.setText(String.valueOf(weight));

        btnMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "男";
                showSex(sex);
            }
        });

        btnWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "女";
                showSex(sex);
            }
        });

        btnUnknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "未知";
                showSex(sex);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double newHeight = 0;
                double newWeight = 0;
                try {
                    newWeight = Double.valueOf(txtWeight.getText().toString()).doubleValue();
                    newHeight =    Double.valueOf(txtHeight.getText().toString()).doubleValue();
                } catch (Exception ex) {

                }
                if (newWeight < 30 || newWeight > 150) {
                    CommonUtil.showMessages(getApplicationContext(), "体重输入有问题");
                    txtWeight.setText(String.valueOf(weight));
                    return;
                }

                if (newHeight < 120 || newHeight > 250) {
                    CommonUtil.showMessages(getApplicationContext(), "身高输入有问题");
                    return;
                }

                UserBase userBase = new UserBase();
                userBase.setSex(sex);
                userBase.setHeight(newHeight);
                userBase.setWeight(newWeight);
                UserUtil.saveBodySetting(userBase);
                if (UserUtil.getUserId() > 0) {
                    HttpClientHelper httpClientHelper = new HttpClientHelper();
                    String requestBody = gson.toJson(userBase, UserBase.class);
                    String url = MessageFormat.format(Constant.USER_ADDITIONAL_UPDATE, UserUtil.getUserId());
                    httpClientHelper.put(CommonUtil.getUrl(url), null, requestBody, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String response) {
                            Log.d(this.getClass().getName(), response);
                            if (statusCode == 200 || statusCode == 204) {
                                UserService userService = new UserService(getHelper());
                                UserBase userBase = gson.fromJson(response, UserBase.class);
                                UserInfo userInfo = gson.fromJson(response, UserInfo.class);
                                userBase.setUserInfo(userInfo);
                                userService.saveUserInfoToDB(userBase);
                                UserUtil.saveUserInfoToList(userBase);
                                Intent intent = new Intent(BodySettingActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.e(this.getClass().getName(), response);
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            Log.e(this.getClass().getName(), error.getMessage() + content);
                        }
                    });
                } else {
                    Intent intent = new Intent(BodySettingActivity.this, MainActivity.class);
                    startActivity(intent);
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

    private void showSex(String sex) {
        final Drawable drawableBg = getResources().getDrawable(R.drawable.seg_selected_bg);
        if (sex == "男") {
            btnWoman.setBackground(null);
            btnUnknow.setBackground(null);
            btnMan.setBackground(drawableBg);
        } else if (sex == "女") {
            btnMan.setBackground(null);
            btnUnknow.setBackground(null);
            btnWoman.setBackground(drawableBg);
        } else {
            btnMan.setBackground(null);
            btnUnknow.setBackground(drawableBg);
            btnWoman.setBackground(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (newRegister) {
            //do nothing
        } else {
            super.onBackPressed();
        }
    }
}
