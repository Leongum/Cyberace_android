package com.G5432.Cyberace.ShareSNS;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.RunningHistory;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Service.SystemService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.ToastUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-31
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class ShareSNSActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnSubmit;
    private Button btnShareSina;
    private Button btnShareTencent;
    private Button btnShareRenren;
    private EditText txtContent;
    private List<String> platforms = new ArrayList<String>();
    private SystemService systemService;
    private RunningHistoryService runningHistoryService= null;
    RunningHistory runningHistory = null;
    String imagePath = "";
    String runUuid = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        imagePath = extras.getString("imagePath");
        runUuid = extras.getString("runUuid");
        setContentView(R.layout.sharesns);
        ShareSDK.initSDK(this);
        btnReturn = (Button) findViewById(R.id.sharesnsBtnReturn);
        btnSubmit = (Button) findViewById(R.id.sharesnsBtnSubmit);
        btnShareSina = (Button) findViewById(R.id.sharesnsBtnSina);
        btnShareTencent = (Button) findViewById(R.id.sharesnsBtnTencent);
        btnShareRenren = (Button) findViewById(R.id.sharesnsBtnRenren);
        txtContent = (EditText) findViewById(R.id.sharesnsTxtContent);
        systemService = new SystemService(getHelper());
        runningHistoryService = new RunningHistoryService(getHelper());
        runningHistory = runningHistoryService.fetchRunHistoryByRunId(runUuid);

        initPage();
    }

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnShareSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnShareSina.getAlpha() != 1) {
                    checkAuthStatus(SinaWeibo.NAME);
                } else {
                    btnShareSina.setAlpha((float) 0.7);
                    platforms.remove(SinaWeibo.NAME);
                }
            }
        });


        btnShareTencent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnShareTencent.getAlpha() != 1) {
                    checkAuthStatus(TencentWeibo.NAME);
                } else {
                    btnShareTencent.setAlpha((float) 0.7);
                    platforms.remove(TencentWeibo.NAME);
                }
            }
        });

        btnShareRenren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnShareRenren.getAlpha() != 1) {
                    checkAuthStatus(Renren.NAME);
                } else {
                    btnShareRenren.setAlpha((float) 0.7);
                    platforms.remove(Renren.NAME);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(platforms.size() == 0){
                    ToastUtil.showMessage(getApplicationContext(), systemService.getSystemMessage(Constant.SELECT_SHARE_PLATFORM_ERROR));
                    return;
                }
                String shareContent = systemService.getSystemMessage(Constant.SHARE_DEFAULT_CONTENT);
                shareContent = shareContent.replaceFirst("%@",CommonUtil.transDistanceToStandardFormat(runningHistory.getDistance())).replaceFirst("%@",CommonUtil.transSecondToStandardFormat(runningHistory.getDuration())).replaceFirst("%@",String.format("%.1f kca",runningHistory.getSpendCarlorie()));
                if(txtContent.getText()!= null && txtContent.getText().toString().trim()!= "") {
                    shareContent = "『"+txtContent.getText().toString()+"』 " + shareContent;
                }
                for (String platformName : platforms) {
                    Platform platform = ShareSDK.getPlatform(getApplication(), platformName);
                    if (platform.getName().equalsIgnoreCase(SinaWeibo.NAME)) {
                        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                        sp.text = shareContent;
                        sp.imagePath = imagePath;
                        platform.share(sp);
                    } else if (platform.getName().equalsIgnoreCase(TencentWeibo.NAME)) {
                        TencentWeibo.ShareParams sp = new TencentWeibo.ShareParams();
                        sp.text = shareContent;
                        sp.imagePath = imagePath;
                        platform.share(sp);
                    } else if (platform.getName().equalsIgnoreCase(Renren.NAME)) {
                        String url = "https://api.renren.com/v2/photo/upload";
                        String method = "POST";
                        short customerAction = 4;
                        HashMap<String, Object> values = new HashMap<String, Object>();
                        values.put("description", shareContent);
                        HashMap<String, String> filePathes = new HashMap<String, String>();
                        filePathes.put("file", imagePath);
                        platform.customerProtocol(url, method, customerAction, values, filePathes);
                    }
                    ToastUtil.showMessage(getApplicationContext(),systemService.getSystemMessage(Constant.SHARE_SUBMITTED));
                    finish();
                    Intent intent = new Intent(ShareSNSActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkAuthStatus(String platformName) {
        Platform platform = ShareSDK.getPlatform(getApplication(), platformName);
        platform.setPlatformActionListener(platformActionListener);
        if (platform.isValid()) {
            if (platformName.equalsIgnoreCase(SinaWeibo.NAME)) {
                btnShareSina.setAlpha(1);
            } else if (platformName.equalsIgnoreCase(TencentWeibo.NAME)) {
                btnShareTencent.setAlpha(1);
            } else if (platformName.equalsIgnoreCase(Renren.NAME)) {
                btnShareRenren.setAlpha(1);
            }
            platforms.add(platformName);
        } else {
            platform.setPlatformActionListener(platformActionListener);
            platform.authorize();
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
                    checkAuthStatus(plat.getName());
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
