package com.G5432.Cyberace.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.Feedback;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-22
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;
    private Button btnSubmit;
    private EditText txtContent;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        btnReturn = (Button) findViewById(R.id.feedbackBtnReturn);
        btnSubmit = (Button) findViewById(R.id.feedbackBtnSubmit);
        txtContent = (EditText) findViewById(R.id.feedbackTxtContent);
        initPage();
    }

    private void initPage() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtContent.getText().toString().isEmpty()) {
                    HttpClientHelper httpClientHelper = new HttpClientHelper();
                    Feedback feedback = new Feedback();
                    feedback.setSuggestion(txtContent.getText().toString());
                    feedback.setUserId(UserUtil.getUserId());
                    String requestBody = gson.toJson(feedback, Feedback.class);
                    httpClientHelper.post(CommonUtil.getUrl(Constant.FEEDBACK_URL), null, requestBody, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String response) {
                        }
                    });
                    finish();
                    Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
