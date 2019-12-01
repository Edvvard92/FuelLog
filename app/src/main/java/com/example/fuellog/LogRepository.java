package com.example.fuellog;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;

public class LogRepository {

    private LogDao mLogDao;
    private LiveData<List<Log>> mAllLogs;

    LogRepository(Application application) {
        LogRoomDatabase db = LogRoomDatabase.getDatabase(application);
        mLogDao = db.logDao();
        mAllLogs = mLogDao.getAllLogs();
    }

    LiveData<List<Log>> getAllLogs() {
        return mAllLogs;
    }

    public void insert (Log log) {
        new insertAsyncTask(mLogDao).execute(log);
    }
    public void update(Log log)  {
        new updateLogAsyncTask(mLogDao).execute(log);
    }

    private static class insertAsyncTask extends AsyncTask<Log, Void, Void> {

        private LogDao mAsyncTaskDao;


        insertAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteAllLogsAsyncTask extends AsyncTask<Void, Void, Void> {
        private LogDao mAsyncTaskDao;

        deleteAllLogsAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void deleteAll()  {
        new deleteAllLogsAsyncTask(mLogDao).execute();
    }
    private static class deleteLogAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao mAsyncTaskDao;

        deleteLogAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
            mAsyncTaskDao.deleteLog(params[0]);
            return null;
        }
    }
    public void deleteLog(Log log)  {
        new deleteLogAsyncTask(mLogDao).execute(log);
    }

    /**
     *  Updates a word in the database.
     */
    private static class updateLogAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao mAsyncTaskDao;

        updateLogAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
