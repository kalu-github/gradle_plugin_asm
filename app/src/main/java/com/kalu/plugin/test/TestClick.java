package com.kalu.plugin.test;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

public class TestClick extends Activity {

    public void onHello(View view) {

        long startTime = System.currentTimeMillis();
        Log.d("asmtime", "methodName" + "[+" + "className" + "] => startTime" + startTime);

        long closeTime = System.nanoTime();
        Log.d("asmtime", "methodName" + "[+" + "className" + "] => closeTime" + closeTime);

        long costTime = closeTime - startTime;
        Log.d("asmtime", "methodName" + "[+" + "className" + "] => costTime" + ((float) costTime / 1000) + "ms");

        Log.d("asmclick", "*************************************");
        Log.d("asmclick", "view = " + view);
        if (null != view) {

            Context context = view.getContext();
            Log.d("asmclick", "context = " + context);
            if (null != context) {

                int identifier = -1;
                try {
                    identifier = context.getResources().getIdentifier("id_plugin_annotation_fastclick", "id", context.getPackageName());
                } catch (Exception e) {
                }
                Log.d("asmclick", "identifier = " + identifier);
                if (identifier != -1) {

                    long lastTime;
                    Object tag = view.getTag(identifier);
                    if (null != tag) {
                        lastTime = Long.parseLong(tag.toString());
                    } else {
                        lastTime = 0;
                    }
                    Log.d("asmclick", "lastTime = " + lastTime);

                    long timeMillis = System.currentTimeMillis();
                    Log.d("asmclick", "timeMillis = " + timeMillis);

                    long timeDistance = timeMillis - lastTime;
                    Log.d("asmclick", "timeDistance = " + timeDistance);

                    // 通过
                    if (0 < timeDistance && timeDistance >= 2000) {
                        view.setTag(identifier, timeMillis);
                        Log.d("asmclick", "通过");
                    }
                    // 拦截
                    else {
                        Log.d("asmclick", "拦截");
                    }
                }
            }
        }
        Log.d("asmclick", "*************************************");
    }
}
