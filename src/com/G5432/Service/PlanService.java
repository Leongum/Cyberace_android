package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.j256.ormlite.dao.Dao;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class PlanService {

    private Dao<Plan, Integer> planDap = null;

    private Dao<PlanNextMission, Void> planNextMissionDao = null;

    private Dao<PlanUserFollow, Void> planUserFollowDao = null;

    private Dao<PlanRunHistory, String> planRunHistoryDao = null;

    private Dao<PlanCollect, Void> planCollectDao = null;

    public PlanService(DatabaseHelper helper) {
        try {
            planDap = helper.getPlanDao();
            planNextMissionDao = helper.getPlanNextMissionDao();
            planUserFollowDao = helper.getPlanUserFollowDao();
            planCollectDao = helper.getPlanCollectDao();
            planRunHistoryDao = helper.getPlanRunHistoryDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
