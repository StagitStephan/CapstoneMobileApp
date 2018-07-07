package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AttendanceTMP.class, StoreInformation.class, Employee.class}, version = 4, exportSchema = false)
public abstract class AttendanceTMPDBAdapter extends RoomDatabase {
    public abstract AttendanceDAO getAttendanceDAO();
    public abstract StoreInformationDAO getStoreInformationDAO();
    public abstract EmployeeDAO getEmployeeDAO();
}
