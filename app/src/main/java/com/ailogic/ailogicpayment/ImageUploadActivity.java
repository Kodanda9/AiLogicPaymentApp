package com.ailogic.ailogicpayment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ailogic.ailogicpayment.uploadUtil.BottomDialog;
import com.ailogic.ailogicpayment.uploadUtil.FilePickUtils;
import com.ailogic.ailogicpayment.uploadUtil.LifeCycleCallBackManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import static com.ailogic.ailogicpayment.uploadUtil.FilePickUtils.CAMERA_PERMISSION;
import static com.ailogic.ailogicpayment.uploadUtil.FilePickUtils.STORAGE_PERMISSION_IMAGE;

public class ImageUploadActivity extends Activity {

    private SimpleDraweeView ivImage;
    private FilePickUtils filePickUtils;
    private BottomDialog bottomDialog;
    private LifeCycleCallBackManager lifeCycleCallBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload);

        ivImage =(SimpleDraweeView) findViewById(R.id.ivImage);
        showImagePickerDialog(onFileChoose);

    }//onCreate

    private FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
        @Override public void onFileChoose(String fileUri, int requestCode) {
//            bottomDialog.dismiss();
            Log.i("Str-->",fileUri);
            ivImage.setImageURI(Uri.fromFile(new File(fileUri)));
        }
    };
    public void showImagePickerDialog(FilePickUtils.OnFileChoose onFileChoose) {
        filePickUtils = new FilePickUtils(this, onFileChoose);
        lifeCycleCallBackManager = filePickUtils.getCallBackManager();
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_photo_selector, null);
        bottomDialog = new BottomDialog(ImageUploadActivity.this);
        bottomDialog.setContentView(bottomSheetView);
        final TextView tvCamera = bottomSheetView.findViewById(R.id.tvCamera);
        final TextView tvGallery = bottomSheetView.findViewById(R.id.tvGallery);
        tvCamera.setOnClickListener(onCameraListener);
        tvGallery.setOnClickListener(onGalleryListener);
        bottomDialog.show();
    }

    private View.OnClickListener onCameraListener = new View.OnClickListener() {
        @Override public void onClick(View view) {
            filePickUtils.requestImageCamera(CAMERA_PERMISSION, true, true);
        }
    };

    private View.OnClickListener onGalleryListener = new View.OnClickListener() {
        @Override public void onClick(View view) {
            filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, true, true);
        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void showDialog(View view) {
        showImagePickerDialog(onFileChoose);
    }
}
