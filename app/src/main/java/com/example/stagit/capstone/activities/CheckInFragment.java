package com.example.stagit.capstone.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stagit.capstone.R;
import com.example.stagit.capstone.utilities.AttendanceTMP;
import com.example.stagit.capstone.utilities.Employee;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInFragment extends Fragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.openCameraBtn)
    Button openCameraBtn;
    @BindView(R.id.confirmCheckInID)
    Button confirmCheckInID;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    Unbinder unbinder;
    Bitmap bitmap;
    @BindView(R.id.inputID)
    EditText inputID;
    @BindView(R.id.employeeName)
    EditText employeeName;
    @BindView(R.id.idText)
    TextView idText;

    public CheckInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.openCameraBtn)
    void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.confirmCheckInID)
    void confirmCheckIn() {
        if (imageView.getDrawable() == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Thông Báo");
            alertDialog.setMessage("Xin hãy chụp hình trước khi điểm danh!!!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else if (employeeName.getText().toString().trim().length() <= 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Thông Báo");
            alertDialog.setMessage("Xin hãy nhập mã pin nhân viên trước khi điểm danh!!!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else

        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String bitmapBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            AttendanceTMP attendanceTMP = new AttendanceTMP();
            attendanceTMP.setTime(new Date());
            attendanceTMP.setImgBase64(bitmapBase64);
            attendanceTMP.setEmployeeID(Integer.parseInt(idText.getText().toString().trim()));
            attendanceTMP.setSend(false);
            MainActivity.attendanceTMPDBAdapter.getAttendanceDAO().addAttendanceTMP(attendanceTMP);
            Toast.makeText(getActivity(), "Đã xác nhận điểm danh!", Toast.LENGTH_LONG).show();
            imageView.setImageResource(android.R.color.transparent);
            HomeActivity.fragmentManager.beginTransaction().replace(R.id.homeFragmentContainer, new HomeFragment()).commit();
        }

    }

    @OnTextChanged(R.id.inputID)
    void inputChange() {
        String inputUserName = inputID.getText().toString().trim();
        String username = "";
        int id = 0;
        List<Employee> employeeList = MainActivity.attendanceTMPDBAdapter.getEmployeeDAO().getEmployeeList();
        for (Employee employee : employeeList) {
            if (inputUserName.equalsIgnoreCase(employee.getUsername())) {
                username = employee.getEmployeeName();
                id = employee.getId();
            }
        }
        employeeName.setText(username);
        idText.setText(id+"");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
