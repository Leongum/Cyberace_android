package com.G5432.Cyberace;

import android.os.Bundle;
import com.G5432.DBUtils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-17
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


}
