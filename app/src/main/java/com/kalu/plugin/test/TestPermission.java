package com.kalu.plugin.test;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class TestPermission extends Activity {

    public void permissionVerificationRequest1(@NonNull Activity activity, @NonNull boolean isCallback, @NonNull boolean isPass) {

        // 测试1
        int requestCode = 1001;
        String[] strings = new String[]{Manifest.permission.CAMERA};

        // 测试1

        // 检测
        if (!isCallback && !isPass && ContextCompat.checkSelfPermission(activity, strings[0]) != 0) {
            ActivityCompat.requestPermissions(activity, strings, requestCode);
        }
        // 回调
        else {
            if (isCallback && isPass) {
                Toast.makeText(this, "请添加权限！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "已获得权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void permissionVerificationRequest1(@NonNull Activity activity, @NonNull Integer integer) {

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean var1 = true;
        for (int var3 = 0; var3 < grantResults.length; var3++) {
            if (grantResults[var3] != PackageManager.PERMISSION_GRANTED) {
                var1 = false;
                break;
            }
        }

        Log.e("kalu", var1 + "");

        // callback
        // createCallback(requestCode, false, null);
    }

    private void createCallback(int var1, boolean var2, HashMap<Integer, String> var3) {
        for (Map.Entry<Integer, String> entry : var3.entrySet()) {
            if (null != entry.getValue() && entry.getValue().length() > 0 && null != entry.getKey() && var1 == entry.getKey()) {
                this.permissionVerificationRequest1(this, true, var2);
                return;
            }
        }
    }
}
