package com.kalu.asmplugin


import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * description: 插件入口, 注册服务
 * created by kalu on 2021-01-20
 */
class AsmPlugin implements Plugin<Project> {

    static boolean isTimeConsuming = true
    static boolean isDebug = true
    static boolean isLog = true

    @Override
    void apply(Project project) {

//        // 配置文件初始化
//        project.afterEvaluate {
//        }

        System.out.println("AsmPlugin[apply] => project = " + project.name)

        // 监听
        AsmTaskListener listener = new AsmTaskListener()
        project.gradle.addListener(listener)

        // 任务
        AsmTransform transform = new AsmTransform()
        project.android.registerTransform(transform)
    }


}
