package com.G5432.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.G5432.Entity.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-8
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Mission.class);
            TableUtils.createTable(connectionSource, MissionChallenge.class);
            TableUtils.createTable(connectionSource, MissionPlacePackage.class);
            TableUtils.createTable(connectionSource, RunningHistory.class);
            TableUtils.createTable(connectionSource, SystemMessage.class);
            TableUtils.createTable(connectionSource, UserBase.class);
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, VersionControl.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Dao<Mission, Integer> missionDao;

    public Dao<Mission, Integer> getMissionDao() throws SQLException {
        if (missionDao == null) {
            missionDao = getDao(Mission.class);
        }
        return missionDao;
    }

    private Dao<MissionChallenge, Void> missionChallengeDao;

    public Dao<MissionChallenge, Void> getMissionChallengeDao() throws SQLException {
        if (missionChallengeDao == null) {
            missionChallengeDao = getDao(MissionChallenge.class);
        }
        return missionChallengeDao;
    }

    private Dao<MissionPlacePackage, Void> missionPlacePackageDao;

    public Dao<MissionPlacePackage, Void> getMissionPlacePackageDao() throws SQLException {
        if (missionPlacePackageDao == null) {
            missionPlacePackageDao = getDao(MissionPlacePackage.class);
        }
        return missionPlacePackageDao;
    }

    private Dao<RunningHistory, String> runningHistoryDao;

    public Dao<RunningHistory, String> getRunningHistoryDao() throws SQLException {
        if (runningHistoryDao == null) {
            runningHistoryDao = getDao(RunningHistory.class);
        }
        return runningHistoryDao;
    }

    private Dao<SystemMessage, Integer> systemMessageDao;

    public Dao<SystemMessage, Integer> getSystemMessageDao() throws SQLException {
        if (systemMessageDao == null) {
            systemMessageDao = getDao(SystemMessage.class);
        }
        return systemMessageDao;
    }

    private Dao<UserBase, Integer> userBaseDao;

    public Dao<UserBase, Integer> getUserBaseDao() throws SQLException {
        if (userBaseDao == null) {
            userBaseDao = getDao(UserBase.class);
        }
        return userBaseDao;
    }

    private Dao<UserInfo, Integer> userInfoDao;

    public Dao<UserInfo, Integer> getUserInfoDao() throws SQLException {
        if (userInfoDao == null) {
            userInfoDao = getDao(UserInfo.class);
        }
        return userInfoDao;
    }

    private Dao<VersionControl, String> versionControlDao;

    public Dao<VersionControl, String> getVersionControlDao() throws SQLException {
        if (versionControlDao == null) {
            versionControlDao = getDao(VersionControl.class);
        }
        return versionControlDao;
    }

}
