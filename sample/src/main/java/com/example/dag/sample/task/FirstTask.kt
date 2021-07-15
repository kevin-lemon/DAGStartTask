package com.example.dag.sample.task

import android.util.Log
import com.tool.dag.task.StartTask

/**
 * Created by wxk on 2021/7/15.
 */
class FirstTask : StartTask() {


    override fun apply() {
        Log.d("wxk:",Thread.currentThread().name)
        Thread.sleep(1000)
    }
}