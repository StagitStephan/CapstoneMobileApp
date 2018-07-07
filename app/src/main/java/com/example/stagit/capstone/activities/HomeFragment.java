package com.example.stagit.capstone.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.stagit.capstone.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.checkInBtn)
    Button checkInBtn;
    @BindView(R.id.empListBtn)
    Button empListBtn;
    @BindView(R.id.scheduleBtn)
    Button scheduleBtn;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.checkInBtn)
    void goToCheckIn(){
        HomeActivity.fragmentManager.beginTransaction()
                .replace(R.id.homeFragmentContainer, new CheckInFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.scheduleBtn)
    void goToSchedule(){
        HomeActivity.fragmentManager.beginTransaction()
                .replace(R.id.homeFragmentContainer, new ScheduleFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
