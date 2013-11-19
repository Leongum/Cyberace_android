package com.G5432.Cyberace.Setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-22
 * Time: 下午11:12
 * To change this template use File | Settings | File Templates.
 */
public class AccountSettingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnLogout;
    private TextView txtSina;
    private TextView txtRenren;
    private TextView txtTencent;
    private CheckBox cheSina;
    private CheckBox cheRenren;
    private CheckBox cheTencent;

    final private String UNAUTHED_STRING = "尚未授权";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsetting);
        ShareSDK.initSDK(this);
        btnReturn = (Button) findViewById(R.id.accountsettingBtnReturn);
        btnLogout = (Button) findViewById(R.id.accountsettingBtnLogout);
        txtSina = (TextView) findViewById(R.id.accountsettingTxtSina);
        txtRenren = (TextView) findViewById(R.id.accountsettingTxtRenren);
        txtTencent = (TextView) findViewById(R.id.accountsettingTxtTencent);
        cheSina = (CheckBox) findViewById(R.id.accountsettingCheSina);
        cheRenren = (CheckBox) findViewById(R.id.accountsettingCheRenren);
        cheTencent = (CheckBox) findViewById(R.id.accountsettingCheTencent);
        initPage();
    }

    private CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                Platform plat = null;
                if (compoundButton.getId() == R.id.accountsettingCheSina) {
                    plat = ShareSDK.getPlatform(getApplication(), SinaWeibo.NAME);
                } else if (compoundButton.getId() == R.id.accountsettingCheTencent) {
                    plat = ShareSDK.getPlatform(getApplication(), TencentWeibo.NAME);
                } else if (compoundButton.getId() == R.id.accountsettingCheRenren) {
                    plat = ShareSDK.getPlatform(getApplication(), Renren.NAME);
                }
                plat.setPlatformActionListener(platformActionListener);
                plat.authorize();
            } else {
                Platform plat = null;
                if (compoundButton.getId() == R.id.accountsettingCheSina) {
                    plat = ShareSDK.getPlatform(getApplication(), SinaWeibo.NAME);
                } else if (compoundButton.getId() == R.id.accountsettingCheTencent) {
                    plat = ShareSDK.getPlatform(getApplication(), TencentWeibo.NAME);
                } else if (compoundButton.getId() == R.id.accountsettingCheRenren) {
                    plat = ShareSDK.getPlatform(getApplication(), Renren.NAME);
                }
                plat.removeAccount();
                updateAuthInfo(plat.getName());
            }
        }
    };

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(this);

        logoutBuilder.setMessage("确定要注销吗？");

        logoutBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserUtil.logout();
                Intent intent = new Intent(AccountSettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logoutBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad = logoutBuilder.create();
                ad.show();
            }
        });

        updateAuthInfo(SinaWeibo.NAME);
        updateAuthInfo(TencentWeibo.NAME);
        updateAuthInfo(Renren.NAME);

        cheSina.setOnCheckedChangeListener(checkBoxListener);
        cheTencent.setOnCheckedChangeListener(checkBoxListener);
        cheRenren.setOnCheckedChangeListener(checkBoxListener);
    }

    private void updateAuthInfo(String platformName) {
        Platform platform = ShareSDK.getPlatform(this, platformName);
        String userName = platform.getDb().getUserName();
        if (userName == null || userName.length() <= 0
                || "null".equals(userName)) {
            // 如果平台已经授权却没有拿到帐号名称，则自动获取用户资料，以获取名称
            platform.showUser(null);
        }
        if (platform.getName().equalsIgnoreCase(SinaWeibo.NAME)) {
            if (platform.isValid()) {
                txtSina.setText(userName);
                cheSina.setChecked(true);
            } else {
                cheSina.setChecked(false);
                txtSina.setText(UNAUTHED_STRING);
            }
        } else if (platform.getName().equalsIgnoreCase(TencentWeibo.NAME)) {
            if (platform.isValid()) {
                txtTencent.setText(userName);
                cheTencent.setChecked(true);
            } else {
                cheTencent.setChecked(false);
                txtTencent.setText(UNAUTHED_STRING);
            }
        } else if (platform.getName().equalsIgnoreCase(Renren.NAME)) {
            if (platform.isValid()) {
                txtRenren.setText(userName);
                cheRenren.setChecked(true);
            } else {
                cheRenren.setChecked(false);
                txtRenren.setText(UNAUTHED_STRING);
            }
        }
    }

    // 定义Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Platform plat = (Platform) msg.obj;
            switch (msg.arg1) {
                case 1: {
                    // 成功
                    updateAuthInfo(plat.getName());
                }
                break;
                case 2: {
                    // 失败
                    ToastUtil.showMessage(getApplicationContext(), "呃。授权失败了～");
                }
                case 3: {
                    // 取消
                    ToastUtil.showMessage(getApplicationContext(), "取消了授权？");
                }
            }
        }
    };

    PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> stringObjectHashMap) {
            Message msg = new Message();
            msg.arg1 = 1;
            msg.obj = platform;
            handler.sendMessage(msg);
        }

        @Override
        public void onError(Platform platform, int action, Throwable throwable) {
            Message msg = new Message();
            msg.arg1 = 2;
            msg.obj = platform;
            handler.sendMessage(msg);
        }

        @Override
        public void onCancel(Platform platform, int action) {
            Message msg = new Message();
            msg.arg1 = 3;
            msg.obj = platform;
            handler.sendMessage(msg);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
