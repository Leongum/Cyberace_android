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
    private Button btnShareWeiXin;
    private EditText txtContent;
    private List<String> platforms = new ArrayList<String>();
    private SystemService systemService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharesns);
        ShareSDK.initSDK(this);
        btnReturn = (Button) findViewById(R.id.sharesnsBtnReturn);
        btnSubmit = (Button) findViewById(R.id.sharesnsBtnSubmit);
        btnShareSina = (Button) findViewById(R.id.sharesnsBtnSina);
        btnShareTencent = (Button) findViewById(R.id.sharesnsBtnTencent);
        btnShareRenren = (Button) findViewById(R.id.sharesnsBtnRenren);
        btnShareWeiXin = (Button) findViewById(R.id.sharesnsBtnWeixin);
        txtContent = (EditText) findViewById(R.id.sharesnsTxtContent);
        systemService = new SystemService(getHelper());
        initPage();
    }

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnShareWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform plat = ShareSDK.getPlatform(getApplication(), WechatMoments.NAME);
                Wechat.ShareParams sp = new Wechat.ShareParams();
                sp.title = "呵呵";
                sp.text = "哈哈";
                sp.shareType = Platform.SHARE_IMAGE;
                sp.imagePath = GetandSaveCurrentImage();
                plat.share(sp);
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
                String imagePath = GetandSaveCurrentImage();
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


    private String GetandSaveCurrentImage() {
        //构建Bitmap
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //获取屏幕
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
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
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
