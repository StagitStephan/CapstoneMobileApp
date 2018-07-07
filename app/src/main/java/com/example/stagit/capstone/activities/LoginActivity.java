package com.example.stagit.capstone.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stagit.capstone.R;
import com.example.stagit.capstone.apiconnector.LoginService;
import com.example.stagit.capstone.enums.ConnectURL;
import com.example.stagit.capstone.utilities.StoreInformation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements Callback<List<StoreInformation>> {

    @BindView(R.id.usernameTxt)
    EditText usernameTxt;
    @BindView(R.id.passwordTxt)
    EditText passwordTxt;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    private void showLoading() {
        loadingPanel.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingPanel.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        hideLoading();
    }

    private  boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @OnClick(R.id.btnLogin)
    void login() {
        showLoading();
        if(!isNetworkAvailable()){
            return;
        }
        String username = usernameTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter username or password", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectURL.CONNECT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        Call<List<StoreInformation>> call = loginService.login(username, password);
        call.enqueue(this);
    }

    private void goToHome() {
        usernameTxt.setText("");
        passwordTxt.setText("");
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<List<StoreInformation>> call, Response<List<StoreInformation>> response) {
        List<StoreInformation> storeInformations = response.body();
        if(storeInformations.isEmpty()){
            Toast.makeText(getBaseContext(), "No response from server", Toast.LENGTH_SHORT);
            return;
        }
        StoreInformation oldStoreInformation = MainActivity.attendanceTMPDBAdapter.getStoreInformationDAO().getStoreInformation();
        if (oldStoreInformation != null) {
            oldStoreInformation.setActive(false);
            MainActivity.attendanceTMPDBAdapter.getStoreInformationDAO().updateStoreInformation(oldStoreInformation);
        }
        StoreInformation newStoreInformation = new StoreInformation();
        newStoreInformation.setStoreID(storeInformations.get(0).getStoreID());
        newStoreInformation.setStoreName(storeInformations.get(0).getStoreName());
        newStoreInformation.setLocation(storeInformations.get(0).getLocation());
        newStoreInformation.setActive(true);
        MainActivity.attendanceTMPDBAdapter.getStoreInformationDAO().addStoreInforMation(newStoreInformation);
        hideLoading();
        goToHome();
    }

    @Override
    public void onFailure(Call<List<StoreInformation>> call, Throwable t) {
        hideLoading();
        Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
    }
}
