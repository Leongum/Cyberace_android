package com.G5432.Cyberace.Run;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.Cyberace.R;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.Enum.*;
import com.G5432.Entity.Mission;
import com.G5432.Entity.MissionChallenge;
import com.G5432.Entity.RunningHistory;
import com.G5432.Service.MissionService;
import com.G5432.Service.RunningHistoryService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-23
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class ChallengeMainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private Button btnReturn;

    private ListView listChallenge;

    private RelativeLayout layoutHidden;

    private Button btnStart;

    private TextView txtTitle;

    private ListView listGrade;

    private MissionService missionService;

    private RunningHistoryService runningHistoryService;

    private List<Mission> missionList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challengemain);
        missionService = new MissionService(getHelper());
        runningHistoryService = new RunningHistoryService(getHelper());
        btnReturn = (Button) findViewById(R.id.challenegBtnReturn);
        listChallenge = (ListView) findViewById(R.id.challengeListDetail);
        layoutHidden = (RelativeLayout) findViewById(R.id.challengeLayoutDetail);
        layoutHidden.setVisibility(View.GONE);
        btnStart = (Button) findViewById(R.id.challengeBtnStart);
        txtTitle = (TextView) findViewById(R.id.challengeTxtTitle);
        listGrade = (ListView) findViewById(R.id.challengeListMissionGrade);
        missionList = missionService.fetchMissionListByType(MissionTypeEnum.Challenge);
        initPage();
    }

    private void initPage() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (Mission mission : missionList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String sex = "男子";
            if (UserUtil.getUserSex() == "女") {
                sex = "女子";
            }
            map.put("itemSex", sex);
            map.put("itemName", mission.getMissionName());
            RunningHistory best = runningHistoryService.fetchBestRunHistoryByMissionId(mission.getMissionId(), UserUtil.getUserId());
            String grade = "?";
            if (best != null) {
                if (best.getMissionGrade() >= 0 && best.getMissionGrade() <= 6) {
                    grade = MissionGradeEnum.values()[best.getMissionGrade()].toString();
                }
            }
            map.put("itemLevel", grade);
            listItem.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.challengelistview,
                new String[]{"itemSex", "itemName", "itemLevel"},
                new int[]{R.id.challengeTxtSex, R.id.challenegTxtName, R.id.challengeTxtLevel}
        );

        listChallenge.setAdapter(listItemAdapter);

        //添加点击
        listChallenge.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Mission currentMission = missionList.get(arg2);
                layoutHidden.setVisibility(View.VISIBLE);
                txtTitle.setText(currentMission.getMissionName());
                ArrayList<HashMap<String, Object>> gradeListItem = new ArrayList<HashMap<String, Object>>();
                for (MissionChallenge missionChallenge : currentMission.getMissionChallenges()) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("missionLevel", missionChallenge.getGrade());
                    map.put("missionTime", CommonUtil.transSecondToStandardFormat(missionChallenge.getTime()));
                    gradeListItem.add(map);
                }

                SimpleAdapter gradeItemAdapter = new SimpleAdapter(getApplicationContext(), gradeListItem, R.layout.challengemissiongrade,
                        new String[]{"missionLevel", "missionTime"},
                        new int[]{R.id.challengeTxtGradeLevel, R.id.challenegTxtTime}
                );

                listGrade.setAdapter(gradeItemAdapter);
            }
        });

        layoutHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutHidden.setVisibility(View.GONE);
            }
        });


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
