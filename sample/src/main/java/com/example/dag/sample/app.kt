package com.example.dag.sample

import android.app.Application
import android.util.Log
import com.example.dag.sample.task.FirstTask
import com.example.dag.sample.task.FourthTask
import com.example.dag.sample.task.SecondTask
import com.example.dag.sample.task.ThirdTask
import com.tool.dag.task.StartTaskManager
import com.tool.dag.task.TaskCreator

/**
 * Created by wxk on 2021/7/15.
 */
class app : Application() {

    override fun onCreate() {
        super.onCreate()
        var taskCreator = TaskCreator.Builder().addTask(FirstTask())
            .addTask(ThirdTask()).after(FirstTask::class.java,SecondTask::class.java, FourthTask::class.java)
            .addTask(SecondTask()).after(FirstTask::class.java)
            .addTask(FourthTask()).after(FirstTask::class.java)
            .build()
        StartTaskManager().addContextAndTask(taskCreator).addCompleteListener {
            Log.d("StartTask:","complete")
        }.start()
        Log.d("StartTask:","Application onCreate complete")
    }
}

