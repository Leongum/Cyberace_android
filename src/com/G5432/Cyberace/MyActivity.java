package com.G5432.Cyberace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Service.UserService;
import com.G5432.Utils.Constant;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

public class MyActivity extends OrmLiteBaseActivity<DatabaseHelper> {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = new TextView(this);
        TextView tv2 = new TextView(this);
        doSampleDatabaseStuff(tv, tv2);
    }

    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    /**
     * Do our sample database stuff as an example.
     */
    public void doSampleDatabaseStuff(TextView tv, TextView tv2) {

        UserService userService = new UserService(this.getHelper());

        //userService.syncUserInfoByLogin("leon@qq.com", "e10adc3949ba59abbe56e057f20f883e");

        StringBuilder sb = new StringBuilder();

        // if we already have items in the database
        UserBase userBase2 = userService.fetchUserById(2);
        sb = new StringBuilder();
        sb.append("login: nickName(").append(userBase2.getNickName())
                .append(") level(").append(userBase2.getUserInfo().getLevel())
                .append(") Uuid(").append(userBase2.getUuid()).append(")");
        tv2.setText(sb.toString());

    }

}
