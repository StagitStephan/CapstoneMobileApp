package com.example.stagit.capstone.activities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stagit.capstone.R;
import com.example.stagit.capstone.utilities.AttendanceTMP;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.dateText)
    TextView dateText;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    Unbinder unbinder;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        List<AttendanceTMP> attendanceTMPList = MainActivity.attendanceTMPDBAdapter.getAttendanceDAO().getAttendanceTMP();
        if(!attendanceTMPList.isEmpty()){
            byte[] decodedString = Base64.decode(attendanceTMPList.get(0).getImgBase64(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
            dateText.setText(new SimpleDateFormat().format(attendanceTMPList.get(0).getTime()));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
