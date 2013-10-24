package com.G5432.Cyberace.History;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-24
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryTotalActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historytotal);
        btnReturn = (Button) findViewById(R.id.historytotalBtnReturn);
        initPage();

    }

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
