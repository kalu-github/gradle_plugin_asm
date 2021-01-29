package com.kalu.plugin;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kalu.asmplugin.annotation.FastClick;
import com.kalu.asmplugin.annotation.PermissionVerification;
import com.kalu.asmplugin.annotation.TimeConsuming;

@Keep
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request1001
        findViewById(R.id.request1001).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onPermissionRequestMain(MainActivity.this, false, false);
            }
        });
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
    public void onClickFast(@NonNull View view) {
        Toast.makeText(view.getContext(), "onClickFast", Toast.LENGTH_SHORT).show();
    }

    @FastClick(time = 1200)
    @TimeConsuming
    public void onClickHello(@NonNull View view) {
        Toast.makeText(view.getContext(), "onHello", Toast.LENGTH_SHORT).show();
    }

    @PermissionVerification(requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode = 1001, callName = "com/kalu/plugin/MainActivity")
    public void onPermissionRequestMain(@NonNull Activity activity, @NonNull boolean isCall, @NonNull boolean isPass) {

        Toast.makeText(getApplicationContext(), isPass ? "request1001成功" : "request1001失败", Toast.LENGTH_SHORT).show();
    }
}
