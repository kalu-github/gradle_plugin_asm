package com.kalu.asmplugin.util;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.TransformOutputProvider;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class PluginFileUtil {

    public static void copyJarInput(TransformOutputProvider outputProvider, JarInput jarInput) {

        if (null == jarInput)
            return;

        String destName = jarInput.getName();
        if (null == destName || destName.length() == 0)
            return;

        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length() - 4);
        }

        copyJarInput(outputProvider, jarInput, destName);
    }

    public static void copyJarInput(TransformOutputProvider outputProvider, JarInput jarInput, String destName) {

        if (null == jarInput)
            return;

        String absolutePath = jarInput.getFile().getAbsolutePath();
        String md5Hex = DigestUtils.md5Hex(absolutePath);

        // 获得输出文件
        File srcFile = jarInput.getFile();
        File destFile = outputProvider.getContentLocation(destName + "_" + md5Hex, jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
        try {
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            System.out.println("PluginFileUtil[copyJarInput] => " + e.getMessage());
        }
    }

    public static void copyDirectory(DirectoryInput directoryInput, File destDir) {

        if (null == directoryInput)
            return;

        File srcDir = directoryInput.getFile();
        if (null == srcDir)
            return;

        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            System.out.println("PluginFileUtil[copyDirectory] => " + e.getMessage());
        }
    }
}
