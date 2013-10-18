package com.G5432.Cyberace;

import android.os.Bundle;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-18
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
}
