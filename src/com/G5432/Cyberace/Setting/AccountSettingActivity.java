package com.G5432.Cyberace.Setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.G5432.Cyberace.Main.MainActivity;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsetting);
        btnReturn = (Button) findViewById(R.id.accountsettingBtnReturn);
        btnLogout = (Button) findViewById(R.id.accountsettingBtnLogout);
        initPage();
    }

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
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
