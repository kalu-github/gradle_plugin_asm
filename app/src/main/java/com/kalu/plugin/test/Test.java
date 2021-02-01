package com.kalu.plugin.test;

import android.app.Activity;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Test extends Activity {

//    public void main(Activity activity, boolean isCall, boolean isPass) {
//
//    }

//    public void main() {
//
//        long start = System.currentTimeMillis();
////
////        long close = System.currentTimeMillis();
////        long time = close - start;
////        Log.e("asmtest", time + "");
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean pass = true;
        if (null == permissions || permissions.length == 0 || null == grantResults || grantResults.length == 0) {
            pass = false;
        } else {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != 0) {
                    pass = false;
                    break;
                }
            }
        }
        Log.d("asmpermission", "onRequestPermissionsResult => pass = " + pass);


        if (requestCode == 1001) {
            this.onPermissionRequestMain(this, true, pass);
            return;
        }
    }

    private void onPermissionRequestMain(Activity var1, boolean isCall, boolean isPass) {

        String[] strings = {"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

        // 权限状态
        boolean isAgree = true;
        for (int i = 0; i < strings.length; i++) {

            int status = ContextCompat.checkSelfPermission(var1, strings[i]);
            Log.d("asmpermission", "checkSelfPermission => permission" + strings[i] + ", status = " + status);

            if (status != 0) {
                isAgree = false;
                break;
            }
        }

        Log.d("asmpermission", "checkSelfPermission => isCall = " + isCall);
        Log.d("asmpermission", "checkSelfPermission => isAgree = " + isAgree);

        Log.d("asmpermission", "checkSelfPermission => isPass = " + isPass);
        isPass = isAgree;
        Log.d("asmpermission", "checkSelfPermission => isPass = " + isPass);


        if (!isCall && !isAgree) {
            ActivityCompat.requestPermissions(var1, strings, 1001);
            return;
        }
    }
}
