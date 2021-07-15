package com.tool.dag.task

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by wxk on 2021/7/15.
 */
class TaskDispatcher(val finish: (task: StartTask) -> Unit) :CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext
    private val mutex = Mutex()
    fun launchTask(task:StartTask){
        if (task.workOnMainThread()){
            launch(Dispatchers.Main){
                Log.d("StartTask:","StartTask ${task.getTaskName()} start")
                task.apply()
                Log.d("StartTask:","StartTask ${task.getTaskName()} completed")
                taskFinishNotify(task)
            }
        }else launch(Dispatchers.IO){
            Log.d("StartTask:","StartTask ${task.getTaskName()} start")
            task.apply()
            Log.d("StartTask:","StartTask${task.getTaskName()} completed")
            taskFinishNotify(task)
        }
    }

    private suspend fun taskFinishNotify(task:StartTask){
        mutex.withLock {
            finish(task)
        }
    }

}