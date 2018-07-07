package com.example.stagit.capstone.apiconnector;

import com.example.stagit.capstone.utilities.AttendanceTMP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AttendanceTMPService {
    @POST("/api/attendance/AddAttendanceList")
    Call<Void> sendAttendance(
            @Body List<AttendanceTMP> listAttendance
    );
}
