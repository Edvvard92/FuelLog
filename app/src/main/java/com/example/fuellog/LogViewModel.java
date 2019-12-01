package com.example.fuellog;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LogViewModel extends AndroidViewModel {

    private LogRepository mRepository;

    private LiveData<List<Log>> mAllLogs;

    public LogViewModel (Application application) {
        super(application);
        mRepository = new LogRepository(application);
        mAllLogs = mRepository.getAllLogs();
    }

    LiveData<List<Log>> getAllLogs() { return mAllLogs; }

    public void insert(Log log) { mRepository.insert(log); }
    public void deleteAll() {mRepository.deleteAll();}
    public void deleteLog(Log log) {mRepository.deleteLog(log);}
    public void update(Log log) {
        mRepository.update(log);
    }
}

