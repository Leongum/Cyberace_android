package com.G5432.Cyberace.Run;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-1
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class RunInfoActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runinfopage);
        btnReturn = (Button) findViewById(R.id.aboutusBtnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
