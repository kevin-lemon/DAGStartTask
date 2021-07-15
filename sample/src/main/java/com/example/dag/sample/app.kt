package com.example.dag.sample

import android.app.Application
import android.util.Log
import com.example.dag.sample.task.FirstTask
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
    }
}

