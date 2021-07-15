package com.example.dag.sample.task

import android.util.Log
import com.tool.dag.task.StartTask

/**
 * Created by wxk on 2021/7/15.
 */
class SecondTask : StartTask() {

    override fun workOnMainThread(): Boolean {
        return true
    }

    override fun apply() {
        Log.d("wxk:",Thread.currentThread().name)
        Thread.sleep(5000)
    }
}