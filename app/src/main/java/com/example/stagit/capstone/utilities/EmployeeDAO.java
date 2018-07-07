package com.example.stagit.capstone.utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EmployeeDAO {

    @Insert
    public void addEmployee(Employee employee);

    @Query("select * from employee where Id = :Id")
    public Employee getEmployee(int Id);

    @Query("select * from employee")
    public List<Employee> getEmployeeList();
}
