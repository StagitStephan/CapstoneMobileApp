package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface StoreInformationDAO {
    @Insert
    public void addStoreInforMation(StoreInformation storeInformation);

    @Query("select * from storeinformation where isActive = 1")
    public StoreInformation getStoreInformation();

    @Update
    public void updateStoreInformation(StoreInformation... storeInformation);
}
