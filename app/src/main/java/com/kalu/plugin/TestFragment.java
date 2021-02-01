package com.kalu.plugin;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kalu.asmplugin.annotation.PermissionVerification;

public class TestFragment extends Fragment {

    @PermissionVerification(
            requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
            requestCode = 1002,
            requestCall = TestFragment.class)
    public void onPermissionRequestTest(@NonNull Activity activity, @NonNull boolean isCall, @NonNull boolean isPass) {

        Toast.makeText(getContext(), isPass ? "权限获取成功" : "权限获取失败", Toast.LENGTH_SHORT).show();
    }
}
