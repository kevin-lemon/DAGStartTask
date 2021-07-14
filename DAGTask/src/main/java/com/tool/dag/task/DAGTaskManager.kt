package com.tool.dag.task

import android.app.Application
import android.os.Looper

/**
 * Created by wxk on 2021/7/14.
 */
object DAGTaskManager {
    private lateinit var mContext: Application
    private lateinit var mTaskCreator: TaskCreator
    fun addContextAndTask(context: Application,taskCreator:TaskCreator){
        mContext = context
        mTaskCreator = taskCreator
    }

    fun start(){
        if (Looper.getMainLooper() != Looper.myLooper()){
            throw RuntimeException("must start with main thread")
        }
        if (!this::mContext.isInitialized){
            throw RuntimeException("must call addContextAndTask to init context first")
        }
        if (!this::mTaskCreator.isInitialized){
            throw RuntimeException("must call addContextAndTask to init taskCreator first")
        }

        mTaskCreator.start()
    }
}