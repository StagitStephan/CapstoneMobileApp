package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AttendanceDAO {

    @Insert
    public void addAttendanceTMP(AttendanceTMP attendanceTMP);

    @Query("select * from attendancetmp where isSend = 0")
    public List<AttendanceTMP> getAttendanceTMP();

    @Update
    public void updateAttendaceTMP(AttendanceTMP... attendanceTMP);
}
