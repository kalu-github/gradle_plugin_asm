package com.kalu.asmplugin.util;

/**
 * description: 日志
 * created by kalu on 2021-01-24
 */
public class PluginLogUtil {

    public static final void log(String string) {

        if (null == string || string.length() == 0)
            return;

        System.out.println(string);
    }
}
