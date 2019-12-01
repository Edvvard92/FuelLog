package com.example.fuellog;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Log.class}, version = 1, exportSchema = false)
public abstract class LogRoomDatabase extends RoomDatabase {

    public abstract LogDao logDao();
    private static LogRoomDatabase INSTANCE;

    static LogRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LogRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LogRoomDatabase.class, "log_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LogDao mDao;
        String[] logs= {};

        PopulateDbAsync(LogRoomDatabase db) {
            mDao = db.logDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

       //      If we have no logs, then create the initial list of logs
       //     if (mDao.getAnyLog().length < 1) {
         //      for (int i = 0; i <= logs.length - 1; i++) {
       //             Log log = new Log(logs[i]);
       //             mDao.insert(log);
        //        }
        //    }
            return null;
        }
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}


