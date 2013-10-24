package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午12:08
 * To change this template use File | Settings | File Templates.
 */
public class RunningHistoryService {

    private Dao<RunningHistory, String> runningHistoryDao = null;

    public RunningHistoryService(DatabaseHelper helper) {
        try {
            runningHistoryDao = helper.getRunningHistoryDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<RunningHistory> fetchRunHistoryByUserId(Integer userId) {
        List<RunningHistory> runningHistoryList = null;
        try {
            QueryBuilder<RunningHistory, String> runningHistoryQueryBuilder = runningHistoryDao.queryBuilder();
            runningHistoryQueryBuilder.where().eq("userId", userId);
            runningHistoryQueryBuilder.orderBy("missionEndTime", false);
            runningHistoryList = runningHistoryQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistoryList;
    }

    public Map<String, String> fetchTotalByUserIdAndMissionType(Integer userId, List<Integer> missionTypeList) {
        try {
            String missionTypeQuery = "";
            if (missionTypeList != null && missionTypeList.size() > 0) {
                missionTypeQuery = "missiontypeid =" + missionTypeList.get(0);
                for (int i = 1; i < missionTypeList.size(); i++) {
                    missionTypeQuery += " or missiontypeid =" + missionTypeList.get(i);
                }
                if (missionTypeQuery != "") {
                    missionTypeQuery = " and (" + missionTypeQuery + ")";
                }
            }

            GenericRawResults<String[]> rawResults = runningHistoryDao.queryRaw("select sum(distance),avg(avgSpeed),sum(spendCarlorie) " +
                    "from RunningHistory where userid = " + userId + missionTypeQuery);

            List<String[]> results = rawResults.getResults();
            Map<String, String> total = null;
            if (results != null && results.size() > 0) {
                total = new HashMap<String, String>();
                String[] resultArray = results.get(0);
                total.put("distance", resultArray[0]);
                total.put("speed", resultArray[1]);
                total.put("carlorie", resultArray[2]);
                rawResults = runningHistoryDao.queryRaw("select count(*) from RunningHistory where userid = " + userId + " and missiontypeid = 0");
                results = rawResults.getResults();
                resultArray = results.get(0);
                total.put("challenge", resultArray[0]);
            }
            return total;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public List<RunningHistory> fetchRunHistoryByUserIdAndMissionType(Integer userId, List<Integer> missionTypeList) {
        List<RunningHistory> runningHistoryList = null;
        try {
            QueryBuilder<RunningHistory, String> runningHistoryQueryBuilder = runningHistoryDao.queryBuilder();
            runningHistoryQueryBuilder.where().eq("userId", userId).and().in("missionTypeId", missionTypeList);
            runningHistoryQueryBuilder.orderBy("missionEndTime", false);

            runningHistoryList = runningHistoryQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistoryList;
    }

    public RunningHistory fetchBestRunHistoryByMissionId(Integer missionId, Integer userId) {
        RunningHistory runningHistory = null;
        try {
            QueryBuilder<RunningHistory, String> runningHistoryQueryBuilder = runningHistoryDao.queryBuilder();
            runningHistoryQueryBuilder.where().eq("userId", userId).and().eq("missionId", missionId).and().eq("valid", 1);
            runningHistoryQueryBuilder.orderBy("missionGrade", false);
            runningHistory = runningHistoryQueryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistory;
    }

    public List<RunningHistory> fetchUnsyncedRunHistory(Integer userId) {
        List<RunningHistory> runningHistoryList = null;
        try {
            QueryBuilder<RunningHistory, String> runningHistoryQueryBuilder = runningHistoryDao.queryBuilder();
            Where where = runningHistoryQueryBuilder.where();
            where.and(where.or(where.eq("userId", userId), where.eq("userId", -1)), where.eq("commitTime", null));
            runningHistoryList = runningHistoryQueryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistoryList;
    }

    public void updateUnsyncedRunHistories(Integer userId) {
        List<RunningHistory> runningHistoryList = fetchUnsyncedRunHistory(userId);
        if (runningHistoryList == null || runningHistoryList.size() == 0) {
            return;
        }
        try {
            for (RunningHistory runningHistory : runningHistoryList) {
                runningHistory.setUserId(userId);
                //todo:: change it to systemTime
                runningHistory.setCommitTime(new Date());
                runningHistoryDao.update(runningHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public RunningHistory fetchRunHistoryByRunId(String runId) {
        RunningHistory runningHistory = null;
        try {
            runningHistory = runningHistoryDao.queryForId(runId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return runningHistory;
    }

    public void saveRunListToDB(List<RunningHistory> runningHistoryList) {
        try {
            for (RunningHistory runningHistory : runningHistoryList) {
                if (runningHistory.getRunUuid() != null) {
                    runningHistoryDao.createOrUpdate(runningHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveRunInfoToDB(RunningHistory runningHistory) {
        try {
            if (runningHistory.getRunUuid() != null) {
                runningHistoryDao.createOrUpdate(runningHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
