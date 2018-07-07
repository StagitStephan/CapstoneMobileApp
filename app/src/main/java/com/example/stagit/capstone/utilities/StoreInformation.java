package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "storeinformation")
public class StoreInformation {

    @PrimaryKey(autoGenerate = true)
    int id;

    String storeId;

    String storeName;

    String location;

    boolean isActive;

    public String getStoreID() {
        return storeId;
    }

    public void setStoreID(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getID() {

        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
