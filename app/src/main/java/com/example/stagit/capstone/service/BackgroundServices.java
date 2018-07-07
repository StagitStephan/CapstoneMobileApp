package com.example.stagit.capstone.service;


import android.os.Handler;

import com.example.stagit.capstone.activities.MainActivity;
import com.example.stagit.capstone.apiconnector.AttendanceTMPService;
import com.example.stagit.capstone.enums.ConnectURL;
import com.example.stagit.capstone.utilities.AttendanceTMP;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundServices implements Callback<Void>{
    List<AttendanceTMP> attendanceTMPList;
    public void sendingAttendanceToServerAsync(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            attendanceTMPList = MainActivity.attendanceTMPDBAdapter.getAttendanceDAO().getAttendanceTMP();
                            if(!attendanceTMPList.isEmpty()){
                                uploadAttendanceTMPList(attendanceTMPList);
                            }
                        }
                        catch (Exception e){
                            String a = "";
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 5 * 3 * 1000);
    }

    private void uploadAttendanceTMPList(List<AttendanceTMP> attendanceTMPList){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectURL.CONNECT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AttendanceTMPService attendanceTMPService = retrofit.create(AttendanceTMPService.class);
        Call<Void> call = attendanceTMPService.sendAttendance(attendanceTMPList);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.code() == 200){
            for (AttendanceTMP attendanceTMP: attendanceTMPList) {
                attendanceTMP.setSend(true);
                MainActivity.attendanceTMPDBAdapter.getAttendanceDAO().updateAttendaceTMP(attendanceTMP);
            }
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        
    }
}
