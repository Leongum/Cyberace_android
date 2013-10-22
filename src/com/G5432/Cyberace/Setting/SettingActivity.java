package com.G5432.Cyberace.Setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-22
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class SettingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnFeedBack;
    private TextView txtNickName;
    private TableRow tblRowIndex1;
    private TableRow tblRowIndex2;
    private TableRow tblRowIndex5;
    private Button btnSpeedKMPerH;
    private Button btnSpeedMimPerKM;
    private CheckBox checkWifi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        btnReturn = (Button) findViewById(R.id.settingBtnReturn);
        txtNickName = (TextView) findViewById(R.id.settingTxtNickName);
        btnFeedBack = (Button) findViewById(R.id.settingBtnFeedback);
        tblRowIndex1 = (TableRow) findViewById(R.id.settingTblIndex1);
        tblRowIndex2 = (TableRow) findViewById(R.id.settingTblIndex2);
        tblRowIndex5 = (TableRow) findViewById(R.id.settingTblIndex5);
        btnSpeedKMPerH = (Button) findViewById(R.id.settingBtnSpeedKMperH);
        btnSpeedMimPerKM = (Button) findViewById(R.id.settingBtnSpeedMinPerKM);
        checkWifi = (CheckBox) findViewById(R.id.settingCheckSyncMode);
        initPage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private CompoundButton.OnCheckedChangeListener onlyWifiListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                UserUtil.setSyncMode(Constant.SYNC_MODE_WIFI_ONLY);
            } else {
                UserUtil.setSyncMode(Constant.SYNC_MODE_ALL_MODE);
            }
        }
    };

    private void initPage() {
        final Drawable drawableBg = getResources().getDrawable(R.drawable.seg_selected_bg);
        if (UserUtil.getUserId() > 0) {
            txtNickName.setText(UserUtil.getUserNickName());
        }

        if(UserUtil.getSyncMode() == Constant.SYNC_MODE_WIFI_ONLY) {
            checkWifi.setChecked(true);
        }

        if(UserUtil.getSpeedFormat() == Constant.SPEED_FORMAT_MIN_PER_KM){
            btnSpeedMimPerKM.setBackground(drawableBg);
            btnSpeedKMPerH.setBackground(null);
        }

        btnSpeedKMPerH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSpeedMimPerKM.setBackground(null);
                btnSpeedKMPerH.setBackground(drawableBg);
                UserUtil.setSpeedFormat(Constant.SPEED_FORMAT_KM_PER_HOUR);
            }
        });

        btnSpeedMimPerKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSpeedKMPerH.setBackground(null);
                btnSpeedMimPerKM.setBackground(drawableBg);
                UserUtil.setSpeedFormat(Constant.SPEED_FORMAT_MIN_PER_KM);
            }
        });

        checkWifi.setOnCheckedChangeListener(onlyWifiListener);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tblRowIndex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, BodySettingActivity.class);
                intent.putExtra("NewRegister", false);
                startActivity(intent);
            }
        });

        btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}
