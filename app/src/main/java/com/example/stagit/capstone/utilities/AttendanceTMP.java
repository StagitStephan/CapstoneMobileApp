package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.stagit.capstone.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "attendancetmp")
public class AttendanceTMP {
    @PrimaryKey(autoGenerate = true)
    private  int ID;

    @TypeConverters({DateConverter.class})
    private Date time;

    private String imgBase64;

    private int employeeID;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    private boolean isSend;

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}
