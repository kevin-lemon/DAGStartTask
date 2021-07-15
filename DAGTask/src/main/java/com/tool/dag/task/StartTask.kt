package com.tool.dag.task

/**
 * Created by wxk on 2021/7/14.
 */
abstract class StartTask {

    private lateinit var preSet :HashSet<Class<out StartTask>>
    private var isFinish = false
    open fun workOnMainThread():Boolean = false

    open fun isFinish():Boolean = isFinish

    open fun setFinished(){
        isFinish = true
    }

    open fun  addPreTask(taskName:Class<out StartTask>){
        if (!this::preSet.isInitialized){
            preSet = HashSet()
        }
        preSet.add(taskName)
    }

    open fun getPreTask():HashSet<Class<out StartTask>>?{
        return if (!this::preSet.isInitialized || preSet.isEmpty()) {
            null
        }else{
            preSet
        }
    }

    open fun removePreTask(taskName:Class<out StartTask>){
        if (this::preSet.isInitialized && preSet.isNotEmpty()){
            preSet.remove(taskName)
        }
    }

    open fun checkAllPreTaskFinish():Boolean{
        return !this::preSet.isInitialized || preSet.isEmpty()
    }

    open fun getPreTaskSize():Int{
        return if (!this::preSet.isInitialized || preSet.isEmpty()){
            0
        }else{
            preSet.size
        }
    }


    open fun getTaskName():String{
        return this::class.java.name
    }

    abstract fun apply()
}