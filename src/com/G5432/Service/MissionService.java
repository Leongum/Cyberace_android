package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Enums.MissionType;
import com.G5432.HttpClient.HttpClientHelper;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class MissionService {

    private Dao<Mission, Integer> missionDao = null;

    private Dao<MissionChallenge, Void> missionChallengeDao = null;

    private Dao<MissionPlacePackage, Void> missionPlacePackageDao = null;

    public MissionService(DatabaseHelper helper) {
        try {
            missionDao = helper.getMissionDao();
            missionChallengeDao = helper.getMissionChallengeDao();
            missionPlacePackageDao = helper.getMissionPlacePackageDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveMissionToDB(List<Mission> missionList) {
        try {
            for (Mission mission : missionList) {
                missionDao.createOrUpdate(mission);
                if (mission.getMissionTypeId() == MissionType.Challenge.ordinal() && mission.getChallengeId() != null) {
                    DeleteBuilder<MissionChallenge, Void> missionChallengeDeleteBuilder = missionChallengeDao.deleteBuilder();
                    missionChallengeDeleteBuilder.where().eq("challengeId", mission.getChallengeId());
                    missionChallengeDeleteBuilder.delete();
                    for (MissionChallenge missionChallenge : mission.getMissionChallenges()) {
                        missionChallengeDao.create(missionChallenge);
                    }
                } else if (mission.getMissionTypeId() == MissionType.Cycle.ordinal()) {
                    //todo:: under design
                } else if (mission.getMissionTypeId() == MissionType.Recommand.ordinal() && mission.getMissionPlacePackageId() != null) {
                    DeleteBuilder<MissionPlacePackage, Void> missionPlacePackageDeleteBuilder = missionPlacePackageDao.deleteBuilder();
                    missionPlacePackageDeleteBuilder.where().eq("packageId", mission.getMissionPlacePackageId());
                    missionPlacePackageDeleteBuilder.delete();
                    for (MissionPlacePackage missionPlacePackage : mission.getMissionPlacePackages()) {
                        missionPlacePackageDao.create(missionPlacePackage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Mission fetchMissionByMissionId(Integer missionId) {
        Mission mission = null;
        try {
            mission = missionDao.queryForId(missionId);
            mission = fetchMissionDetails(mission);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return mission;
    }

    public List<Mission> fetchMissionListByType(MissionType missionType) {
        List<Mission> missionsList = null;

        try {
            QueryBuilder<Mission, Integer> missionQueryBuilder = missionDao.queryBuilder();
            missionQueryBuilder.where().eq("missionTypeId", missionType.ordinal());
            missionQueryBuilder.orderBy("missionId", true);
            missionsList = missionQueryBuilder.query();
            for (Mission mission : missionsList) {
                mission = fetchMissionDetails(mission);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return missionsList;
    }

    public Mission fetchMissionDetails(Mission mission) {
        try {
            if (mission != null) {

                if (mission.getMissionTypeId() == MissionType.Challenge.ordinal() && mission.getChallengeId() != null) {
                    List<MissionChallenge> missionChallengeList = null;
                    QueryBuilder<MissionChallenge, Void> missionChallengeQueryBuilder = missionChallengeDao.queryBuilder();
                    String sex = "男";
                    if (UserUtil.getUserSex() == "女") {
                        sex = "女";
                    }
                    missionChallengeQueryBuilder.where().eq("challengeId", mission.getChallengeId()).and().eq("sex", sex);
                    missionChallengeQueryBuilder.orderBy("sequence", true);
                    missionChallengeList = missionChallengeQueryBuilder.query();
                    mission.setMissionChallenges(missionChallengeList);
                } else if (mission.getMissionTypeId() == MissionType.Cycle.ordinal()) {
                    //todo:: under design
                } else if (mission.getMissionTypeId() == MissionType.Recommand.ordinal() && mission.getMissionPlacePackageId() != null) {
                    List<MissionPlacePackage> missionPlacePackageList = null;
                    QueryBuilder<MissionPlacePackage, Void> missionPlacePackageQueryBuilder = missionPlacePackageDao.queryBuilder();
                    missionPlacePackageQueryBuilder.where().eq("packageId", mission.getMissionPlacePackageId());
                    missionPlacePackageQueryBuilder.orderBy("sequence", false);
                    missionPlacePackageList = missionPlacePackageQueryBuilder.query();
                    mission.setMissionPlacePackages(missionPlacePackageList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return mission;
    }
}
