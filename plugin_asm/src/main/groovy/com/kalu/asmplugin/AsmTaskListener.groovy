package com.kalu.asmplugin

import com.kalu.asmplugin.util.PluginLogUtil
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState;

/**
 * description: 此类可以监控每一个task的执行开始和结束，以及工程build的情况
 * created by kalu on 2021-01-25
 */
class AsmTaskListener implements TaskExecutionListener, BuildListener {

    private static final String TAG = "AsmTaskListener";

    @Override
    void beforeExecute(Task task) {
        PluginLogUtil.log(TAG + "task before : " + task.getName())
    }

    /**
     * 比如，我们要在packageRelease这个task任务执行完后，做一些操作，
     * 我们就可以在此方法中判断
     * @param task
     * @param taskState
     */
    @Override
    void afterExecute(Task task, TaskState taskState) {
        PluginLogUtil.log(TAG + "task after : " + task.getName())
        if (task.getName() == "packageRelease") {
            //做自己的任务
        }
    }

    @Override
    void buildFinished(BuildResult result) {
        //项目build完成之后，会调用此方法
        PluginLogUtil.log(TAG + "build finished.")
    }

    @Override
    void buildStarted(Gradle gradle) {
        PluginLogUtil.log(TAG + "build started.")
    }

    @Override
    void projectsEvaluated(Gradle gradle) {
        PluginLogUtil.log(TAG + "project evaluated.")
    }

    @Override
    void projectsLoaded(Gradle gradle) {
        PluginLogUtil.log(TAG + "project loaded.")
    }

    @Override
    void settingsEvaluated(Settings settings) {
        PluginLogUtil.log(TAG + "setting evaluated.")
    }
}
