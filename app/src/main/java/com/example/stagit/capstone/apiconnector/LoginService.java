package com.example.stagit.capstone.apiconnector;

import com.example.stagit.capstone.utilities.StoreInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @POST("/api/employee/CheckLogin")
    Call<List<StoreInformation>> login(
            @Query("username") String username,
            @Query("password") String password
    );

}
