package com.G5432.Cyberace.Main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.G5432.Cyberace.R;
import com.G5432.Cyberace.Setting.BodySettingActivity;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.Entity.UserInfo;
import com.G5432.HttpClient.BackendSyncRequest;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Service.SystemService;
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
 * Date: 13-10-18
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnLogin;
    private Button btnRegister;
    private Button btnSubmit;
    private Button btnReturn;
    private CheckBox checkShowPassword;
    private TextView txtPassword;
    private TextView txtNickName;
    private TextView txtEmail;
    private SystemService systemService;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLogin = (Button) findViewById(R.id.loginBtnLogin);
        btnRegister = (Button) findViewById(R.id.loginBtnResigter);
        btnSubmit = (Button) findViewById(R.id.loginBtnSubmit);
        btnReturn = (Button) findViewById(R.id.loginBtnReturn);
        checkShowPassword = (CheckBox) findViewById(R.id.loginCheckShowPwd);
        txtPassword = (TextView) findViewById(R.id.loginTxtPassword);
        txtEmail = (TextView) findViewById(R.id.loginTxtUserEmail);
        txtNickName = (TextView) findViewById(R.id.loginTxtNickName);
        systemService = new SystemService(getHelper());
        initPage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private CompoundButton.OnCheckedChangeListener showPasswordListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        }
    };

    private OnClickListener returnListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    private OnClickListener registerSubmitListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (txtEmail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty() || txtNickName.getText().toString().isEmpty()) {
                String message = systemService.getSystemMessage(Constant.REGISTER_INPUT_CHECK);
                CommonUtil.showMessages(getApplicationContext(), message);
                return;
            }
            if (!txtEmail.getText().toString().contains("@") || !txtEmail.getText().toString().contains(".")) {
                CommonUtil.showMessages(getApplicationContext(), "请填写正确的邮箱");
                return;
            }
            if (txtPassword.getText().toString().length() < 6) {
                CommonUtil.showMessages(getApplicationContext(), "密码太短");
                return;
            }
            HttpClientHelper httpClientHelper = new HttpClientHelper();
            UserBase userBase = new UserBase();
            userBase.setNickName(txtNickName.getText().toString());
            userBase.setUserEmail(txtEmail.getText().toString());
            userBase.setPassword(CommonUtil.getMD5(txtPassword.getText().toString()));
            String requestBody = gson.toJson(userBase, UserBase.class);
            httpClientHelper.post(CommonUtil.getUrl(Constant.REGISTER_URL), null, requestBody, new AsyncHttpResponseHandler() {
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
                        Intent intent = new Intent(LoginActivity.this, BodySettingActivity.class);
                        intent.putExtra("NewRegister", true);
                        startActivity(intent);
                    } else {
                        Log.e(this.getClass().getName(), response);
                        String message = systemService.getSystemMessage(Constant.REGISTER_FAIL);
                        CommonUtil.showMessages(getApplicationContext(), message);
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    String message = systemService.getSystemMessage(Constant.REGISTER_FAIL);
                    CommonUtil.showMessages(getApplicationContext(), message);
                    Log.e(this.getClass().getName(), error.getMessage() + content);
                }
            });
        }
    };


    private OnClickListener loginSubmitLister = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (txtEmail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()) {
                String message = systemService.getSystemMessage(Constant.LOGIN_INPUT_CHECK);
                CommonUtil.showMessages(getApplicationContext(), message);
                return;
            }
            HttpClientHelper httpClientHelper = new HttpClientHelper();
            String url = MessageFormat.format(Constant.LOGIN_URL, txtEmail.getText().toString(), CommonUtil.getMD5(txtPassword.getText().toString()));
            httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
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
                        BackendSyncRequest backendSyncRequest = new BackendSyncRequest(getHelper());
                        backendSyncRequest.syncRunningHistories();
                        backendSyncRequest.uploadRunningHistories();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e(this.getClass().getName(), response);
                        String message = systemService.getSystemMessage(Constant.LOGIN_ERROR);
                        CommonUtil.showMessages(getApplicationContext(), message);
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    String message = systemService.getSystemMessage(Constant.LOGIN_ERROR);
                    CommonUtil.showMessages(getApplicationContext(), message);
                    Log.e(this.getClass().getName(), error.getMessage() + content);
                }
            });
        }
    };

    private OnClickListener registerListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            txtNickName.setAlpha(1);
            Resources resources = getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.seg_selected_bg);
            btnRegister.setBackground(btnDrawable);
            btnLogin.setBackground(null);
            btnSubmit.setOnClickListener(registerSubmitListener);
        }
    };

    private OnClickListener loginListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            txtNickName.setAlpha(0);
            Resources resources = getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.seg_selected_bg);
            btnLogin.setBackground(btnDrawable);
            btnRegister.setBackground(null);
            btnSubmit.setOnClickListener(loginSubmitLister);
        }
    };

    private void initPage() {
        checkShowPassword.setOnCheckedChangeListener(showPasswordListener);
        btnReturn.setOnClickListener(returnListener);
        btnSubmit.setOnClickListener(loginSubmitLister);
        btnRegister.setOnClickListener(registerListener);
        btnLogin.setOnClickListener(loginListener);
    }
}
