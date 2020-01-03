package com.example.fuellog;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LogViewModel extends AndroidViewModel {

    private LogRepository mRepository;

    private LiveData<List<LogCar>> mAllLogs;

    public LogViewModel(Application application) {
        super(application);
        mRepository = new LogRepository(application);
        mAllLogs = mRepository.getAllLogs();
    }

    LiveData<List<LogCar>> getAllLogs() {
        return mAllLogs;
    }

    public void insert(LogCar logCar) {
        mRepository.insert(logCar);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteLog(LogCar logCar) {
        mRepository.deleteLog(logCar);
    }

    public void update(LogCar logCar) {
        mRepository.update(logCar);
    }
}

