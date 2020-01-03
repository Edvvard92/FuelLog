package com.example.fuellog;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LogRepository {

    private LogDao mLogDao;
    private LiveData<List<LogCar>> mAllLogs;

    LogRepository(Application application) {
        LogRoomDatabase db = LogRoomDatabase.getDatabase(application);
        mLogDao = db.logDao();
        mAllLogs = mLogDao.getAllLogs();
    }

    LiveData<List<LogCar>> getAllLogs() {
        return mAllLogs;
    }

    public void insert(LogCar logCar) {
        new insertAsyncTask(mLogDao).execute(logCar);
    }

    public void update(LogCar logCar) {
        new updateLogAsyncTask(mLogDao).execute(logCar);
    }

    private static class insertAsyncTask extends AsyncTask<LogCar, Void, Void> {

        private LogDao mAsyncTaskDao;


        insertAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LogCar... params) {
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

    public void deleteAll() {
        new deleteAllLogsAsyncTask(mLogDao).execute();
    }

    private static class deleteLogAsyncTask extends AsyncTask<LogCar, Void, Void> {
        private LogDao mAsyncTaskDao;

        deleteLogAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LogCar... params) {
            mAsyncTaskDao.deleteLog(params[0]);
            return null;
        }
    }

    public void deleteLog(LogCar logCar) {
        new deleteLogAsyncTask(mLogDao).execute(logCar);
    }

    /**
     * Updates a word in the database.
     */
    private static class updateLogAsyncTask extends AsyncTask<LogCar, Void, Void> {
        private LogDao mAsyncTaskDao;

        updateLogAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LogCar... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
