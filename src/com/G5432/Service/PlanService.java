package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Entity.Enum.CollectStatusEnum;
import com.G5432.HttpClient.BackendSyncRequest;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-11-18
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class PlanService {

    private Dao<Plan, Integer> planDao = null;

    private Dao<PlanNextMission, String> planNextMissionDao = null;

    private Dao<PlanUserFollow, String> planUserFollowDao = null;

    private Dao<PlanRunHistory, String> planRunHistoryDao = null;

    private Dao<PlanCollect, String> planCollectDao = null;

    private MissionService missionService = null;

    private BackendSyncRequest backendSyncRequest = null;

    private RunningHistoryService runningHistoryService = null;

    public PlanService(DatabaseHelper helper) {
        try {
            planDao = helper.getPlanDao();
            planNextMissionDao = helper.getPlanNextMissionDao();
            planUserFollowDao = helper.getPlanUserFollowDao();
            planCollectDao = helper.getPlanCollectDao();
            planRunHistoryDao = helper.getPlanRunHistoryDao();
            missionService = new MissionService(helper);
            backendSyncRequest = new BackendSyncRequest(helper);
            runningHistoryService = new RunningHistoryService(helper);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void savePlanToDB(Plan plan) {
        try {
            planDao.createOrUpdate(plan);
            missionService.saveMissionToDB(plan.getMissionList());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Plan fetchPlan(Integer planId) {
        Plan plan = null;
        try {
            plan = planDao.queryForId(planId);
            plan.setMissionList(missionService.fetchMissionsByPlanId(planId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plan;
    }

    public void updatePlanCollect(PlanCollect planCollect) {
        try {
            planCollect.setCollectTime(UserUtil.getSystemTime());
            planCollect.setUpdated(1);
            planCollectDao.createOrUpdate(planCollect);
            backendSyncRequest.upLoadUserCollect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void collectPlan(Plan plan) {
        PlanCollect planCollect = new PlanCollect();
        planCollect.setUserId(UserUtil.getUserId());
        planCollect.setPlanId(plan.getPlanId());
        planCollect.setCollectStatus(CollectStatusEnum.NotCollected.ordinal());
        updatePlanCollect(planCollect);
    }

    public List<PlanCollect> fetchPlanCollect(Integer userId) {
        List<PlanCollect> planCollectList = null;

        try {
            QueryBuilder<PlanCollect, String> planCollectQueryBuilder = planCollectDao.queryBuilder();
            planCollectQueryBuilder.where().eq("userId", userId);
            planCollectList = planCollectQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planCollectList;
    }

    public void savePlanCollectToDB(List<PlanCollect> planCollectList) {
        try {
            for (PlanCollect planCollect : planCollectList) {
                planCollectDao.createOrUpdate(planCollect);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<PlanCollect> fetchUnsyncedPlanCollect(Integer userId) {
        List<PlanCollect> planCollectList = null;

        try {
            QueryBuilder<PlanCollect, String> planCollectQueryBuilder = planCollectDao.queryBuilder();
            planCollectQueryBuilder.where().eq("userId", userId).and().eq("updated", 1);
            planCollectList = planCollectQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return planCollectList;
    }

    public void updateUnsyncedPlanCollect(Integer userId) {
        try {
            UpdateBuilder<PlanCollect, String> planCollectUpdateBuilder = planCollectDao.updateBuilder();
            planCollectUpdateBuilder.where().eq("userId", userId).and().eq("updated", 1);
            planCollectUpdateBuilder.updateColumnValue("updated", 0);
            planCollectUpdateBuilder.update();

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void updateUserFollow(PlanUserFollow userFollow){
        try {
            userFollow.setAddTime(UserUtil.getSystemTime());
            userFollow.setUpdated(1);
            planUserFollowDao.createOrUpdate(userFollow);
            backendSyncRequest.upLoadUserFollow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlanUserFollow fetchUserFollow(Integer userId, Integer followerId){
        PlanUserFollow planUserFollow = null;

        try {
            QueryBuilder<PlanUserFollow, String> planUserFollowQueryBuilder = planUserFollowDao.queryBuilder();
            planUserFollowQueryBuilder.where().eq("userId", userId).and().eq("followUserId", followerId);
            planUserFollow = planUserFollowQueryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planUserFollow;
    }

    public void savePlanUserFollowToDB(List<PlanUserFollow> planUserFollowList) {
        try {
            for (PlanUserFollow planUserFollow : planUserFollowList) {
                planUserFollowDao.createOrUpdate(planUserFollow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanUserFollow> fetchUnsyncedPlanUserFollow(Integer userId) {
        List<PlanUserFollow> planUserFollowList = null;

        try {
            QueryBuilder<PlanUserFollow, String> planUserFollowQueryBuilder = planUserFollowDao.queryBuilder();
            planUserFollowQueryBuilder.where().eq("userId", userId).and().eq("updated", 1);
            planUserFollowList = planUserFollowQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return planUserFollowList;
    }

    public void updateUnsyncedPlanUserFollow(Integer userId) {
        try {
            UpdateBuilder<PlanUserFollow, String> planUserFollowUpdateBuilder = planUserFollowDao.updateBuilder();
            planUserFollowUpdateBuilder.where().eq("userId", userId).and().eq("updated", 1);
            planUserFollowUpdateBuilder.updateColumnValue("updated", 0);
            planUserFollowUpdateBuilder.update();

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<PlanRunHistory> fetchUserPlanHistoryList(Integer userId){
        List<PlanRunHistory> planRunHistory = null;

        try {
            QueryBuilder<PlanRunHistory, String> planRunHistoryQueryBuilder = planRunHistoryDao.queryBuilder();
            planRunHistoryQueryBuilder.where().eq("userId", userId);
            planRunHistory = planRunHistoryQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planRunHistory;
    }

    public PlanRunHistory fetchUserPlanHistory(String planRunUuid){
        PlanRunHistory planRunHistory = null;

        try {
            QueryBuilder<PlanRunHistory, String> planRunHistoryQueryBuilder = planRunHistoryDao.queryBuilder();
            planRunHistoryQueryBuilder.where().eq("planRunUuid", planRunUuid);
            planRunHistory = planRunHistoryQueryBuilder.queryForFirst();
            planRunHistory.setRunningHistoryList(runningHistoryService.fetchRunHistoryByPlanRunUuid(planRunUuid));
            Plan plan = fetchPlan(planRunHistory.getPlanId());
            planRunHistory.setPlandName(plan.getPlanName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planRunHistory;
    }

}
