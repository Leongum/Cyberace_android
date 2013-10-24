package com.G5432.Cyberace.History;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-23
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class RunHistoryActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runhistory);
        btnReturn = (Button) findViewById(R.id.aboutusBtnReturn);
        ViewFlipper viewFlipper = (ViewFlipper) this.findViewById(R.id.aboutusBtnReturn);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
