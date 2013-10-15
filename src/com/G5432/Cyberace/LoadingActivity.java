package com.G5432.Cyberace;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class LoadingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        UserUtil.sharedPreferences = getSharedPreferences("UserBaseInfo",MODE_APPEND);
        doSyncData();
    }

    private void doSyncData() {
        HttpClientHelper httpClientHelper = new HttpClientHelper();
        httpClientHelper.get(Constant.VERSION_URL, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if(statusCode == 200 || statusCode == 204){

                }
            }
            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

//    [RORNetWorkUtils initCheckNetWork];
//    NSLog(@"%hhd",[RORNetWorkUtils getIsConnetioned]);
//
//    // Do any additional setup after loading the view.
//    //sync version
//    Version_Control *version = [RORSystemService syncVersion:@"ios"];
//    if(version != nil){
//        NSString *missionLastUpdateTime = [RORUserUtils getLastUpdateTime:@"MissionUpdateTime"];
//        NSString *messageLastUpdateTime = [RORUserUtils getLastUpdateTime:@"SystemMessageUpdateTime"];
//        NSTimeInterval messageScape = [version.messageLastUpdateTime timeIntervalSinceDate:[RORUtils getDateFromString:messageLastUpdateTime]];
//        NSTimeInterval missionScape = [version.missionLastUpdateTime timeIntervalSinceDate:[RORUtils getDateFromString:missionLastUpdateTime]];
//        if(messageScape > 0){
//            //sync message
//            [RORSystemService syncSystemMessage];
//        }
//        if(missionScape > 0)
//        {
//            //sync missions
//            [RORMissionServices syncMissions];
//        }
//    }
//    //sync user
//    NSNumber *userId = [RORUserUtils getUserId];
//    if([userId intValue] > 0){
//        //sync runningHistory
//        [RORRunHistoryServices syncRunningHistories];
//        [RORRunHistoryServices uploadRunningHistories];
//        //sync userInfo.
//        [RORUserServices syncUserInfoById:userId];
//    }

}
