package com.example.fuellog;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_table")
public class Log {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "log")
    private String mLog;
    @NonNull
    @ColumnInfo(name = "log2")
    private String mLog2;
    @NonNull
    @ColumnInfo(name = "log3")
    private String mLog3;

    public Log(@NonNull String log,@NonNull String log2,@NonNull String log3)  {
        this.mLog = log;
        this.mLog2 = log2;
        this.mLog3 = log3;
    }


    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Log(int id, @NonNull String log,@NonNull String log2,@NonNull String log3) {
        this.id = id;
        this.mLog = log;
        this.mLog2 = log2;
        this.mLog3 = log3;
    }


    public String getLog(){
        return this.mLog;

    }
    public String getLog2(){
        return this.mLog2;

    }
    public String getLog3(){
        return this.mLog3;

    }
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
