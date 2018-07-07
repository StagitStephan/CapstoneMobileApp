package com.example.stagit.capstone.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stagit.capstone.R;
import com.example.stagit.capstone.service.BackgroundServices;
import com.example.stagit.capstone.utilities.AttendanceTMPDBAdapter;
import com.example.stagit.capstone.utilities.StoreInformation;

public class MainActivity extends AppCompatActivity {
    public static AttendanceTMPDBAdapter attendanceTMPDBAdapter;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        run sync at 8 am every day but replace with each time loading home screen
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent newIntent = new Intent(MainActivity.this, SyncDataService.class);
//        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,newIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 8 * 60 * 60 * 1000, 24 * 60 * 60 * 1000,pendingIntent);

        //create database
        attendanceTMPDBAdapter = Room.databaseBuilder(getApplicationContext(), AttendanceTMPDBAdapter.class, "capstonedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        //get store information to redirect
        StoreInformation storeInformation = attendanceTMPDBAdapter.getStoreInformationDAO().getStoreInformation();
        if (storeInformation != null) {
            loadHome();
        } else {
            loadLogin();
        }
    }

    protected void loadLogin() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    protected void loadHome() {
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }
}
