package com.tool.dag.task

import android.os.Looper
import com.tool.dag.task.sort.DAGSort
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by wxk on 2021/7/14.
 */
open class StartTaskManager {
    private lateinit var mTaskCreator: TaskCreator
    private lateinit var mTaskDispatcher: TaskDispatcher
    private var successCount :AtomicInteger = AtomicInteger(0)
    private lateinit var completeListener : ()->Unit
    fun addContextAndTask(taskCreator:TaskCreator):StartTaskManager{
        mTaskCreator = taskCreator
        successCount = AtomicInteger(0)
        return this
    }

    fun addCompleteListener(listener: ()->Unit ):StartTaskManager{
        completeListener = listener
        return this
    }

    private fun onCompleted(){
       if (this::completeListener.isInitialized){
           completeListener()
       }
    }


    private var finishTask:(task:StartTask)->Unit = {
        it.setFinished()
        successCount.getAndAdd(1)
        if (successCount.get() == mTaskCreator.taskList.size){
            onCompleted()
        }else{
            DAGSort.getReadyTask(it, mTaskCreator.childMap)?.forEach { task->
                mTaskDispatcher.launchTask(task)
            }
        }
    }

    fun start(){
        if (Looper.getMainLooper() != Looper.myLooper()){
            throw RuntimeException("must start with main thread")
        }
        if (!this::mTaskCreator.isInitialized){
            throw RuntimeException("must call addContextAndTask to init taskCreator first")
        }
        mTaskDispatcher = TaskDispatcher(finishTask)
        var taskList = mTaskCreator.taskList
        taskList?.forEach {
            if (it.getPreTaskSize() == 0 && !it.isFinish()){
                mTaskDispatcher.launchTask(it)
            }
        }
    }
}