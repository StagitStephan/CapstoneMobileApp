package com.example.stagit.capstone.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.stagit.capstone.R;
import com.example.stagit.capstone.apiconnector.EmployeeService;
import com.example.stagit.capstone.apiconnector.LoginService;
import com.example.stagit.capstone.enums.ConnectURL;
import com.example.stagit.capstone.service.BackgroundServices;
import com.example.stagit.capstone.utilities.Employee;
import com.example.stagit.capstone.utilities.StoreInformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements Callback<List<Employee>>{

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textClock)
    TextClock textClock;
    @BindView(R.id.homeFragmentContainer)
    FrameLayout homeFragmentContainer;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        //Sending attendances to Sv every 5 minutes
        new BackgroundServices().sendingAttendanceToServerAsync();

        StoreInformation storeInformation = MainActivity.attendanceTMPDBAdapter.getStoreInformationDAO().getStoreInformation();
        textView.setText("CỬA HÀNG BÁN LẺ " + storeInformation.getStoreName().toString().trim().toUpperCase());
        loadData(Integer.parseInt(storeInformation.getStoreID()));
        fragmentManager = getSupportFragmentManager();
        if(homeFragmentContainer != null){
            if(savedInstanceState!= null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.homeFragmentContainer, new HomeFragment()).commit();
        }
    }

    private void loadData(int storeId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectURL.CONNECT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService employeeService = retrofit.create(EmployeeService.class);
        Call<List<Employee>> call = employeeService.getEmployee(storeId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
        List<Employee> employeeList = response.body();
        if(employeeList == null || !employeeList.isEmpty()) {
            for (Employee employee: employeeList) {
                Employee employeeInDB = MainActivity.attendanceTMPDBAdapter.getEmployeeDAO().getEmployee(employee.getId());
                if(employeeInDB == null) {
                    MainActivity.attendanceTMPDBAdapter.getEmployeeDAO().addEmployee(employee);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<List<Employee>> call, Throwable t) {

    }
}
