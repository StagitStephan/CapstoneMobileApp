package com.example.stagit.capstone.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.stagit.capstone.R;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends Activity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.openCameraBtn)
    Button openCameraBtn;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.confirmCheckInID)
    Button confirmCheckin;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.openCameraBtn)
    void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.confirmCheckInID)
    void confirmCheckIn() {
        if (imageView.getDrawable() == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(CameraActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please take a picture before check in!!!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String bitmapBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}
