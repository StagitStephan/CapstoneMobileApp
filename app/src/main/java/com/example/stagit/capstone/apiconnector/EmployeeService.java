package com.example.stagit.capstone.apiconnector;

import com.example.stagit.capstone.utilities.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeService {
    @POST("/api/employee/GetAllEmployeeByStoreId")
    Call<List<Employee>> getEmployee(
            @Query("storeId") int storeId
    );
}
