package com.kalu.plugin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kalu.asmplugin.annotation.FastClick;
import com.kalu.asmplugin.annotation.TimeConsuming;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        findViewById(R.id.fastclick).setOnClickListener(new View.OnClickListener() {
//            @FastClick(time = 1100)
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "onHello", Toast.LENGTH_SHORT).show();
//            }
//        });

//        // req1001
//        findViewById(R.id.req1001).setOnClickListener(new View.OnClickListener() {
//
//            @FastClick(value = 1001)
//            @Override
//            public void onClick(View v) {
//
//                permissionVerificationRequest1001(MainActivity.this, false, false);
//            }
//        });
//
//        // req1002
//        findViewById(R.id.req1002).setOnClickListener(new View.OnClickListener() {
//
//            @FastClick(value = 1002)
//            @Override
//            public void onClick(View v) {
//
//                permissionVerificationRequest1002(MainActivity.this, false, false);
//            }
//        });
    }

    @TimeConsuming
    public void onClickTime(@NonNull View view) {
        for (int i = 0; i < 10000000; i++) {
            int m = 1;
            int n = 2;
            Math.max(Math.min(m, n), Math.max(m, n));
        }
    }

    @FastClick(time = 1200)
    @TimeConsuming
    public void onClickHello(@NonNull View view) {
        Toast.makeText(view.getContext(), "onHello", Toast.LENGTH_SHORT).show();
    }

//    @PermissionVerification(requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode = 1001)
//    public void permissionVerificationRequest1001(@NonNull Activity activity, @NonNull boolean isCallback, @NonNull boolean isPass) {
//
//        // succ
//        if (isCallback && isPass) {
//            Toast.makeText(getApplicationContext(), "权限获取成功", Toast.LENGTH_SHORT).show();
//        }
//        // fail
//        else {
//            Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @PermissionVerification(requestPermissions = {Manifest.permission.CAMERA}, requestCode = 1002)
//    public void permissionVerificationRequest1002(@NonNull Activity activity, @NonNull boolean isCallback, @NonNull boolean isPass) {
//
//        // succ
//        if (isCallback && isPass) {
//            Toast.makeText(getApplicationContext(), "权限获取成功", Toast.LENGTH_SHORT).show();
//        }
//        // fail
//        else {
//            Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void permissionVerificationRequest1(@NonNull Activity activity, @NonNull Integer code) {
//        Toast.makeText(getApplicationContext(), code + "", Toast.LENGTH_SHORT).show();
//    }
}
