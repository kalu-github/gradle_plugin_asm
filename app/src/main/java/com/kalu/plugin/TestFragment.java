package com.kalu.plugin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kalu.asmplugin.annotation.PermissionVerification;

public class TestFragment extends Fragment {

//    @PermissionVerification(requestPermissions = {Manifest.permission.CAMERA}, requestCode = 1002)
//    public void request(@NonNull Context context) {
//
//        requestPermissions(new String[1], 1002);
//
//    }
//
//    @PermissionVerification(isFragment = true, requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode = 1001)
//    public void permissionVerificationRequest1(@NonNull Activity activity, @NonNull boolean isCallback, @NonNull boolean isPass) {
//
//        // succ
//        if (isCallback && isPass) {
//            Toast.makeText(getContext(), "权限获取成功", Toast.LENGTH_SHORT).show();
//        }
//        // fail
//        else {
//            Toast.makeText(getContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
//        }
//    }
}
